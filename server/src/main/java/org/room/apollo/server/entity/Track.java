package org.room.apollo.server.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by Alexey on 10/9/17.
 */
@Document(collection = "tracks")
public class Track {
    @Id
    private String id;
    private String title;
    @Field("streaming_url")
    private String streamingUrl;

    public Track(String title, String streamingUrl) {
        this.title = title;
        this.streamingUrl = streamingUrl;
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

    public String getStreamingUrl() {
        return streamingUrl;
    }

    public void setStreamingUrl(String streamingUrl) {
        this.streamingUrl = streamingUrl;
    }

    @Override
    public String toString() {
        return "Track{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", streamingUrl='" + streamingUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Track track = (Track) o;

        if (id != null ? !id.equals(track.id) : track.id != null) return false;
        if (!title.equals(track.title)) return false;
        return streamingUrl != null ? streamingUrl.equals(track.streamingUrl) : track.streamingUrl == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + title.hashCode();
        result = 31 * result + (streamingUrl != null ? streamingUrl.hashCode() : 0);
        return result;
    }
}
