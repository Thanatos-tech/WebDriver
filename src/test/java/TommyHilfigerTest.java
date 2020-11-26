import com.sun.jmx.snmp.tasks.Task;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.sql.Time;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public  class TommyHilfigerTest {

    private WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void BrowserSetup(){
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver = new ChromeDriver(chromeOptions);
    }



    @Test
    public void SearchItemTest() throws InterruptedException {

        driver.get("https://ru.tommy.com/%D0%BC%D1%83%D0%B6%D1%87%D0%B8%D0%BD%D1%8B");
        WebElement searchCookiesButton = WaitForElementsLocatedBy(driver,
                By.xpath("//button[@class='cookie-notice__agree-button cookie-notice__agree-button--ru button']"));
        searchCookiesButton.click();
        WebElement searchInput = WaitForElementsLocatedBy(driver, By.id("headerSearchInput"));
        searchInput.click();
        searchInput.sendKeys("MW0MW12983_BD2");
        searchInput.sendKeys(Keys.ENTER);
        List<WebElement> seacrhResults = driver.findElements(By.xpath("//li[@class='product-list__item three']/article"));
        Assert.assertTrue(seacrhResults.get(0).getAttribute("data-primarycolour").equals("MW0MW12983_BD2"), "Item not found");

    }

    @Test
    public void AddItemInWishlistTest() throws InterruptedException {

        driver.get("https://ru.tommy.com/%D0%BC%D1%83%D0%B6%D1%87%D0%B8%D0%BD%D1%8B");
        WebElement searchCookiesButton = WaitForElementsLocatedBy(driver,
                By.xpath("//button[@class='cookie-notice__agree-button cookie-notice__agree-button--ru button']"));
        searchCookiesButton.click();
        WebElement goToShopButton = WaitForElementsLocatedBy(driver,
                By.xpath("//span[@class='th-cta-wrapper th-cta-wrapper-vertical ']/a[text()=' ПЕРЕЙТИ В МАГАЗИН ']"));
        goToShopButton.click();
        List<WebElement> firstItemInList = driver.findElements(By.xpath("//ul[@class='product-list__items']/li"));
        firstItemInList.get(0).click();
        WebElement addingWishlistToggle = WaitForElementsLocatedBy(driver,
                By.xpath("//span[@class='wishlist__toggle--button']"));
        addingWishlistToggle.click();
        WebElement wishlistButton = WaitForElementsLocatedBy(driver,
                By.xpath("//button[@class='wishlist__button']"));
        wishlistButton.click();

        WebDriverWait waitForElement = new WebDriverWait(driver, 10);
        waitForElement.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='wishlist-item']")));

        List<WebElement> itemsInWishlist = driver.findElements(By.xpath("//div[@class='wishlist-item']"));
        Assert.assertTrue(itemsInWishlist.size()>0,"Item not found");

    }

    private static WebElement WaitForElementsLocatedBy(WebDriver driver, By by){
        return new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(by));
    }


    @AfterMethod(alwaysRun = true)
    public void BrowserTearDown() {
        driver.quit();
    }

}
