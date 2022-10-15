package package2.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import package2.model.ContactData;
import package2.model.GroupData;

public class ContactHelper extends HelperBase {
    public ContactHelper(WebDriver driver) {
        super(driver);
    }
    public void returnToContactPage() {
        click(By.linkText("home page"));
    }
    public void initContactCreation() {
        click(By.linkText("add new"));
    }
    public void fillContactForm(ContactData contactData) {
        type(By.name("lastname"), contactData.getLastName());
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("address"), contactData.getAddress());
        type(By.name("email"), contactData.getEmail());
        type(By.name("mobile"), contactData.getPhone());
    }
    public void submitContactCreation() {
        click(By.name("submit"));
    }
    public void selectContact() {
        click(By.xpath("//img[@alt='Edit']"));
    }
    public void deleteSelectedContact() {
        click(By.xpath("(//input[@name='update'])[3]"));
    }
    public void submitContactModification() {
        click(By.xpath("(//input[@name='update'])[2]"));
    }
}
