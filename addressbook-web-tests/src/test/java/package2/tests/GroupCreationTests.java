package package2.tests;

import org.testng.annotations.*;
import package2.model.GroupData;
import package2.model.Groups;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class GroupCreationTests extends TestBase{

    @Test
    public void testGroupCreation() throws Exception {
        app.goTo().groupPage();
        Groups before = app.group().all();
        GroupData newGroup = new GroupData().withName("Group1");
        app.group().create(newGroup);
        assertThat(app.group().count(), equalTo(before.size() + 1));
        Groups after = app.group().all();

        //newGroup.withId(after.stream().max(Comparator.comparingInt(GroupData::getId)).get().getId());
        //before.add(newGroup);
        //Comparator<? super GroupData> byId = Comparator.comparingInt(GroupData::getId);
        //before.sort(byId);
        //after.sort(byId);
        //Assert.assertEquals(before, after);
        assertThat(after, equalTo(
                before.withAdded(newGroup.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    }
    @Test
    public void testBadGroupCreation() throws Exception {
        app.goTo().groupPage();
        Groups before = app.group().all();
        GroupData newGroup = new GroupData().withName("Group1'");
        app.group().create(newGroup);
        assertThat(app.group().count(), equalTo(before.size()));
        Groups after = app.group().all();
        assertThat(after, equalTo(before));
    }
}
