package aiss.videominer.controller;


import aiss.videominer.exception.CaptionNotFoundException;
import aiss.videominer.model.Caption;
import aiss.videominer.repository.CaptionRepository;
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
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

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
    public Caption findCaption(@Parameter(description = "id of the caption to be searched")@PathVariable(value = "id") String id) throws
            CaptionNotFoundException {
        Optional<Caption> caption = repository.findById(id);
        if(caption.isEmpty()){
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
    public List<Caption> findCaptions(
            @Parameter(description = "number of page to be retrieved") @RequestParam(required = false, defaultValue = "0") int page,
            @Parameter(description = "size of page to be retrieved") @RequestParam(required = false, defaultValue = "10") int size,
            @Parameter(description = "language of captions to be retrieved") @RequestParam(required = false) String language,
            @Parameter(description = "parameter to order captions retrieved") @RequestParam(required = false) String order
    ) throws CaptionNotFoundException {
        Pageable paging;
        if(order != null){
            if(order.startsWith("-")){
                paging= PageRequest.of(page, size, Sort.by(order.substring(1)).descending());
            }else{
                paging =PageRequest.of(page, size, Sort.by(order).ascending());
            }
        }else{
            paging= PageRequest.of(page, size);
        }
        Page<Caption> captions;
        if(language!= null){
            captions = repository.findByLanguage(language, paging);
        }else{
            captions = repository.findAll(paging);
        }

        if(captions.isEmpty()){
            throw new CaptionNotFoundException();
        }
        return captions.getContent();
    }

}
