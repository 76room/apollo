package org.room.apollo.server.soundcloud;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by Alexey on 10/5/17.
 */
public class CommentSC {
    private int id;
    private String uri;
    private LocalDate createdAt;
    private String body;
    private long timestamp;
    private int userId;
    private UserSC user;
    private int TrackId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public UserSC getUser() {
        return user;
    }

    public void setUser(UserSC user) {
        this.user = user;
    }

    public int getTrackId() {
        return TrackId;
    }

    public void setTrackId(int trackId) {
        TrackId = trackId;
    }
}
