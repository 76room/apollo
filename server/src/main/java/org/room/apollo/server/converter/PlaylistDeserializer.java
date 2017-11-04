package org.room.apollo.server.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.room.apollo.server.dto.deezer.PlaylistD;

import java.io.IOException;
import java.util.List;

public class PlaylistDeserializer extends JsonDeserializer<List<PlaylistD>> {

    @Override
    public List<PlaylistD> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Playlists tracks = p.readValueAs(Playlists.class);
        return tracks.data;
    }

    private static class Playlists {
        public List<PlaylistD> data;
        public String checksum;
    }

}
