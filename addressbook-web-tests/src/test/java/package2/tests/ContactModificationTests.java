package package2.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import package2.model.ContactData;
import package2.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;


public class ContactModificationTests extends TestBase{

    @Test
    public void testContactModification() throws Exception {
        app.getNavigationHelper().goToContactPage();
        if (! app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("LastName111","FirstName111","Address1","e1@mail.com","+7(111)-111-11-11"));
        }
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().selectContact(before.size() - 1);
        ContactData editedContact = new ContactData(before.get(before.size()-1).getId(),"LastName2","FirstName2","Address2","e2@mail.com","+7(222)-222-22-22");
        app.getContactHelper().fillContactForm(editedContact);
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().goToContactPage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());

        before.remove(before.size() - 1);
        before.add(editedContact);
        Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}
