package package2.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import package2.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;


public class GroupCreationTests extends TestBase{

    @Test
    public void testGroupCreation() throws Exception {
        app.getNavigationHelper().goToGroupPage();
        List<GroupData> before = app.getGroupHelper().getGroupList();
        GroupData newGroup = new GroupData("Group111", null, null);
        app.getGroupHelper().createGroup(newGroup);
        List<GroupData> after = app.getGroupHelper().getGroupList();
        Assert.assertEquals(after.size(), before.size() + 1);

        //newGroup.setId(after.stream().max(Comparator.comparingInt(GroupData::getId)).get().getId());
        before.add(newGroup);
        Comparator<? super GroupData> byId = Comparator.comparingInt(GroupData::getId);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}
