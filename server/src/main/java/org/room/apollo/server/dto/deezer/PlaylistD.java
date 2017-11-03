package org.room.apollo.server.dto.deezer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.room.apollo.server.converter.LocalDeserializer;
import org.room.apollo.server.converter.TracksDeserializer;

import java.time.LocalDateTime;
import java.util.List;

public class PlaylistD {
    private long id;
    private boolean collaborative;
    private int fans;
    private String link;
    @JsonDeserialize(using = TracksDeserializer.class)
    private List<TrackD> tracks;
    @JsonProperty("is_loved_track")
    private boolean isLovedTrack;
    private String type;
    @JsonProperty("nb_tracks")
    private int nbTracks;
//    private Creator creator;
    @JsonProperty("creation_date")
    @JsonDeserialize(using = LocalDeserializer.class)
    private LocalDateTime creationDate;
    private String title;
    private long duration;
    private String share;
    private String description;
    private String tracklist;
    private int rating;
    @JsonProperty("public")
    private boolean publicity;
    @JsonProperty("picture_xl")
    private String pictureXl;
    @JsonProperty("picture_small")
    private String pictureSmall;
    private String picture;
    @JsonProperty("picture_medium")
    private String pictureMedium;
    @JsonProperty("picture_big")
    private String pictureBig;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isCollaborative() {
        return collaborative;
    }

    public void setCollaborative(boolean collaborative) {
        this.collaborative = collaborative;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<TrackD> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackD> tracks) {
        this.tracks = tracks;
    }

    public boolean isLovedTrack() {
        return isLovedTrack;
    }

    public void setLovedTrack(boolean lovedTrack) {
        isLovedTrack = lovedTrack;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNbTracks() {
        return nbTracks;
    }

    public void setNbTracks(int nbTracks) {
        this.nbTracks = nbTracks;
    }

//    public LocalDate getCreationDate() {
//        return creationDate;
//    }
//
//    public void setCreationDate(LocalDate creationDate) {
//        this.creationDate = creationDate;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTracklist() {
        return tracklist;
    }

    public void setTracklist(String tracklist) {
        this.tracklist = tracklist;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isPublicity() {
        return publicity;
    }

    public void setPublicity(boolean publicity) {
        this.publicity = publicity;
    }

    public String getPictureXl() {
        return pictureXl;
    }

    public void setPictureXl(String pictureXl) {
        this.pictureXl = pictureXl;
    }

    public String getPictureSmall() {
        return pictureSmall;
    }

    public void setPictureSmall(String pictureSmall) {
        this.pictureSmall = pictureSmall;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPictureMedium() {
        return pictureMedium;
    }

    public void setPictureMedium(String pictureMedium) {
        this.pictureMedium = pictureMedium;
    }

    public String getPictureBig() {
        return pictureBig;
    }

    public void setPictureBig(String pictureBig) {
        this.pictureBig = pictureBig;
    }
}