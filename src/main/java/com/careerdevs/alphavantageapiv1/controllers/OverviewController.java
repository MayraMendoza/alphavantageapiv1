package com.careerdevs.alphavantageapiv1.controllers;

import com.careerdevs.alphavantageapiv1.models.Overview;
import com.careerdevs.alphavantageapiv1.utils.ApiErrorHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/overview")
public class OverviewController {

    @Autowired
    private Environment env;
    private final String BASE_URL = "https://www.alphavantage.co/query?function=OVERVIEW";

    // test route
    @GetMapping("/test")
    public ResponseEntity<?> testOverview(RestTemplate restTemplate) {
        try {

            String url = BASE_URL + "&symbol=IBM&apikey=" + env.getProperty("AV_API_KEY");

            Overview responseBody = restTemplate.getForObject(url, Overview.class);
            return ResponseEntity.ok(responseBody);

        } catch(IllegalArgumentException e){
            return ApiErrorHandling.customApiError(
                    "Error In testOverview: check URL used for AV Request", HttpStatus.INTERNAL_SERVER_ERROR);


        } catch (Exception e) {
            return ApiErrorHandling.genericApiError(e);
        }
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<?> dynamicOverview(RestTemplate restTemplate, @PathVariable String symbol){
        try {

            String url = BASE_URL + "&symbol=" + symbol+ "&apikey=" + env.getProperty("AV_API_KEY");

            Overview responseBody = restTemplate.getForObject(url, Overview.class);


            if(responseBody == null){
                return ApiErrorHandling.customApiError("Did not receive response from AV",
                        HttpStatus.INTERNAL_SERVER_ERROR);

            }else if (responseBody.equals("{}")){
                return ApiErrorHandling.customApiError("Invalid stock symbol: "+symbol,
                        HttpStatus.BAD_REQUEST);
            }
            return ResponseEntity.ok(responseBody);


        } catch (Exception e) {
            return ApiErrorHandling.genericApiError(e);
        }
    }
}
