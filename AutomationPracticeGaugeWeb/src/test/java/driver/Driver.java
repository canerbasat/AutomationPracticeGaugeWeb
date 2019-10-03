package driver;

import org.openqa.selenium.WebDriver;

public class Driver {

    private static Driver instance;
    public WebDriver webDriver;

    private Driver() {
    }

    public static Driver getInstance() {
        if (instance == null) {
            instance = new Driver();
        }

        return instance;
    }

}
