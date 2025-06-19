import SelfHealing.CustomWebDriver;
import SelfHealing.Details;
import SelfHealing.FirstRunDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.Page;

import java.time.Duration;
import static SelfHealing.HealX.wait;

//public class test extends SelfHealing.Details {
//    private static final Logger log = LoggerFactory.getLogger(test.class);
//    By logo = By.id("nav-logo-sprites");
//    By searchBar = By.id("twotabsearchtextbox");
//    By languageBtn = By.id("icp-nav-flyout");
//    By searchBtn = By.cssSelector("#nav-search-submit-button");
//    public static SelfHealing.FirstRunDriver driver;
//    @Before
//    public void beforeTest  () {
//        WebDriver delegate =  new ChromeDriver();
//        driver = new SelfHealing.FirstRunDriver(delegate) ;
//        PageFactory.initElements(delegate,this);
//        driver.get("https://www.amazon.in/");
//    }
//    @Test
//    public void checkNavigationFlow(){
////        System.out.println(logo);
//        String actualTitle = driver.getTitle();
//        String expectedTitle = "Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in";
//        Assert.assertEquals(actualTitle,expectedTitle);
////        System.out.println(searchBtn);
////        driver.findElement(logo);
////        driver.findElement(searchBar);
//        driver.findElement(searchBtn);
////        driver.findElement(languageBtn);
//
//    }
//    @After
//    public void tearDown(){
//        if (driver != null) {
//            driver.quit();
//        }
//    }
//}
public class test extends Details {
//    private static final Logger log = LoggerFactory.getLogger(test.class);
//    By aakashLogo = By.cssSelector("img[src='images/AD_logo.png']");
//    By registerNowBtn = By.cssSelector("a[class='_registerNowBtn_12rr9_332']");
//    By logo = By.id("nav-logo-sprites");
//    By searchBar = By.id("twotabsearchtextbox");
//    By languageBtn = By.id("icp-nav-flyout");
//    By searchBtn = By.cssSelector("#nav-search-submit-button");
    public static CustomWebDriver driver;

    private Page page;
    @Before
    public void beforeTest  () {
        WebDriver delegate =  new ChromeDriver();
        driver = new CustomWebDriver(delegate);
//        PageFactory.initElements(delegate,this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.ril.com/");
        page = new Page();
        page.initialize(driver,wait);
    }

    @Test
    public void validateHomePage(){
        page.validateHomePage();
        page.clickBtn();
    }



//    @Test
//    public void checkNavigationFlow(){
////        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(2));
////        driver.findElement(aakashLogo);
//
//
//
////        wait.until(ExpectedConditions.visibilityOfElementLocated(registerNowBtn));
////        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
////        jsExecutor.executeScript("arguments[0].click();", driver.findElement(registerNowBtn));
////        driver.findElement(registerNowBtn).click();
//////        System.out.println(logo);
////        String actualTitle = driver.getTitle();
////        String expectedTitle = "Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in";
////        Assert.assertEquals(actualTitle,expectedTitle);
//////        System.out.println(searchBtn);
//////        driver.findElement(logo);
//////        driver.findElement(searchBar);
////        driver.findElement(searchBtn);
////        driver.findElement(languageBtn);
//
//    }
    @After
    public void tearDown(){
        if (driver != null) {
            driver.quit();
        }
    }
}
