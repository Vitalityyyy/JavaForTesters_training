package package2.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import package2.model.ContactData;
import package2.model.Contacts;
import package2.model.GroupData;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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
    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("lastname"), contactData.getLastName());
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("address"), contactData.getAddress());
        type(By.name("email"), contactData.getEmail1());
        type(By.name("email2"), contactData.getEmail2());
        type(By.name("email3"), contactData.getEmail3());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("work"), contactData.getWorkPhone());
        attach(By.name("photo"), contactData.getPhoto());
        if (creation && contactData.getGroups().size() > 0) {
            Assert.assertTrue(contactData.getGroups().size() == 1);
            new Select(driver.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
        } else {
//            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }
    public void submitContactCreation() {
        click(By.name("submit"));
    }

    private void initContactModificationById(int id) {
        //driver.findElement(By.cssSelector("a[href=\"edit.php?id=" + id + "\"]")).click();
        driver.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
    }

    public void deleteSelectedContact() {
        click(By.xpath("(//input[@name='update'])[3]"));
    }
    public void submitContactModification() {
        click(By.xpath("(//input[@name='update'])[2]"));
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public void create(ContactData contactData) {
        filterBy("[all]");
        initContactCreation();
        fillContactForm(contactData, true);
        submitContactCreation();
        returnToContactPage();
        contactCache = null;
    }

    public void modify(ContactData contact) {
        initContactModificationById(contact.getId());
        fillContactForm(contact, false);
        submitContactModification();
        contactCache = null;
    }

    public void delete(ContactData contact) {
        initContactModificationById(contact.getId());
        deleteSelectedContact();
        contactCache = null;
    }

    public int count() {
        return driver.findElements(By.name("entry")).size();
    }

    private Contacts contactCache = null;

    public Contacts all() {
        if (contactCache != null) {
            return new Contacts(contactCache);
        }
        contactCache = new Contacts();
        List<WebElement> rows = driver.findElements(By.name("entry"));
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            String lastName = cells.get(1).getText();
            String firstName = cells.get(2).getText();
            int id = Integer.parseInt(row.findElement(By.tagName("input")).getAttribute("id"));
            String address = cells.get(3).getText();
            String allphones = cells.get(5).getText();
            String allemails = cells.get(4).getText();
            contactCache.add(new ContactData().withId(id).withLastName(lastName).withFirstName(firstName)
                    .withAddress(address).withAllPhones(allphones).withAllEmails(allemails));
        }
        return new Contacts(contactCache);
    }

    public ContactData infoFromEditForm(ContactData contact) {
        initContactModificationById(contact.getId());
        String firstname = driver.findElement(By.name("firstname")).getAttribute("value");
        String lastname = driver.findElement(By.name("lastname")).getAttribute("value");
        String address = driver.findElement(By.name("address")).getAttribute("value");
        String home = driver.findElement(By.name("home")).getAttribute("value");
        String mobile = driver.findElement(By.name("mobile")).getAttribute("value");
        String work = driver.findElement(By.name("work")).getAttribute("value");
        String email1 = driver.findElement(By.name("email")).getAttribute("value");
        String email2 = driver.findElement(By.name("email2")).getAttribute("value");
        String email3 = driver.findElement(By.name("email3")).getAttribute("value");
        driver.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstName(firstname).withLastName(lastname).withAddress(address)
                .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work).withEmail1(email1).withEmail2(email2).withEmail3(email3);
    }

    public void addGroupToContact(ContactData contact, GroupData group) {
        click(By.cssSelector(String.format("[type=checkbox][value='%s']", contact.getId())));
        new Select(driver.findElement(By.name("to_group"))).selectByVisibleText(group.getName());
        click(By.name("add"));
    }
    public void removeContactFromGroup(ContactData contact, GroupData group) {
        new Select(driver.findElement(By.name("group"))).selectByVisibleText(group.getName());
        click(By.cssSelector(String.format("[type=checkbox][value='%s']", contact.getId())));
        click(By.name("remove"));
    }

    public void filterByGroup(GroupData group) {
        new Select(driver.findElement(By.name("group"))).selectByVisibleText(group.getName());
        contactCache = null;;
    }

    public void filterBy(String option) {
        new Select(driver.findElement(By.name("group"))).selectByVisibleText(option);
        contactCache = null;;
    }

    public Contacts getContactsNotInGroup(Contacts contacts, Contacts grContacts) {
        contacts.removeAll(grContacts);
        return contacts;
    }

    public int getId(String lastName) {
        WebElement checkbox = driver.findElement(By.xpath("//td[contains(text(), '" + lastName + "')]/ancestor::tr[@name='entry']//input[@type='checkbox']"));
        int id = Integer.parseInt(checkbox.getAttribute("value"));
        return id;
    }

    public void verifyContactIsAddedToGroup(ContactData contact, Contacts grContactsBefore) {
        Contacts grContactsAfter = all();
        assertThat(grContactsAfter, equalTo(grContactsBefore.withAdded(contact).stream().map((c) -> new ContactData()
                .withId(c.getId())
                .withLastName(c.getLastName())
                .withFirstName(c.getFirstName())
                .withAddress(c.getAddress())
                .withAllEmails(c.getAllEmails())
                .withAllPhones(c.getAllPhones())).collect(Collectors.toSet())));
    }

    public void verifyContactIsRemovedFromGroup(ContactData contact, Contacts grContactsBefore) {
        Contacts grContactsAfter = all();
        assertThat(grContactsAfter, equalTo(grContactsBefore.without(contact)));
    }
}
