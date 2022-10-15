package package2.tests;

import org.testng.annotations.Test;
import package2.model.ContactData;


public class ContactDeletionTests extends TestBase{

    @Test
    public void testContactDeletion() throws Exception {
        app.getNavigationHelper().goToContactPage();
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectedContact();
        app.getNavigationHelper().goToContactPage();
    }
}
