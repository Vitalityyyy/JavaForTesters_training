package package2.appmanager;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Browser;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import static org.testng.Assert.fail;

public class ApplicationManager {
    private final Properties properties;
    WebDriver driver;
    private SessionHelper sessionHelper;
    private NavigationHelper navigationHelper;
    private GroupHelper groupHelper;
    private ContactHelper contactHelper;
    private String baseUrl;
    private StringBuffer verificationErrors = new StringBuffer();
    private String browser;
    private DbHelper dbHelper;

    public ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(String.format("src/test/resources/%s.properties", target)));
        dbHelper = new DbHelper();
        if (browser.equals(Browser.FIREFOX.browserName())) {
            driver = new FirefoxDriver();
        } else if (browser.equals(Browser.CHROME.browserName())) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(options);
        } else if (browser.equals(Browser.EDGE.browserName())) {
            driver = new EdgeDriver();
        }
        //System.setProperty("webdriver.gecko.driver", "C:/distr/geckodriver.exe");
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        driver.get(properties.getProperty("web.baseUrl"));
        sessionHelper = new SessionHelper(driver);
        groupHelper = new GroupHelper(driver);
        contactHelper = new ContactHelper(driver);
        navigationHelper = new NavigationHelper(driver);
        sessionHelper.login(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));
    }

    public void stop() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    public GroupHelper group() {
        return groupHelper;
    }
    public ContactHelper contact() {
        return contactHelper;
    }
    public NavigationHelper goTo() {
        return navigationHelper;
    }
    public DbHelper db() {
        return dbHelper;
    }
}
