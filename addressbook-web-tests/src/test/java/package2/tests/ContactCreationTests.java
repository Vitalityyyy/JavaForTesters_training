package package2.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.*;
import package2.model.ContactData;
import package2.model.Contacts;
import package2.model.GroupData;
import package2.model.Groups;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validContactsFromCsv() throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.csv")))) {
            String line = reader.readLine();
            while (line != null) {
                String[] split = line.split((";"));
                list.add(new Object[]{new ContactData()
                        .withLastName(split[0])
                        .withFirstName(split[1])
                        .withAddress(split[2])
                        .withEmail1(split[3])
                        .withMobilePhone(split[4])});
                line = reader.readLine();
            }
            return list.iterator();
        }
    }
    @DataProvider
    public Iterator<Object[]> validContactsFromXml() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/contacts.xml"))) {
            String xml = "";
            String line = reader.readLine();
            while (line != null) {
                xml += line;
                line = reader.readLine();
            }
            XStream xstream = new XStream();
            xstream.processAnnotations(GroupData.class);
            List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml.toString());
            return contacts.stream().map((g) -> new Object[]{g}).toList().iterator();
        }
    }
    @DataProvider
    public Iterator<Object[]> validContactsFromJson() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/contacts.json"))) {
            StringBuilder json = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                json.append(line);
                line = reader.readLine();
            }
            Gson gson = new Gson();
            List<ContactData> contacts = gson.fromJson(json.toString(), new TypeToken<List<ContactData>>() {}.getType());
            return contacts.stream().map((g) -> new Object[]{g}).toList().iterator();
        }
    }
    @DataProvider
    public Iterator<Object[]> invalidContacts() {
        File photo = new File ("src/test/resources/stru.png");
        List<Object[]> list = new ArrayList<Object[]>();
        list.add(new Object[] {new ContactData().withLastName("LastName1'").withFirstName("FirstName1").withAddress("Address1").withEmail1("e1@mail.com").withMobilePhone("+7(111)-111-11-11").withPhoto(photo)});
        list.add(new Object[] {new ContactData().withLastName("LastName2'").withFirstName("FirstName2").withAddress("Address2").withEmail2("e2@mail.com").withMobilePhone("+7(222)-222-22-22").withPhoto(photo)});
        return list.iterator();
    }

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("Group111"));
        }
    }
    @Test(dataProvider = "validContactsFromCsv")
    public void testContactCreation(ContactData contact) throws Exception {
        Groups groups = app.db().groups();
        Contacts before = app.db().contacts();
        contact.withPhoto(new File ("src/test/resources/stru.png")).inGroup(groups.iterator().next());
        app.contact().create(contact);
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        Contacts after = app.db().contacts();
        //contact.withId(after.stream().max(Comparator.comparingInt(ContactData::getId)).get().getId());
        //before.add(contact);
        //Assert.assertEquals(before, after);
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt(ContactData::getId).max().getAsInt()))));
        verifyContactListInUI();
    }

    @Test(dataProvider = "invalidContacts", enabled = false)
    public void testBadContactCreation(ContactData contact) throws Exception {
        Contacts before = app.db().contacts();
        app.contact().create(contact);
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after = app.db().contacts();
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
