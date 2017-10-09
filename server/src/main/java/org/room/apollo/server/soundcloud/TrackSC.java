package org.room.apollo.server.soundcloud;

/**
 * Created by Alexey on 10/5/17.
 */
public class TrackSC extends BaseSoundType{
    private State state;
    private String license;
    private Type type;
    private String waveformUrl;
    private String downloadUrl;
    private String streamUrl;
    private String videoUrl;
    private int bmp;
    private boolean commentable;
    private String isrc;
    private String keySign;
    private int commentsCount;
    private int downloadCount;
    private int playbackCount;
    private int favoritingsCount;
    private String originalFormat;
    private String originalContentSize;
    //asset_data, artwork_data
    private boolean userFavorite;

    public enum State{
        PROCESSING,FAILED,FINISHED
    }
    public enum Type{
        ORIGINAL, REMIX, LIVE, RECORDING, SPOKEN, PODCAST,INPROGRESS, STEM, LOOP, SOUND_EFFECT, SAMPLE, OTHER
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getWaveformUrl() {
        return waveformUrl;
    }

    public void setWaveformUrl(String waveformUrl) {
        this.waveformUrl = waveformUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getBmp() {
        return bmp;
    }

    public void setBmp(int bmp) {
        this.bmp = bmp;
    }

    public boolean isCommentable() {
        return commentable;
    }

    public void setCommentable(boolean commentable) {
        this.commentable = commentable;
    }

    public String getIsrc() {
        return isrc;
    }

    public void setIsrc(String isrc) {
        this.isrc = isrc;
    }

    public String getKeySign() {
        return keySign;
    }

    public void setKeySign(String keySign) {
        this.keySign = keySign;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public int getPlaybackCount() {
        return playbackCount;
    }

    public void setPlaybackCount(int playbackCount) {
        this.playbackCount = playbackCount;
    }

    public int getFavoritingsCount() {
        return favoritingsCount;
    }

    public void setFavoritingsCount(int favoritingsCount) {
        this.favoritingsCount = favoritingsCount;
    }

    public String getOriginalFormat() {
        return originalFormat;
    }

    public void setOriginalFormat(String originalFormat) {
        this.originalFormat = originalFormat;
    }

    public String getOriginalContentSize() {
        return originalContentSize;
    }

    public void setOriginalContentSize(String originalContentSize) {
        this.originalContentSize = originalContentSize;
    }

    public boolean isUserFavorite() {
        return userFavorite;
    }

    public void setUserFavorite(boolean userFavorite) {
        this.userFavorite = userFavorite;
    }
}
