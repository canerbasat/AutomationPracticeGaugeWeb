package pages;

import mapping.Mapper;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class BasePage {
    Mapper mapper = new Mapper();
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor javaScriptExecutor;

    public BasePage(WebDriver webDriver) {
        this.driver = webDriver;
        this.wait = new WebDriverWait(this.driver, 30);
        this.javaScriptExecutor = (JavascriptExecutor) driver;
    }

    public void navigateTo(String url) {
        driver.get(url);
    }

    public void waitSeconds(int seconds) {
        try {
            Thread.sleep(1000 * seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public WebElement findElement(String by) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(mapper.getElementFromJSON(by)));
        return element;
    }

    public void clickElement(String by) {
        findElement(by).click();
    }

    public void sendKeys(String by, String text) {
        findElement(by).sendKeys(text);
    }

    public void clearTextbox(String by) {
        findElement(by).clear();
    }

    public void waitUntilElementAppear(String by) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(mapper.getElementFromJSON(by)));
        Assert.assertTrue(by + " elemanı sayfada görülemedi!",
                element.isDisplayed());
    }

    public void hoverOnElement(String by) {
        WebElement element = findElement(by);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();
        waitSeconds(3);
    }

    public void acceptPopupIfExits() {
        if (driver.switchTo().alert() != null) {
            driver.switchTo().alert().accept();
        }
    }


}
