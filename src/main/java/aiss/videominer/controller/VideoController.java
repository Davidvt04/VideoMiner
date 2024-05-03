package aiss.videominer.controller;


import aiss.videominer.exception.ChannelNotFoundException;
import aiss.videominer.exception.VideoNotFoundException;
import aiss.videominer.model.Channel;
import aiss.videominer.model.Video;
import aiss.videominer.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


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
    public List<Video> findChannels(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size

    ) throws VideoNotFoundException {
        Pageable paging = PageRequest.of(page, size);
        Page<Video> videos = repository.findAll(paging);
        if(videos.isEmpty()){
            throw new VideoNotFoundException();
        }
        return videos.getContent();
    }
}
