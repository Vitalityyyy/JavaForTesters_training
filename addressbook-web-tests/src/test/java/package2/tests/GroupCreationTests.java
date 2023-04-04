package package2.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.*;
import package2.model.GroupData;
import package2.model.Groups;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class GroupCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validGroupsFromXml() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/groups.xml"))) {
            StringBuilder xml = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                xml.append(line);
                line = reader.readLine();
            }
            XStream xstream = new XStream();
            xstream.processAnnotations(GroupData.class);
            List<GroupData> groups = (List<GroupData>) xstream.fromXML(xml.toString());
            return groups.stream().map((g) -> new Object[]{g}).toList().iterator();
        }
    }
    @DataProvider
    public Iterator<Object[]> validGroupsFromJson() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/groups.json"))) {
            StringBuilder json = new StringBuilder();
            String line = reader.readLine();
            while (line != null) {
                json.append(line);
                line = reader.readLine();
            }
            Gson gson = new Gson();
            List<GroupData> groups = gson.fromJson(json.toString(), new TypeToken<List<GroupData>>() {}.getType());
            return groups.stream().map((g) -> new Object[]{g}).toList().iterator();
        }
    }
    @DataProvider
    public Iterator<Object[]> invalidGroups() {
        List<Object[]> list = new ArrayList<Object[]>();
        list.add(new Object[] {new GroupData().withName("test1'").withHeader("header 1").withFooter("footer 1")});
        list.add(new Object[] {new GroupData().withName("test2'").withHeader("header 2").withFooter("footer 2")});
        return list.iterator();
    }

    @Test(dataProvider = "validGroupsFromJson")
    public void testGroupCreation(GroupData group) throws Exception {
        app.goTo().groupPage();
        Groups before = app.db().groups();
        app.group().create(group);
        assertThat(app.group().count(), equalTo(before.size() + 1));
        Groups after = app.db().groups();

        //group.withId(after.stream().max(Comparator.comparingInt(GroupData::getId)).get().getId());
        //before.add(group);
        //Comparator<? super GroupData> byId = Comparator.comparingInt(GroupData::getId);
        //before.sort(byId);
        //after.sort(byId);
        //Assert.assertEquals(before, after);
        assertThat(after, equalTo(
                before.withAdded(group.withId(after.stream().mapToInt(GroupData::getId).max().getAsInt()))));
        verifyGroupListInUI();
    }
    @Test(dataProvider = "invalidGroups", enabled = false)
    public void testBadGroupCreation(GroupData group) throws Exception {
        app.goTo().groupPage();
        Groups before = app.db().groups();
        app.group().create(group);
        assertThat(app.group().count(), equalTo(before.size()));
        Groups after = app.db().groups();
        assertThat(after, equalTo(before));
    }
}
