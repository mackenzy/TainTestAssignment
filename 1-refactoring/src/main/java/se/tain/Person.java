package se.tain;
import java.util.Date;

public class Person {
    private String NAME;
    private PhoneNumber PhoneNumber;
    private Date date;

    public Person( String name, PhoneNumber phoneNumber ) {
        this.NAME = name;
    }

    public String getName() {
        return NAME;
    }

    public void setName( String name ) {
        name = name;
    }

    public PhoneNumber getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber( PhoneNumber phoneNumber ) {
        this.PhoneNumber = phoneNumber;
    }

}
