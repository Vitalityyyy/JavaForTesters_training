package package2.tests;

import org.openqa.selenium.remote.Browser;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import package2.appmanager.ApplicationManager;

public class TestBase {

    protected static final ApplicationManager app = new ApplicationManager(Browser.CHROME.browserName());

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        app.stop();
    }
}
