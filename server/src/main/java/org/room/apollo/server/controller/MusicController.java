package org.room.apollo.server.controller;

import org.room.apollo.server.dto.deezer.ChartD;
import org.room.apollo.server.dto.deezer.PlaylistD;
import org.room.apollo.server.dto.deezer.TrackD;
import org.room.apollo.server.service.MusicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MusicController {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationController.class);

    private final MusicService trackService;

    @Autowired
    public MusicController(MusicService trackService) {
        this.trackService = trackService;
    }

    @GetMapping("/tracks/{id}")
    public TrackD getTrackById(@PathVariable int id){
        LOG.info("Looking for track for id: {}", id);
        TrackD track = trackService.getTrackFromDeezer(id);
        LOG.info("Track for id: {} is: {}", id, track);
        return track;
    }

    @GetMapping("/playlist/{id}")
    public PlaylistD getPlaylistById(@PathVariable int id){
        LOG.info("Looking for playlist for id: {}", id);
        PlaylistD playlist = trackService.getPlaylistFromDeezer(id);
        LOG.info("Playlist for id: {} is: {}", id, playlist);
        return playlist;
    }

    @GetMapping("/chart")
    public ChartD getChart(){
        ChartD chart = trackService.getChartFromDeezer();
        LOG.info("Chart is: {}", chart);
        return chart;
    }
}
