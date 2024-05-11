package aiss.videominer.controller;


import aiss.videominer.exception.ChannelNotFoundException;
import aiss.videominer.exception.VideoNotFoundException;
import aiss.videominer.model.Channel;
import aiss.videominer.model.Video;
import aiss.videominer.repository.VideoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Tag(name= "Video", description="Video management API" )
@RestController
@RequestMapping("/videominer/videos")
public class VideoController {

    @Autowired
    VideoRepository repository;
    @Operation(
            summary="Retrieve a video by Id",
            description = "Get a video by specifying its id",
            tags = {"videos", "get"})

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Video.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema)})
    })

    @GetMapping("/{id}")
    public Video findVideo(@Parameter(description = "id of the video to be searched")@PathVariable(value = "id") String id) throws VideoNotFoundException {
        Optional<Video> video = repository.findById(id);
        if(video.isEmpty()){
            throw new VideoNotFoundException();
        }
        return video.get();
    }

    @Operation(
            summary="Retrieve a list of videos",
            description = "Get a list of videos",
            tags = {"videos", "get"})

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Video.class), mediaType = "application/json")})
    })
    @GetMapping
    public List<Video> findVideos(
            @Parameter(description = "number of page to be retrieved") @RequestParam(required = false, defaultValue = "0") int page,
            @Parameter(description = "size of page to be retrieved") @RequestParam(required = false, defaultValue = "10") int size

    ) throws VideoNotFoundException {

        Pageable paging = PageRequest.of(page, size);
        Page<Video> videos = repository.findAll(paging);
        if(videos.isEmpty()){
            throw new VideoNotFoundException();
        }
        return videos.getContent();
    }
}
