package SelfHealing;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

public class CustomWebDriver implements WebDriver, TakesScreenshot, JavascriptExecutor {
    public static WebDriver driver;

    public CustomWebDriver(WebDriver delegate) {
        driver = delegate;
    }

    public CustomWebDriver() {

    }


    @Override
    public WebElement findElement(By by) {

        try {
            return driver.findElement(by);
        } catch (NoSuchElementException e) {
            String locatorName;
            try {
                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                // The element at index 2 should be the caller (depending on how deep the call stack is)
                String callerClassName = stackTrace[2].getClassName();
                Class<?> callerClass = null;
                callerClass = Class.forName(callerClassName);
                Object callerInstance = callerClass.getDeclaredConstructor().newInstance();

                // Define the parameter type
                Class<?>[] paramTypes = new Class<?>[] { By.class };
                Method method = callerClass.getMethod("getLocatorName", paramTypes);

                // Call the method with a parameter
                locatorName = (String) method.invoke(callerInstance, by);
                System.out.println(locatorName);

            } catch (Exception ex) {
//                System.out.println(by.);
//                System.out.println(by.getClass());
                System.out.println("Unable to find the locator Name using StackTrace");
                throw new RuntimeException(ex);
            }
            if (locatorName!=null){
                NormalRun nr = new NormalRun(driver);
                WebElement healedElement = nr.getElement(locatorName,by);
                    if (healedElement != null) {
                        return healedElement;
                    }
            }

            // Element not found, call SelfHealing.HealX
//            String varName = getVariableName(by);
            System.out.println("Not found the following Locator :"+locatorName);

        }
        return null;
    }

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

    @Override
    public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
        return ((TakesScreenshot) driver).getScreenshotAs(target);
    }

    @Override
    public Object executeScript(String script, Object... args) {
        return ((JavascriptExecutor) driver).executeScript(script, args);
    }

    @Override
    public Object executeAsyncScript(String script, Object... args) {
        return ((JavascriptExecutor) driver).executeAsyncScript(script, args);
    }
}
