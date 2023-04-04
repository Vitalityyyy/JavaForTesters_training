package package2.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import package2.model.ContactData;
import package2.model.Contacts;
import package2.model.GroupData;
import package2.model.Groups;

public class RemoveContactFromGroupTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("Group111"));
        }
        if (app.db().contacts().size() == 0) {
            app.goTo().contactPage();
            app.contact().create(new ContactData().withLastName("LastName111").withFirstName("FirstName111").withAddress("Address111").withEmail1("e1@mail.com").withMobilePhone("+7(111)-111-11-11"));
        }
    }

    @Test
    public void testRemoveContactFromGroup() {
        Groups groups = app.db().groups();
        GroupData group = groups.iterator().next();

        Contacts contacts = app.contact().all();
        app.contact().filterByGroup(group);
        Contacts grContactsBefore = app.contact().all();

        ContactData contact;
        if (grContactsBefore.size() == 0) {
            contact = contacts.iterator().next();
            app.goTo().contactPage();
            app.contact().filterBy("[all]");
            app.contact().addGroupToContact(contact, group);
            grContactsBefore.withAdded(contact);
        } else {
            contact = grContactsBefore.iterator().next();
        }
        app.goTo().contactPage();
        app.contact().filterByGroup(group);
        app.contact().removeContactFromGroup(contact, group);
        app.goTo().contactPage();
        app.contact().verifyContactIsRemovedFromGroup(contact, grContactsBefore);
    }
}
