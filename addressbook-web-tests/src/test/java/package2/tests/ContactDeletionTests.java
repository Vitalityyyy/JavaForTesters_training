package package2.tests;

import org.testng.annotations.Test;
import package2.model.ContactData;
import package2.model.GroupData;


public class ContactDeletionTests extends TestBase{

    @Test
    public void testContactDeletion() throws Exception {
        app.getNavigationHelper().goToContactPage();
        if (! app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("LastName1","FirstName1","Address1","e1@mail.com","+7(111)-111-11-11"));
        }
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectedContact();
        app.getNavigationHelper().goToContactPage();
    }
}
