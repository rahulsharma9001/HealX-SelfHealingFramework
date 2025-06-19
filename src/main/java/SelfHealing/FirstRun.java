package SelfHealing;

import org.openqa.selenium.*;
import org.apache.commons.lang3.tuple.Pair;


import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class FirstRun {
    private static WebDriver driver;
    FirstRun(WebDriver driver){
        this.driver = driver;
    }
    public WebElement findElement(String locatorType, String locatorValue) {
        WebElement element = null;
        try {
            if (locatorType.equals("id")) {
                element = driver.findElement(By.id(locatorValue));
            } else if (locatorType.equals("cssSelector")) {
                element = driver.findElement(By.cssSelector(locatorValue));
            } else if (locatorType.equals("xpath")) {
                element = driver.findElement(By.xpath(locatorValue));
            } else {
                element = driver.findElement(By.xpath("//*[@" + locatorType + "='" + locatorValue + "']"));
            }
        } catch (NoSuchElementException e) {
            return null;
        }

        return element;
    }
    //    public void
    public Map<String, String> getElementAttributes(WebElement element) {
        Map<String, String> attributes = null;
        JavascriptExecutor js = (JavascriptExecutor) driver;
//        WebElement element = (WebElement) js.executeScript(
//                "return document.elementFromPoint(arguments[0], arguments[1]);", x, y
//        );

        if (element != null) {
            // Get all attributes of the element
            attributes = new HashMap<>();
            String script = "var items = {}; " +
                    "for (index = 0; index < arguments[0].attributes.length; ++index) { " +
                    "items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value }; " +
                    "return items;";
            attributes = (Map<String, String>) js.executeScript(script, element);
            attributes.forEach((key, value) -> {
                System.out.println(key + ": " + value);
            });
        } else {
            System.out.println("No element found at the given coordinates.");
        }
        return attributes;
    }
    public Pair<Integer, Integer> getCoordinates(WebElement element){
        Point point = element.getLocation();

        int x = point.getX();
        int y = point.getY();
        // Assert the element is not null and the position is retrieved
        assertNotNull(element);
        assertNotNull(point);
        // Output the position
        System.out.println("Element's Position: (" + x + ", " + y + ")");
        return Pair.of(x,y);
    }

    public void firstRunUpdate(String locatorName, String locatorType,String locatorValue,WebElement specificElement) {

        // Locate the element
        RemoteDB db = new RemoteDB();
        String locator= db.getLocator(locatorName) ;
        if(locator == null){
            db.addData(locatorName,locatorType,locatorValue);
        }
        Pair<Integer, Integer> coordinates = getCoordinates(specificElement);
        int x = coordinates.getLeft();
        int y = coordinates.getRight();
        Map<String, String> attributes = getElementAttributes(specificElement);

        db.setAttributesAndCoordinates(locatorName,x,y,attributes);

    }

}

