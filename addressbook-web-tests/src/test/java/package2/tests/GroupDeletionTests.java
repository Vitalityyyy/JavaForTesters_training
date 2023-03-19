package package2.tests;

import org.testng.annotations.*;
import package2.model.GroupData;
import package2.model.Groups;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("Group111"));
        }
    }

    @Test
    public void testGroupDeletion() throws Exception {
        Groups before = app.db().groups();
        GroupData deletedGroup = before.iterator().next();
        app.group().delete(deletedGroup);
        assertThat(app.group().count(), equalTo(before.size() - 1));
        Groups after = app.db().groups();

        //before.remove(deletedGroup);
        //Assert.assertEquals(before, after);
        assertThat(after, equalTo(before.without(deletedGroup)));
    }
}
