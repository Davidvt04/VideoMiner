package aiss.videominer.controller;


import aiss.videominer.exception.ChannelNotFoundException;
import aiss.videominer.exception.VideoNotFoundException;
import aiss.videominer.model.Channel;
import aiss.videominer.model.Video;
import aiss.videominer.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videominer/videos")
public class VideoController {

    @Autowired
    VideoRepository repository;

    @GetMapping("/{id}")
    public Video findChannel(@PathVariable(value = "id") String id) throws VideoNotFoundException {
        Optional<Video> video = repository.findById(id);
        if(!video.isPresent()){
            throw new VideoNotFoundException();
        }
        return video.get();
    }

    @GetMapping
    public List<Video> findChannels() throws VideoNotFoundException {
        List<Video> videos = new ArrayList<Video>(repository.findAll());
        if(videos.isEmpty()){
            throw new VideoNotFoundException();
        }
        return videos;
    }
}
