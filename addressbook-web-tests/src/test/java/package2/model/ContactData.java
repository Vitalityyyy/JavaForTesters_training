package package2.model;

public class ContactData {
    private final String lastName;
    private final String firstName;
    private final String address;
    private final String email;
    private final String phone;



    public ContactData(String lastName, String firstName, String address, String email, String phone) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.email = email;
        this.phone = phone;
    }

    public String getLastName() {
        return lastName;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getAddress() {
        return address;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }
}