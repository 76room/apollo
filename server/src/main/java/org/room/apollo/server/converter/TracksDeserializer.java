package org.room.apollo.server.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.room.apollo.server.dto.deezer.TrackD;

import java.io.IOException;
import java.util.List;

public class TracksDeserializer extends JsonDeserializer<List<TrackD>> {

    @Override
    public List<TrackD> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Tracks tracks = p.readValueAs(Tracks.class);
        return tracks.data;
    }

    private static class Tracks{
        public List<TrackD> data;
        public String checksum;
    }

}
