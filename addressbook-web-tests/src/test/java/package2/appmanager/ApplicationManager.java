package package2.appmanager;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Browser;

import java.util.concurrent.TimeUnit;
import static org.testng.Assert.fail;

public class ApplicationManager {
    WebDriver driver;
    private SessionHelper sessionHelper;
    private NavigationHelper navigationHelper;
    private GroupHelper groupHelper;
    private ContactHelper contactHelper;
    private String baseUrl;
    private StringBuffer verificationErrors = new StringBuffer();
    private String browser;

    public ApplicationManager(String browser) {
        this.browser = browser;
    }

    public void init() {
        if (browser.equals(Browser.FIREFOX.browserName())) {
            driver = new FirefoxDriver();
        } else if (browser.equals(Browser.CHROME.browserName())) {
            driver = new ChromeDriver();
        } else if (browser.equals(Browser.EDGE.browserName())) {
            driver = new EdgeDriver();
        }
        //System.setProperty("webdriver.gecko.driver", "C:/distr/geckodriver.exe");
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        driver.get("http://localhost/addressbook/");
        sessionHelper = new SessionHelper(driver);
        groupHelper = new GroupHelper(driver);
        contactHelper = new ContactHelper(driver);
        navigationHelper = new NavigationHelper(driver);
        sessionHelper.login("admin", "secret");
    }

    public void stop() {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    public GroupHelper getGroupHelper() {
        return groupHelper;
    }
    public ContactHelper getContactHelper() {
        return contactHelper;
    }
    public NavigationHelper getNavigationHelper() {
        return navigationHelper;
    }
}
