package package2.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import package2.model.ContactData;
import package2.model.Contacts;
import package2.model.GroupData;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase{
    @DataProvider
    public Iterator<Object[]> validContacts() throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.csv")));
        String line = reader.readLine();
        while (line != null) {
            String[] split = line.split((";"));
            list.add(new Object[] {new ContactData()
                    .withLastName(split[0])
                    .withFirstName(split[1])
                    .withAddress(split[2])
                    .withEmail1(split[3])
                    .withMobilePhone(split[4])});
            line = reader.readLine();
        }
        return list.iterator();
    }
    @DataProvider
    public Iterator<Object[]> invalidContacts() {
        File photo = new File ("src/test/resources/stru.png");
        List<Object[]> list = new ArrayList<Object[]>();
        list.add(new Object[] {new ContactData().withLastName("LastName1'").withFirstName("FirstName1").withAddress("Address1").withEmail1("e1@mail.com").withMobilePhone("+7(111)-111-11-11").withPhoto(photo)});
        list.add(new Object[] {new ContactData().withLastName("LastName2'").withFirstName("FirstName2").withAddress("Address2").withEmail2("e2@mail.com").withMobilePhone("+7(222)-222-22-22").withPhoto(photo)});
        return list.iterator();
    }

    @Test(dataProvider = "validContacts")
    public void testContactCreation(ContactData contact) throws Exception {
        Contacts before = app.contact().all();
        contact.withPhoto(new File ("src/test/resources/stru.png"));
        app.contact().create(contact);
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        Contacts after = app.contact().all();
        //contact.withId(after.stream().max(Comparator.comparingInt(ContactData::getId)).get().getId());
        //before.add(contact);
        //Assert.assertEquals(before, after);
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    }

    @Test(dataProvider = "invalidContacts")
    public void testBadContactCreation(ContactData contact) throws Exception {
        Contacts before = app.contact().all();
        app.contact().create(contact);
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(before));
    }

    @Test(enabled = false)
    public void testCurrentDir() {
        File currentDir = new File(".");
        System.out.println(currentDir.getAbsolutePath());
        File photo = new File("src/test/resources/stru.png");
        System.out.println(photo.getAbsolutePath());
        System.out.println(photo.exists());
    }
}
