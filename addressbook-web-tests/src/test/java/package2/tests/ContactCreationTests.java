package package2.tests;

import org.testng.annotations.Test;
import package2.model.ContactData;
import package2.model.Contacts;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase{

    @Test
    public void testContactCreation() throws Exception {
        Contacts before = app.contact().all();
        ContactData newContact = new ContactData().withLastName("LastName1").withFirstName("FirstName1")
                .withAddress("Address1").withEmail1("e1@mail.com").withMobilePhone("+7(111)-111-11-11");
        app.contact().create(newContact);
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        Contacts after = app.contact().all();

        //newContact.withId(after.stream().max(Comparator.comparingInt(ContactData::getId)).get().getId());
        //before.add(newContact);
        //Assert.assertEquals(before, after);
        assertThat(after, equalTo(
                before.withAdded(newContact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    }

    @Test
    public void testBadContactCreation() throws Exception {
        Contacts before = app.contact().all();
        ContactData newContact = new ContactData().withLastName("LastName1'").withFirstName("FirstName1").withAddress("Address1").withEmail1("e1@mail.com").withMobilePhone("+7(111)-111-11-11");
        app.contact().create(newContact);
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(before));
    }
}
