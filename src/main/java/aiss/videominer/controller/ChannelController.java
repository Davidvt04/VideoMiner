package aiss.videominer.controller;

import aiss.videominer.exception.ChannelNotFoundException;
import aiss.videominer.exception.GlobalExceptionHandler;
import aiss.videominer.model.Channel;
import aiss.videominer.repository.ChannelRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Tag(name= "Channel", description="Channel managament API" )
@RestController
@RequestMapping("/videominer/channels")
public class ChannelController {

    @Autowired
    ChannelRepository repository;

    @Operation(
            summary="Retrieve a channel by Id",
            description = "Get a channel by specifying its id",
            tags = {"channels", "get"})

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Channel.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema)})
    })

    @GetMapping("/{id}")
    public Channel findChannel(@Parameter(description = "id of the channel to be searched") @PathVariable(value = "id") String id) throws ChannelNotFoundException {
        Optional<Channel> channel = repository.findById(id);
        if(!channel.isPresent()){
            throw new ChannelNotFoundException();
        }
        return channel.get();
    }


    @Operation(
            summary="Retrieve a list of channels",
            description = "Get a list of channels",
            tags = {"channels", "get"})

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Channel.class), mediaType = "application/json")})
    })
    @GetMapping
    public List<Channel> findChannels() throws ChannelNotFoundException{
        List<Channel> channels = new ArrayList<Channel>(repository.findAll());
        if(channels.isEmpty()){
            throw new ChannelNotFoundException();
        }
        return channels;
    }
    @Operation(
            summary = "Insert a Channel ",
            description= "Add a new channel ",
            tags = {"channels", "post"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Channel", content = {@Content(schema = @Schema(implementation = Channel.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema)})})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Channel create(@Valid @RequestBody Channel channel ){
        Channel _channel = repository.save(new Channel(channel.getId(),channel.getName(), channel.getDescription(), channel.getCreatedTime(), channel.getVideos()));
        return _channel;
    }
}
