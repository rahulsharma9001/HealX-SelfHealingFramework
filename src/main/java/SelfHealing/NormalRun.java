package SelfHealing;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class NormalRun {
    public static WebDriver driver;
    WebDriverWait wait;
    public NormalRun(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver,Duration.ofSeconds(15));
    }
    public WebElement findElement(String locatorType, String locatorValue) {
        WebElement element = null;

//        try {
//            if (locatorType.equals("id")) {
//                element = driver.findElement(By.id(locatorValue));
//            } else if (locatorType == "cssSelector") {
//                element = driver.findElement(By.cssSelector(locatorValue));
//            } else if (locatorType == "xpath") {
//                element = driver.findElement(By.xpath(locatorValue));
//            } else {
//                element = driver.findElement(By.xpath("//*[@" + locatorType + "='" + locatorValue + "']"));
//            }
//        } catch (NoSuchElementException e) {
//            return null;
//        }

        return element;
    }
    public WebElement getElement(String locatorName,By locatorByType){
        RemoteDB db = new RemoteDB();
        String locatorValue= db.getLocator(locatorName) ;
        String locatorType = db.getLocatorType(locatorName);
        WebElement element =null;

        System.out.println("Element not found using stored locator, Trying to heal");
//        driver.navigate().refresh();
//        wait.until((ExpectedCondition<Boolean>) wd ->
//                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
//        element = driver.findElement(locatorByType);
//        if (element != null) {
//            System.out.println("Element Found After Refresh");
//            return element;
//        }
        String alternateLocator = db.getAlternateLocator(locatorName);
//        String alternateLocatorType = db.getAlternateLocatorType(locatorName);
        if (!alternateLocator.equals("")) {
            element = driver.findElement(By.cssSelector(alternateLocator));
            if(element != null){
                System.out.println("Element Found using Alternate Locator, Locator Type and value are :" +alternateLocator);
                return element;
            }
        }
        HealX healingObj = new HealX(driver);
        String healedLoc = healingObj.getHealedLocatorUsingAttributes(locatorName);
        if(healedLoc!=null){
            db.setAlternateLocator(locatorName,healedLoc);
            System.out.println("Locator Found using other attributes" + healedLoc);
            element = driver.findElement(By.cssSelector(healedLoc));
            return element;
        }
        element = healingObj.getHealedWebElementUsingPosition(locatorName);
        if(element!= null){
            System.out.println("Locator Found using positional coordinates: ");
            return element;
        }
        System.out.println("Unable to find A healed Locator. Ensure that after integrating SelfHealing.HealX in your project you have made a firstRun with Correct locators and tests were passing");
        return element;
    }
}