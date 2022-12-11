package package2.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import package2.model.ContactData;
import package2.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;


public class ContactCreationTests extends TestBase{

    @Test
    public void testContactCreation() throws Exception {
        List<ContactData> before = app.getContactHelper().getContactList();
        ContactData newContact = new ContactData("LastName111","FirstName111","Address1","e1@mail.com","+7(111)-111-11-11");
        app.getContactHelper().createContact(newContact);
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size() + 1);

        newContact.setId(after.stream().max(Comparator.comparingInt(ContactData::getId)).get().getId());
        before.add(newContact);
        Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));
    }
}
