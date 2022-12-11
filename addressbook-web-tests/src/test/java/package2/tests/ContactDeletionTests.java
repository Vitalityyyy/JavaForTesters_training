package package2.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import package2.model.ContactData;
import package2.model.GroupData;

import java.util.List;


public class ContactDeletionTests extends TestBase{

    @Test
    public void testContactDeletion() throws Exception {
        app.getNavigationHelper().goToContactPage();
        if (! app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("LastName1","FirstName1","Address1","e1@mail.com","+7(111)-111-11-11"));
        }
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().selectContact(before.size() - 1);
        app.getContactHelper().deleteSelectedContact();
        app.getNavigationHelper().goToContactPage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(before.size() - 1);
        Assert.assertEquals(before, after);
    }
}
