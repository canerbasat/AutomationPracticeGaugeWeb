package step;

import driver.Driver;
import mapping.Mapper;
import org.apache.commons.text.CharacterPredicates;
import org.junit.Assert;
import org.openqa.selenium.support.ui.Select;
import pages.BasePage;
import com.thoughtworks.gauge.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.text.RandomStringGenerator;

import java.util.List;
import java.util.Random;

public class StepImplementation {
    Mapper mapper = new Mapper();
    public WebDriver driver = Driver.getInstance().webDriver;
    WebDriverWait wait = new WebDriverWait(driver, 10);
    BasePage basePage = new BasePage(driver);
    String signInMail = null;
    String productPrice = null;
    String productPriceAtCart = null;

    @Step("<url> sayfasına gidilir")
    public void navigateTo(String url) {
        basePage.navigateTo(url);
    }

    @Step("<Saniye> Saniyesi kadar beklenir")
    public void waitSeconds(int seconds) {
        basePage.waitSeconds(seconds);
    }

    @Step("<by> butonuna tıklanır")
    public void clickElement(String by) {
        basePage.clickElement(by);
    }

    @Step("<by> alanına <text> yazılır")
    public void sendKeys(String by, String text) {
        basePage.sendKeys(by, text);
    }

    @Step("<by> alanı silinir")
    public void deleteTextbox(String by) {
        basePage.clearTextbox(by);
    }

    @Step("<by> elementinin görülmesi beklenir")
    public void waitForTheElement(String by) {
        basePage.waitUntilElementAppear(by);
    }

    @Step("<by> alanının üzerine mouse ile gelinir")
    public void hoverOnTheElement(String by) {
        basePage.hoverOnElement(by);
    }

    @Step("Herhangi bir pop up varsa kapatılır")
    public void closeThePopUps() {
        basePage.acceptPopupIfExits();
    }

    @Step("Klavyeden TAB tuşuna basılır")
    public void pressTAB() {
        driver.findElement(By.tagName("body")).sendKeys(Keys.TAB);
    }

    @Step("Klavyeden ENTER tuşuna basılır")
    public void pressENTER() {
        driver.findElement(By.tagName("body")).sendKeys(Keys.ENTER);
    }

    @Step("<byList> listesinden rastgele bir elemana tıklanır")
    public void clickRandomElementFromByList(String byList) {
        List<WebElement> elements = driver.findElements(mapper.getElementFromJSON(byList));
        Random rnd = new Random();
        int randomNumber = rnd.nextInt(elements.size() + 1);
        WebElement element = elements.get(randomNumber);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    @Step("<el> rastgele bir string dizi yazilir ve kaydedilir")
    public void randomStringGenerate(String el) {
        RandomStringGenerator randomStringGenerator =
                new RandomStringGenerator.Builder()
                        .withinRange('0', 'z')
                        .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
                        .build();
        signInMail = randomStringGenerator.generate(12) + "@hotmail.com";
        basePage.sendKeys(el,signInMail);
    }

    @Step("<el> dropdown menusunden <item> secilir")
    public void selectOptionFromDropdown(String el,String item) throws InterruptedException {
        Select selectByVisibleText = new Select(driver.findElement(mapper.getElementFromJSON(el)));
        selectByVisibleText.selectByValue(item);
        Thread.sleep(5000);
    }


    @Step("<el> elementin texti ve kaydedilir")
    public void saveTheInnerText(String el) {
        productPrice = driver.findElement(mapper.getElementFromJSON(el)).getText();
    }
    @Step("<el> elementin texti de  kaydedilir")
    public void saveTheInnerText2(String el) {
        productPriceAtCart = driver.findElement(mapper.getElementFromJSON(el)).getText();
    }

    @Step("Urunun sayfasinda ki fiyatla sepette ki tutari karsilastirilir")
    public  void checkThePriceSame(){
        Assert.assertTrue(productPrice.contains(productPriceAtCart));
    }

    @Step("<el> elementi <key> degerini icerir")
    public  void checkTheContext(String el,String key){
        String varEl =driver.findElement(mapper.getElementFromJSON(el)).getText();
        Assert.assertTrue(varEl.contains(key));
    }



}
