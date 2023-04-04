package package2.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import package2.model.ContactData;
import package2.model.Contacts;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            app.goTo().contactPage();
            app.contact().create(new ContactData().withLastName("LastName111").withFirstName("FirstName111").withAddress("Address111").withEmail1("e1@mail.com").withMobilePhone("+7(111)-111-11-11"));
        }
    }

    @Test
    public void testContactModification() throws Exception {
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId()).withLastName("LastName2").withFirstName("FirstName2").withAddress("Address2").withEmail1("e2@mail.com").withMobilePhone("+7(222)-222-22-22");
        app.goTo().contactPage();
        app.contact().modify(contact);
        app.goTo().contactPage();
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after = app.db().contacts();

        //before.remove(modifiedContact);
        //before.add(contact);
        //Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
        //before.sort(byId);
        //after.sort(byId);
        //Assert.assertEquals(before, after);
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
        verifyContactListInUI();
    }
}
