package package2.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import package2.model.ContactData;
import java.util.ArrayList;
import java.util.List;

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
    public void selectContact(int index) {
        driver.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
    }
    public void deleteSelectedContact() {
        click(By.xpath("(//input[@name='update'])[3]"));
    }
    public void submitContactModification() {
        click(By.xpath("(//input[@name='update'])[2]"));
    }

    public boolean isThereAContact() {
        return isElementPresent(By.xpath("//img[@alt='Edit']"));
    }

    public void createContact(ContactData contactData) {
        initContactCreation();
        fillContactForm(contactData);
        submitContactCreation();
        returnToContactPage();
    }

    public int getContactCount() {
        return driver.findElements(By.name("entry")).size();
    }

    public List<ContactData> getContactList() {
        List<ContactData> contacts = new ArrayList<ContactData>();
        List<WebElement> elements = driver.findElements(By.name("entry"));
        for (WebElement element : elements) {
            String lastName = element.findElements(By.tagName("td")).get(1).getText();
            String firstName = element.findElements(By.tagName("td")).get(2).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));
            ContactData contact = new ContactData(id, lastName, firstName, null, null, null);
            contacts.add(contact);
        }
        return contacts;
    }
}
