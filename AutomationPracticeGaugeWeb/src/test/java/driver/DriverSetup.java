package driver;

import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class DriverSetup {

    @BeforeScenario
    public void initializeDriver() throws MalformedURLException {
        Driver.getInstance().webDriver = getDriver();
        Driver.getInstance().webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)
                .setScriptTimeout(10, TimeUnit.SECONDS)
                .pageLoadTimeout(10, TimeUnit.SECONDS);
        Driver.getInstance().webDriver.manage().window().fullscreen();

    }

    // Bonigarcia library is used to keep WebDriver binaries up to date.
    public static WebDriver getDriver() throws MalformedURLException {
        String browser = System.getenv("BROWSER");
        if (browser == null) {
            WebDriverManager.chromedriver().setup();
            return new ChromeDriver();
        }
        switch (browser) {
            case "IE":
                WebDriverManager.iedriver().setup();
                return new InternetExplorerDriver();
            case "FIREFOX":
                WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver();
            case "REMOTECHROME":
                DesiredCapabilities capability = DesiredCapabilities.chrome();
                capability.setBrowserName("chrome");
                capability.setPlatform(org.openqa.selenium.Platform.LINUX);
                return new RemoteWebDriver(new URL("http://10.0.0.4:4447/wd/hub"), capability);
            default:
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();

        }
    }

    @AfterScenario
    public void closeDriver() {
        if (Driver.getInstance().webDriver != null) {
            Driver.getInstance().webDriver.quit();
        }
    }
}
