package aiss.videominer.controller;

import aiss.videominer.exception.ChannelNotFoundException;
import aiss.videominer.exception.CommentNotFoundException;
import aiss.videominer.exception.MinException;
import aiss.videominer.model.Channel;
import aiss.videominer.model.Comment;
import aiss.videominer.repository.CommentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Tag(name= "Comment", description="Comment management API" )
@RestController
@RequestMapping("/videominer/comments")
public class CommentController {

    @Autowired
    CommentRepository repository;
    @Operation(
            summary="Retrieve a comment by Id",
            description = "Get a comment by specifying its id",
            tags = {"comments", "get"})

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Comment.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema)})
    })
    @GetMapping("/{id}")
    public Comment findComment(@Parameter(description = "id of the comment to be searched")@PathVariable(value = "id") String id) throws CommentNotFoundException {
        Optional<Comment> comment = repository.findById(id);
        if(!comment.isPresent()){
            throw new CommentNotFoundException();
        }
        return  comment.get();
    }


    @Operation(
            summary="Retrieve a list of comments",
            description = "Get a list of comments",
            tags = {"comments", "get"})

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Comment.class), mediaType = "application/json")})
    })
    @GetMapping
    public List<Comment> findComments(
            @Parameter(description = "number of page to be retrieved") @RequestParam(required = false, defaultValue = "0") int page,
            @Parameter(description = "size of page to be retrieved") @RequestParam(required = false, defaultValue = "10") int size
    ) throws CommentNotFoundException, MinException {
        if(page<0 || size<0){
            throw new MinException();
        }
        Pageable paging = PageRequest.of(page, size);
        Page<Comment> comments = repository.findAll(paging);
        if(comments.isEmpty()){
            throw new CommentNotFoundException();
        }
        return comments.getContent();
    }
}
