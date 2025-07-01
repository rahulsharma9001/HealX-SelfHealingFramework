package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import SelfHealing.*;

public class Page extends Details {
    CustomWebDriver driver;
    WebDriverWait wait;
    private static final Logger log = LoggerFactory.getLogger(Page.class);
    By RelLogo = By.cssSelector("img[class='img-fluid logo']");
    By searchBarRel = By.cssSelector("div[class='search-wrapper-cs'] a[aria-label='Close']");


    public void initialize(CustomWebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);

    }
    public void validateHomePage(){
        driver.findElement(RelLogo);
    }

    public void clickBtn(){
        driver.findElement(searchBarRel).click();
    }
}
