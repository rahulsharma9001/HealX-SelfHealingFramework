package SelfHealing;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.testng.internal.collections.Pair;

import java.util.Map;

public class    RemoteDB {
    MongoCollection<Document> collection;
    // Constructor Function
    public RemoteDB() {
//        String uri = "mongodb+srv://Rahul:dl5ce1315@cluster0.wr7uofz.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
//        String uri = "mongodb+srv://rahulsharmaa9001:Msw1SHkpYQMGkKJc@healx-cluster.izupxho.mongodb.net/?retryWrites=true&w=majority&appName=HealX-Cluster";
        String uri = "mongodb+srv://rahulsharmaa9001:healxpassword@healx-cluster.izupxho.mongodb.net/?retryWrites=true&w=majority&appName=HealX-Cluster";
        MongoClient mongoClient = MongoClients.create(uri) ;
        MongoDatabase database = mongoClient.getDatabase("loblaw");
        collection = database.getCollection("locator");

    }
    // Function to update all the attributes in first run
    public void setAttributesAndCoordinates(String locatorName , int x, int y, Map<String, String> attributes){
        Document filter = new Document("key", locatorName);

        // Define the updates you want to apply
        Document update = new Document("$set", new Document("x", x)
                .append("y", y)
                .append("attributes",attributes)
        );

        // Perform the update
        collection.updateOne(filter, update);

        System.out.println("Position and Attributes of "+ locatorName +" updated successfully in Remote DB");
    }
    // Function to get the locator from a given locatorName
    public String getLocator(String locatorName){
        // Replace the placeholder with your MongoDB deployment's connection string
           Document doc= collection.find(eq("key", locatorName)).first();


        if (doc != null) {
            System.out.println(doc.toJson());
            System.out.println(doc.getString("locator"));
        } else {
            System.out.println("No matching documents found.");
            return null;
        }
        return doc.getString("locator");
    }

    public String getLocatorType(String locatorName){
        // Replace the placeholder with your MongoDB deployment's connection string
        Document doc= collection.find(eq("key", locatorName)).first();


        if (doc == null)  {
            System.out.println("No matching documents found.");
            return null;
        }
        return doc.getString("locatorType");
    }
    public void setAlternateLocator(String locatorName,String alternateLocator){
        Document filter = new Document("key", locatorName);


        Document update = new Document("$set", new Document("alternateLocator", alternateLocator));

        // Perform the update
        collection.updateOne(filter, update);
    }
    public String getAlternateLocator(String locatorName){
        // Replace the placeholder with your MongoDB deployment's connection string
        Document doc= collection.find(eq("key", locatorName)).first();


        if (doc == null)  {
            System.out.println("No matching documents found.");
            return null;
        }
        return doc.getString("alternateLocator");
    }
//    public String getAlternateLocatorType(String locatorName){
//        // Replace the placeholder with your MongoDB deployment's connection string
//        Document doc= collection.find(eq("key", locatorName)).first();
//
//
//        if (doc == null)  {
//            System.out.println("No matching documents found.");
//            return null;
//        }
//        return doc.getString("alternateLocatorType");
//    }
    public String getLocatorAttributeValue(String locatorName, String attributeName){
        Document doc= collection.find(eq("key", locatorName)).first();
        if (doc == null)  {
            System.out.println("No matching documents found.");
            return null;
        }
        String attributeValue = null;
        Document attributes = doc.get("attributes", Document.class);

        if (attributes != null && attributes.containsKey(attributeName)) {
            // Retrieve the value of the "autocomplete" key
            attributeValue =  attributes.getString(attributeName);
        }
        return attributeValue;
    }
    public void addData(String locatorName, String locatorType,String locatorValue){
        Document document = new Document("key", locatorName)
                .append("locator", locatorValue)
                .append("locatorType",locatorType)
                .append("alternateLocator","")
                .append("alternateLocatorType","")
                .append("x", null)
                .append("y", null)
                .append("attributes", new Document());;

        // Insert the Document into the collection
        collection.insertOne(document);

        System.out.println("New Locator: "+locatorName+"inserted successfully");
    }
    public Pair<Integer,Integer> getElementPosition(String locatorName){
        Document doc= collection.find(eq("key", locatorName)).first();
        if (doc == null)  {
            System.out.println("No matching documents found.");
            return null;
        }
        Integer x = doc.getInteger("x");
        Integer y = doc.getInteger("y");

        if (x == null || y == null) {
            System.out.println("x or y value is null in the document.");
            return null;
        }
        Pair<Integer, Integer> position = Pair.of(x, y);
        return position;
    }

}