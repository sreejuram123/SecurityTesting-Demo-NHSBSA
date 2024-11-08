package driver;

import enums.ConfigProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import utils.ConfigFileReader;
import utils.ZapUtility;

public class DriverFactory {

    public static WebDriver get() {
        String browser = ConfigFileReader.get(ConfigProperties.BROWSER);

        WebDriver driver = null;

        if (browser.equalsIgnoreCase("chrome")) {

            ChromeOptions options=new ChromeOptions();
            options.setProxy(ZapUtility.proxy);

            driver = new ChromeDriver();

        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();

        } else if (browser.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        }
        return driver;
    }

}
