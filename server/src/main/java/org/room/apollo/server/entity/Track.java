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
    private String titie;
    @Field("streaming_url")
    private String streamingUrl;

    public Track(String titie, String streamingUrl) {
        this.titie = titie;
        this.streamingUrl = streamingUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitie() {
        return titie;
    }

    public void setTitie(String titie) {
        this.titie = titie;
    }

    public String getStreamingUrl() {
        return streamingUrl;
    }

    public void setStreamingUrl(String streamingUrl) {
        this.streamingUrl = streamingUrl;
    }
}
