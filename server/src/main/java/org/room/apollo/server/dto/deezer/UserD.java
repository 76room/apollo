package org.room.apollo.server.dto.deezer;

public class UserD {

    private String name;
    private String email;

    public UserD() {
    }

    public UserD(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "DeezerUser{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
