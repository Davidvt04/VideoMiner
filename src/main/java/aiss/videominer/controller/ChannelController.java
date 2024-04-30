package aiss.videominer.controller;

import aiss.videominer.exception.ChannelNotFoundException;
import aiss.videominer.exception.GlobalExceptionHandler;
import aiss.videominer.model.Channel;
import aiss.videominer.repository.ChannelRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videominer/channels")
public class ChannelController {

    @Autowired
    ChannelRepository repository;

    @GetMapping("/{id}")
    public Channel findChannel(@PathVariable(value = "id") String id) throws ChannelNotFoundException {
        Optional<Channel> channel = repository.findById(id);
        if(!channel.isPresent()){
            throw new ChannelNotFoundException();
        }
        return channel.get();
    }

    @GetMapping
    public List<Channel> findChannels() throws ChannelNotFoundException{
        List<Channel> channels = new ArrayList<Channel>(repository.findAll());
        if(channels.isEmpty()){
            throw new ChannelNotFoundException();
        }
        return channels;
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Channel create(@Valid @RequestBody Channel channel ){
        Channel _channel = repository.save(new Channel(channel.getId(),channel.getName(), channel.getDescription(), channel.getCreatedTime(), channel.getVideos()));
        return _channel;
    }
}
