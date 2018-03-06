package io.github.seibelsabrina.sheltermefb;

/**
 * Created by seibelsabrina on 2/24/18.
 */

public class Person {
    String personId;
    String name;
    String username;
    String password;
    String mode;


    public Person() {

    }

    public Person(String personId, String name, String username, String password, String mode) {
        this.personId = personId;
        this.name = name;
        this.username = username;
        this.password = password;
        this.mode = mode;
    }

    public String getPersonId() {
        return personId;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMode() {
        return mode;
    }
}
