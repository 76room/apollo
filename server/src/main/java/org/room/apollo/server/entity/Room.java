package org.room.apollo.server.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by Alexey on 10/9/17.
 */
@Document(collection = "rooms")
public class Room {
    @Id
    private String id;
    private String title;
    @DBRef
    private List<User> users;
    @DBRef
    private List<Track> playlist;

    public Room(String title, List<User> users, List<Track> playlist) {
        this.title = title;
        this.users = users;
        this.playlist = playlist;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Track> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<Track> playlist) {
        this.playlist = playlist;
    }
}
