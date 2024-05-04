package aiss.videominer.controller;


import aiss.videominer.exception.CaptionNotFoundException;
import aiss.videominer.model.Caption;
import aiss.videominer.model.Channel;
import aiss.videominer.repository.CaptionRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Tag(name= "Caption", description="Caption managament API" )
@RestController
@RequestMapping("/videominer/captions")
public class CaptionController {

    @Autowired
    CaptionRepository repository;
    @Operation(
            summary="Retrieve a caption by Id",
            description = "Get a caption by specifying its id",
            tags = {"captions", "get"})

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Caption.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema)})
    })
    @GetMapping("/{id}")
    public Caption findCaption(@Parameter(description = "id of the caption to be searched")@PathVariable(value = "id") String id) throws CaptionNotFoundException {
        Optional<Caption> caption = repository.findById(id);
        if(!caption.isPresent()){
            throw new   CaptionNotFoundException();
        }
        return caption.get();
    }

    @Operation(
            summary="Retrieve a list of captions",
            description = "Get a list of captions",
            tags = {"caption", "get"})

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Caption.class), mediaType = "application/json")})
    })
    @GetMapping
    public List<Caption> findCaptions() throws CaptionNotFoundException {
        List<Caption> captions = new ArrayList<Caption>(repository.findAll());
        if(captions.isEmpty()){
            throw new CaptionNotFoundException();
        }
        return captions;
    }

}
