package org.room.apollo.server.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Alexey on 10/9/17.
 */
@Document(collection = "rooms")
public class Room {
    public enum Status{
        PUBLIC, PRIVATE
    }

    @Id
    private String id;
    private String title;
    private String description;
    private Status status;
    private User admin;
    @DBRef
    private List<User> users = new ArrayList<>();
//    @DBRef
//    private List<User> invitedUsers = new ArrayList<>();
    @DBRef
    private Queue<Track> playlist;

    public Room(){}

    public Room(String title, String description, Status status, User admin, List<User> users) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.admin = admin;
        this.users = users;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

//    public List<User> getInvitedUsers() {
//        return invitedUsers;
//    }
//
//    public void setInvitedUsers(List<User> invitedUsers) {
//        this.invitedUsers = invitedUsers;
//    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Queue<Track> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Queue<Track> playlist) {
        this.playlist = playlist;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        users.add(admin);
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", admin=" + admin +
                ", users=" + users +
//                ", invitedUsers=" + invitedUsers +
                ", playlist=" + playlist +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (id != null ? !id.equals(room.id) : room.id != null) return false;
        if (!title.equals(room.title)) return false;
        if (users != null ? !users.equals(room.users) : room.users != null) return false;
        return playlist != null ? playlist.equals(room.playlist) : room.playlist == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + title.hashCode();
        result = 31 * result + (users != null ? users.hashCode() : 0);
        result = 31 * result + (playlist != null ? playlist.hashCode() : 0);
        return result;
    }
}
