import SelfHealing.CustomWebDriver;
import SelfHealing.FirstRunDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import pages.Page;

import java.time.Duration;

import static SelfHealing.HealX.wait;

public class test {

    public static CustomWebDriver driver;
    private Page page;

    @Parameters("browser")
    @BeforeMethod
    public void beforeTest(@Optional("chrome") String browser) {
        WebDriver delegate;

        // Cross-browser support: pick driver based on the parameter we provide
        if (browser.equalsIgnoreCase("chrome")) {
            delegate = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            delegate = new FirefoxDriver();
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        driver = new CustomWebDriver(delegate);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.ril.com/");

        page = new Page();
        page.initialize(driver, wait);
    }

    @Test
    public void validateHomePage() {
        page.validateHomePage();
        page.clickBtn();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
