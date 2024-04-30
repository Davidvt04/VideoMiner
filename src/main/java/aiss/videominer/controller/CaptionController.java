package aiss.videominer.controller;


import aiss.videominer.exception.ChannelNotFoundException;
import aiss.videominer.model.Caption;
import aiss.videominer.model.Channel;
import aiss.videominer.repository.CaptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videominer/captions")
public class CaptionController {

    @Autowired
    CaptionRepository repository;

    @GetMapping("/{id}")
    public Caption findCaption(@PathVariable(value = "id") Long id) throws CaptionNotFoundException {
        Optional<Caption> caption = repository.findById(id);
        if(!caption.isPresent()){
            throw new   CaptionNotFoundException();
        }
        return caption.get();
    }

    @GetMapping
    public List<Caption> findCaptions() throws CaptionNotFoundException {
        List<Caption> captions = new ArrayList<Caption>(repository.findAll());
        if(captions.isEmpty()){
            throw new CaptionNotFoundException();
        }
        return captions;
    }

}
