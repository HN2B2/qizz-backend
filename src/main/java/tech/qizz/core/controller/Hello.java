package tech.qizz.core.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin
@RestController
public class Hello {
    @GetMapping()
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("Hello World!!", HttpStatus.OK);
    }
}
