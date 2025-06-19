package SelfHealing;

import org.openqa.selenium.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class FirstRunDriver extends CustomWebDriver implements WebDriver {
    private WebDriver driver;

    public FirstRunDriver(WebDriver delegate) {
        super();
        // Initialize your WebDriver here (e.g., ChromeDriver, FirefoxDriver)
        // For example, using ChromeDriver:
        driver = delegate;
    }
    @Override
    public WebElement findElement(By by) {
        try {
            WebElement ele = driver.findElement(by);
            String locatorName="";
            FirstRun objectFirstRun = new FirstRun(driver);
            try {
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                // The element at index 2 should be the caller (depending on how deep the call stack is)
                String callerClassName = stackTrace[2].getClassName();
                Class<?> callerClass = null;
                callerClass = Class.forName(callerClassName);
                Object callerInstance = callerClass.getDeclaredConstructor().newInstance();
                // Define the parameter type
                Class<?>[] paramTypes = new Class<?>[]{By.class};
                Method method = callerClass.getMethod("getLocatorName", paramTypes);
                // Call the method with a parameter
                locatorName = (String) method.invoke(callerInstance, by);
                System.out.println("Storing Locator : " + locatorName+ "in DB");

            }catch (InvocationTargetException e) {
                // Retrieve the underlying cause
                Throwable cause = e.getCause();
                cause.printStackTrace();
                throw new RuntimeException(e);
            }
            catch (Exception ex) {
                System.out.println("Unable to find the locator Name using StackTrace");
                throw new RuntimeException(ex);
            }
            String pattern = "By\\.(.+?):\\s*(.+)";

            // Compile the pattern
            Pattern compiledPattern = Pattern.compile(pattern);
            String locatorString = by.toString();
            // Match the input string with the pattern
            Matcher matcher = compiledPattern.matcher(locatorString);

            // Define variables to hold the extracted parts
            String locatorType = null;
            String locatorValue = null;

            // Extract the parts if the pattern matches
            if (matcher.matches()) {
                locatorType = matcher.group(1); // The part between 'By.' and ':'
                locatorValue = matcher.group(2); // The part after ':'
            }
            System.out.println(locatorName);
            System.out.println(locatorType);
            System.out.println(locatorValue);
            objectFirstRun.firstRunUpdate(locatorName, locatorType, locatorValue,ele);
            return ele;
        } catch (NoSuchElementException e) {
            System.out.println("Not found the following Locator :" + by);
            throw e;
        }
    }

    // Implement all WebDriver methods
    @Override
    public void get(String url) {
        driver.get(url);
    }

    @Override
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Override
    public String getTitle() {
        return driver.getTitle();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    @Override
    public String getPageSource() {
        return driver.getPageSource();
    }

    @Override
    public void close() {
        driver.close();
    }

    @Override
    public void quit() {
        driver.quit();
    }

    @Override
    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return driver.switchTo();
    }

    @Override
    public Navigation navigate() {
        return driver.navigate();
    }

    @Override
    public Options manage() {
        return driver.manage();
    }

    // Add any custom methods or override existing ones for additional functionality
    public void customMethod() {
        // Custom functionality here
    }
}
