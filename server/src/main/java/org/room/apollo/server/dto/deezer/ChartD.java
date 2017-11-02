package org.room.apollo.server.dto.deezer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.room.apollo.server.converter.PlaylistDeserializer;
import org.room.apollo.server.converter.TracksDeserializer;

import java.util.List;

public class ChartD {
    @JsonDeserialize(using = TracksDeserializer.class)
    private List<TrackD> tracks;
    @JsonDeserialize(using = PlaylistDeserializer.class)
    private List<PlaylistD> playlists;
//    private List<ArtistD> playlists;
//    private List<AlbumD> playlists;


    public List<TrackD> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackD> tracks) {
        this.tracks = tracks;
    }

    public List<PlaylistD> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<PlaylistD> playlists) {
        this.playlists = playlists;
    }
}
