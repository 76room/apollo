package org.room.apollo.server.controller;

import org.room.apollo.server.dto.deezer.ChartD;
import org.room.apollo.server.dto.deezer.PlaylistD;
import org.room.apollo.server.dto.deezer.TrackD;
import org.room.apollo.server.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MusicController {

    @Autowired
    private MusicService trackService;

    @GetMapping("/tracks/{id}")
    public TrackD getTrackById(@PathVariable int id){
        return trackService.getTrackFromDeezer(id);
    }

    @GetMapping("/playlist/{id}")
    public PlaylistD getPlaylistById(@PathVariable int id){
        return trackService.getPlaylistFromDeezer(id);
    }

    @GetMapping("/chart")
    public ChartD getChart(){
        return trackService.getChartFromDeezer();
    }
}
