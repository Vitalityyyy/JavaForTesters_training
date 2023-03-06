package package2.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import package2.model.ContactData;
import package2.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {
//    @Parameter(names = "-c", description = "Contact count")
//    public int count;

//    @Parameter(names = "-f", description = "Target file")
//    public String file;

    public static void main (String[] args) throws IOException {
        int count = Integer.parseInt(args[0]);
        File file = new File(args[1]);
        List<ContactData> contacts = generateContacts(count);
        save(contacts, file);

//        ContactDataGenerator generator = new ContactDataGenerator();
//        JCommander jCommander = new JCommander(generator);
//        try {
//            jCommander.parse(args);
//        } catch (ParameterException ex) {
//            jCommander.usage();
//            return;
//        }
//        generator.run();
    }

//    private void run() throws IOException {
//        List<ContactData> contacts = generateContacts(count);
//        save(contacts, new File(file));
//    }

    private static void save(List<ContactData> contacts, File file) throws IOException {
        Writer writer = new FileWriter(file);
        for (ContactData contact : contacts) {
            writer.write(String.format("%s;%s;%s;%s;%s\n", contact.getLastName(), contact.getFirstName(), contact.getAddress(),
                    contact.getEmail1(), contact.getMobilePhone(), contact.getPhoto()));
        }
        writer.close();
    }

    private static List<ContactData> generateContacts(int count) {
        File photo = new File ("src/test/resources/stru.png");
        List<ContactData> contacts = new ArrayList<ContactData>();
        for (int i = 0; i < count; i++){
            contacts.add(new ContactData()
                    .withLastName(String.format("Lastname %s", i))
                    .withFirstName(String.format("Firstname %s", i))
                    .withAddress(String.format("Address %s", i))
                    .withEmail1(String.format("e%s@mail.ru", i))
                    .withMobilePhone(String.format("+7-999-999-99-0%s", i))
                    .withPhoto(photo));
        }
        return contacts;
    }
}
