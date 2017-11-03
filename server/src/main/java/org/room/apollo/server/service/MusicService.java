package org.room.apollo.server.service;

import org.room.apollo.server.dto.deezer.ChartD;
import org.room.apollo.server.dto.deezer.PlaylistD;
import org.room.apollo.server.dto.deezer.TrackD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MusicService {

    private String BASIC_URL = "https://api.deezer.com/";

    private RestTemplate template;

    @Autowired
    public MusicService(RestTemplate template) {
        this.template = template;
    }

    public TrackD getTrackFromDeezer(int id){
        String url = BASIC_URL  + "track/" + id;
        return template.getForObject(url,
                TrackD.class);
    }

    public PlaylistD getPlaylistFromDeezer(int id){
        String url = BASIC_URL  + "playlist/" + id;
        return template.getForObject(url,
                PlaylistD.class);
    }

    public ChartD getChartFromDeezer(){
        String url = BASIC_URL  + "chart/";
        return template.getForObject(url,
                ChartD.class);
    }


}
