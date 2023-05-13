package com.songsong.ImageToText.Controller;

import com.songsong.ImageToText.Model.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class MassageController {
    MessageService messageService;

    @Autowired
    public MassageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/massage")
    public ResponseEntity<String> fileIo( @RequestParam("img") List<MultipartFile> img){

        String res =messageService.imgToText(img);



        return new ResponseEntity<String>(res, HttpStatus.OK);
    }
}
