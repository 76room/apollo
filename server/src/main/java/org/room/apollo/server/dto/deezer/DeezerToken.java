package org.room.apollo.server.dto.deezer;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Deezer token DTO.
 */
public class DeezerToken {

    /**
     * Deezer access token.
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * Seconds until expires. 0 is not expires.
     */
    private long expires;

    public DeezerToken() {
    }

    public DeezerToken(String accessToken, long expires) {
        this.accessToken = accessToken;
        this.expires = expires;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeezerToken)) return false;

        DeezerToken that = (DeezerToken) o;

        if (expires != that.expires) return false;
        return accessToken != null ? accessToken.equals(that.accessToken) : that.accessToken == null;
    }

    @Override
    public int hashCode() {
        int result = accessToken != null ? accessToken.hashCode() : 0;
        result = 31 * result + (int) (expires ^ (expires >>> 32));
        return result;
    }
}
