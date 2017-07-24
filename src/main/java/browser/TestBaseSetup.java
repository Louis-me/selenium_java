package browser;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import util.ExtentTestNGIReporterListener;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class TestBaseSetup {
    private WebDriver driver = null;

    public WebDriver setDriver(String browserType, String appURL, String version, String remoteip) {
        if (browserType.equals("chrome")) {
            driver = initChromeDriver(appURL, version, remoteip);

        } else if (browserType.equals("firefox")) {
            driver = initFirefoxDriver(appURL, version, remoteip);

        } else {
            System.out.println("browser : " + browserType
                    + " is invalid, Launching Firefox as browser of choice..");
            driver = initFirefoxDriver(appURL, version, remoteip);
        }
        return driver;
    }


    private WebDriver initChromeDriver(String appURL, String chrome_version, String remoteip) {

//        System.setProperty("webdriver.chrome.driver", driverPath
//                + "chromedriver.exe");
        if (Objects.equals(chrome_version, "") || Objects.equals(remoteip, "")) {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.navigate().to(appURL);
        } else {
            DesiredCapabilities chromeDesiredcap = DesiredCapabilities.chrome();
//            DesiredCapabilities chromeDesiredca=new DesiredCapabilities("chrome", "48.0.2564.109", Platform.LINUX);
//            RemoteWebDriver  r = new RemoteWebDriver(new URL("http://192.168.99.100:4444/wd/hub/"),desiredCaps);

            chromeDesiredcap.setVersion(chrome_version);

            try {
                driver = new RemoteWebDriver(new URL("http://" + remoteip + ":4444/wd/hub/"), chromeDesiredcap);
                driver.manage().window().maximize();
                driver.navigate().to(appURL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        return driver;

    }

    private WebDriver initFirefoxDriver(String appURL, String version, String remoteip) {

        if (Objects.equals(version, "") || Objects.equals(remoteip, "")) {
            driver = new FirefoxDriver();
            driver.manage().window().maximize();
            driver.navigate().to(appURL);
        } else {
            DesiredCapabilities ffDesiredcap= new DesiredCapabilities("firefox", version, Platform.LINUX);

            ffDesiredcap.setVersion(version);
//            WebDriverdriver = newRemoteWebDriver(new URL(“http://192.168.99.100:4444/wd/hub/”),ffDesiredcap);

            try {
                driver = new RemoteWebDriver(new URL("http://" + remoteip + ":4444/wd/hub/"), ffDesiredcap);
                driver.manage().window().maximize();
                driver.navigate().to(appURL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Launching Firefox browser..");
        return driver;
    }
}