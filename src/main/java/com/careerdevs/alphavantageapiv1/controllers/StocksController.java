package com.careerdevs.alphavantageapiv1.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/stocks")

public class StocksController {

    @Autowired
    Environment environment;


    @GetMapping("/")
    public ResponseEntity<?> rootRoute (){
        return  ResponseEntity.ok("RootRoute");

    }


    @GetMapping("/overview")
    public Object getStock(@RequestParam("symbol") String symbol,
                                      RestTemplate restTemplate){
        String url = "https://www.alphavantage.co/query?function=OVERVIEW&";

//        environment.getProperty(AV_API_KEY);
        url += "symbol=" + symbol + "&apikey="+ "key";


        return restTemplate.getForObject(url,Object.class);
    }
}
