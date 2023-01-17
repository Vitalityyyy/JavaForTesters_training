package package2.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import package2.model.GroupData;
import package2.model.Groups;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("Group111"));
        }
    }

    @Test
    public void testGroupModification() {
        Groups before = app.group().all();
        GroupData modifiedGroup = before.iterator().next();
        GroupData group = new GroupData().withId(modifiedGroup.getId()).withName("Group2").withHeader("Header2").withFooter("Footer2");
        app.group().modify(group);
        assertThat(app.group().count(), equalTo(before.size()));
        Groups after = app.group().all();

        //before.remove(modifiedGroup);
        //before.add(group);
        //Comparator<? super GroupData> byId = Comparator.comparingInt(GroupData::getId);
        //before.sort(byId);
        //after.sort(byId);
        //Assert.assertEquals(before, after);
        assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));
    }
}
