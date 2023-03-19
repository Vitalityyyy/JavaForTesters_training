package package2.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import package2.model.ContactData;
import package2.model.Contacts;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            app.goTo().contactPage();
            app.contact().create(new ContactData().withLastName("LastName111").withFirstName("FirstName111").withAddress("Address111").withEmail1("e1@mail.com").withMobilePhone("+7(111)-111-11-11"));
        }
    }

    @Test
    public void testContactDeletion() throws Exception {
        Contacts before = app.db().contacts();
        ContactData deletedContact = before.iterator().next();
        app.contact().delete(deletedContact);
        app.goTo().contactPage();
        assertThat(app.contact().count(), equalTo(before.size() - 1));
        Contacts after = app.db().contacts();

        //before.remove(deletedContact);
        //Assert.assertEquals(before, after);
        assertThat(after, equalTo(before.without(deletedContact)));
    }
}
