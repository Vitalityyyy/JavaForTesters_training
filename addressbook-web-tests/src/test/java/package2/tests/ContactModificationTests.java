package package2.tests;

import org.testng.annotations.Test;
import package2.model.ContactData;


public class ContactModificationTests extends TestBase{

    @Test
    public void testContactModification() throws Exception {
        app.getNavigationHelper().goToContactPage();
        app.getContactHelper().selectContact();
        app.getContactHelper().fillContactForm(new ContactData("LastName2","FirstName2","Address2","e2@mail.com","+7(222)-222-22-22"));
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().goToContactPage();
    }
}
