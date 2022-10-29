package package2.tests;

import org.testng.annotations.Test;
import package2.model.ContactData;
import package2.model.GroupData;


public class ContactCreationTests extends TestBase{

    @Test
    public void testContactCreation() throws Exception {
        app.getContactHelper().initContactCreation();
        app.getContactHelper().fillContactForm(new ContactData("LastName1","FirstName1","Address1","e1@mail.com","+7(111)-111-11-11"));
        app.getContactHelper().submitContactCreation();
        app.getNavigationHelper().goToContactPage();
    }
}
