package com.careerdevs.alphavantageapiv1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // giving spring boot a head up this will be a controller.
//@RequestMapping("/")
public class RootController {

    @Autowired
    Environment env;

    @GetMapping("/")
    public ResponseEntity<?> rootRoute(){

        // return new ResponseEntity<>("root route", Httpstatus.ok);
        return ResponseEntity.ok("rootRoute");
    }

    @GetMapping("/apikey")
    public ResponseEntity<?> apiKey(){

        return ResponseEntity.ok(env.getProperty("AV_AVI_KEY"));

    }
}
