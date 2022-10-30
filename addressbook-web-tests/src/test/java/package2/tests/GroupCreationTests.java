package package2.tests;

import org.testng.annotations.*;
import package2.model.GroupData;


public class GroupCreationTests extends TestBase{

    @Test
    public void testGroupCreation() throws Exception {
        app.getNavigationHelper().goToGroupPage();
        app.getGroupHelper().createGroup(new GroupData("Group1", null, null));
    }
}
