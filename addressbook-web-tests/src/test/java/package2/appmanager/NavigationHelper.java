package package2.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {
    public NavigationHelper(WebDriver driver) {
        super(driver);
    }
    public void goToGroupPage() {
        click(By.linkText("groups"));
    }
    public void goToContactPage() {
        click(By.linkText("home"));
    }
}
