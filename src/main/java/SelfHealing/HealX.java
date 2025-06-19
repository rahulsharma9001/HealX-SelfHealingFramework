package SelfHealing;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.internal.collections.Pair;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.testng.internal.Utils.log;

public class HealX {
    public static WebDriver driver;
    public static WebDriverWait wait;
    RemoteDB db = new RemoteDB();
    private static final List<String> ATTRIBUTE_PRIORITY = Arrays.asList(
            "id", "name", "class", "src", "alt","href", "type", "placeholder", "value", "data-test.java", "aria-label", "title", "for"
    );

    HealX(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Utility method to escape special characters in attribute values for CSS selectors
    private String escapeCssSelectorValue(String value) {
        return value.replace("\\", "\\\\") // Escape backslashes
                .replace("'", "\\'")  // Escape single quotes
                .replace("\"", "\\\"") // Escape double quotes
                .replace("\n", "\\n")  // Escape newlines
                .replace("\r", "\\r")  // Escape carriage returns
                .replace("\f", "\\f")  // Escape form feeds
                .replace("\b", "\\b"); // Escape backspace
    }

    // Method to create CSS selectors using combinations of two attributes
    private List<String> createLocatorsWithAttributeCombinations(String locatorName) {
        List<String> possibleLocators = new ArrayList<>();

        for (int i = 0; i < ATTRIBUTE_PRIORITY.size(); i++) {
            String attr1 = ATTRIBUTE_PRIORITY.get(i);
            String value1 = db.getLocatorAttributeValue(locatorName, attr1);
            if (value1 == null) continue;

            for (int j = i + 1; j < ATTRIBUTE_PRIORITY.size(); j++) {
                String attr2 = ATTRIBUTE_PRIORITY.get(j);
                String value2 = db.getLocatorAttributeValue(locatorName, attr2);
                if (value2 == null) continue;

                // Construct the CSS selector for the combination of these two attributes
                StringBuilder cssSelectorBuilder = new StringBuilder();
                cssSelectorBuilder.append("*");

                if (attr1.equals("class")) {
                    for (String cls : value1.split("\\s+")) {
                        cssSelectorBuilder.append("[class~='").append(escapeCssSelectorValue(cls)).append("']");
                    }
                } else {
                    cssSelectorBuilder.append("[").append(attr1).append("='").append(escapeCssSelectorValue(value1)).append("']");
                }

                if (attr2.equals("class")) {
                    for (String cls : value2.split("\\s+")) {
                        cssSelectorBuilder.append("[class~='").append(escapeCssSelectorValue(cls)).append("']");
                    }
                } else {
                    cssSelectorBuilder.append("[").append(attr2).append("='").append(escapeCssSelectorValue(value2)).append("']");
                }

                possibleLocators.add(cssSelectorBuilder.toString());
            }
        }

        return possibleLocators;
    }

    // Combined method for healing locators using both single and multiple attributes
    public String getHealedLocatorUsingAttributes(String locatorName) {
        System.out.println("Attempting to find node using attribute priority list");

        // First, try to find the element using single attributes
        for (String attr : ATTRIBUTE_PRIORITY) {
            String value = db.getLocatorAttributeValue(locatorName, attr);
            if (value != null) {
                try {
                    System.out.println("Trying attribute: " + attr);
                    String singleAttributeLocator = "[" + attr + "='" + value + "']";
                    List<WebElement> newNode = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                            By.cssSelector(singleAttributeLocator)
                    ));
                    if (newNode != null && newNode.size() == 1) {
                        log("Element Found using single attribute!!! Locator: " + singleAttributeLocator);
                        return singleAttributeLocator;
                    }
                } catch (Exception e) {
                    System.out.println("Failed to find node using attribute: " + attr);
                }
            }
        }

        // If single attribute method fails, try using combinations of two attributes
        System.out.println("Attempting to find node using combination of two attributes");
        List<String> possibleLocators = createLocatorsWithAttributeCombinations(locatorName);

        for (String locator : possibleLocators) {
            try {
                System.out.println("Trying combined locator: " + locator);
                List<WebElement> newNode = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.cssSelector(locator)
                ));
                if (newNode != null && newNode.size() == 1) {
                    log("Element Found using combined attributes!!! Combined Locator Used: " + locator);
                    return locator;
                }
            }catch (Exception e) {
                System.out.println("Failed to find node using combined attributes: " + locator);
            }
        }

        return null; // Return null if no element is found using either method
    }

    public WebElement getHealedWebElementUsingPosition(String locatorName) {
        WebElement element = null;
        System.out.println("Trying to find Element using its position on the page");
        Pair<Integer, Integer> coordinates = db.getElementPosition(locatorName);
        if (coordinates != null && coordinates.first() != null) {
            int x = coordinates.first();
            int y = coordinates.second();
            JavascriptExecutor js = (JavascriptExecutor) driver;
            element = (WebElement) js.executeScript(
                    "return document.elementFromPoint(arguments[0], arguments[1]);", x, y
            );
        } else {
            System.out.println("Element position not found in the DB");
        }
        return element;
    }
}
