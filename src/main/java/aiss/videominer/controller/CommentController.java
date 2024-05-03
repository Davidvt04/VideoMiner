package aiss.videominer.controller;

import aiss.videominer.exception.ChannelNotFoundException;
import aiss.videominer.exception.CommentNotFoundException;
import aiss.videominer.model.Channel;
import aiss.videominer.model.Comment;
import aiss.videominer.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videominer/comments")
public class CommentController {

    @Autowired
    CommentRepository repository;

    @GetMapping("/{id}")
    public Comment findComment(@PathVariable(value = "id") String id) throws CommentNotFoundException {
        Optional<Comment> comment = repository.findById(id);
        if(!comment.isPresent()){
            throw new CommentNotFoundException();
        }
        return  comment.get();
    }
    @GetMapping
    public List<Comment> findComments(
            @RequestParam(defaultValue = "10") int size
    ) throws CommentNotFoundException {
        Pageable paging = PageRequest.ofSize(size);
        Page<Comment> comments = repository.findAll(paging);
        if(comments.isEmpty()){
            throw new CommentNotFoundException();
        }
        return comments.getContent();
    }
}
