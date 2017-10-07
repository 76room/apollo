package org.room.apollo.server.entity;

import java.util.Date;

/**
 * Created by Alexey on 10/7/17.
 */
public abstract class BaseSoundType {
    private int id;
    private Date createdAt;
    private int userId;
    private User user;
    private String title;
    private String permalink;
    private String permalinkUrl;
    private String uri;
    private Track.Sharing sharing;
    private Track.EmbeddableBy embeddableBy;
    private String purchaseUrl;
    private String atworkUrl;
    private String description;
    private String label;
    private long duration;
    private String genre;
    private String tagList;
    private int labelId;
    private String labelName;
    private int release;
    private int releaseDay;
    private int releaseMounth;
    private int releaseYear;
    private boolean streamable;
    private boolean downloadable;

    public enum Sharing{
        PUBLIC, PRIVATE
    }
    public enum EmbeddableBy{
        ME,ALL,NONE
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public String getPermalinkUrl() {
        return permalinkUrl;
    }

    public void setPermalinkUrl(String permalinkUrl) {
        this.permalinkUrl = permalinkUrl;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Track.Sharing getSharing() {
        return sharing;
    }

    public void setSharing(Track.Sharing sharing) {
        this.sharing = sharing;
    }

    public Track.EmbeddableBy getEmbeddableBy() {
        return embeddableBy;
    }

    public void setEmbeddableBy(Track.EmbeddableBy embeddableBy) {
        this.embeddableBy = embeddableBy;
    }

    public String getPurchaseUrl() {
        return purchaseUrl;
    }

    public void setPurchaseUrl(String purchaseUrl) {
        this.purchaseUrl = purchaseUrl;
    }

    public String getAtworkUrl() {
        return atworkUrl;
    }

    public void setAtworkUrl(String atworkUrl) {
        this.atworkUrl = atworkUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTagList() {
        return tagList;
    }

    public void setTagList(String tagList) {
        this.tagList = tagList;
    }

    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(int labelId) {
        this.labelId = labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public int getRelease() {
        return release;
    }

    public void setRelease(int release) {
        this.release = release;
    }

    public int getReleaseDay() {
        return releaseDay;
    }

    public void setReleaseDay(int releaseDay) {
        this.releaseDay = releaseDay;
    }

    public int getReleaseMounth() {
        return releaseMounth;
    }

    public void setReleaseMounth(int releaseMounth) {
        this.releaseMounth = releaseMounth;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public boolean isStreamable() {
        return streamable;
    }

    public void setStreamable(boolean streamable) {
        this.streamable = streamable;
    }

    public boolean isDownloadable() {
        return downloadable;
    }

    public void setDownloadable(boolean downloadable) {
        this.downloadable = downloadable;
    }
}
