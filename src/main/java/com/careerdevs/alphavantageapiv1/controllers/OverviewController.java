package com.careerdevs.alphavantageapiv1.controllers;

import com.careerdevs.alphavantageapiv1.models.Overview;
import com.careerdevs.alphavantageapiv1.repositories.OverviewRepository;
import com.careerdevs.alphavantageapiv1.utils.ApiErrorHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/overview")
public class OverviewController {

    @Autowired
    private Environment env;

    @Autowired
    private OverviewRepository overviewRepository;
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


    // test upload to Database
    @PostMapping("/test")
    public ResponseEntity<?> testUploadOverview(RestTemplate restTemplate) {
        try {

            String url = BASE_URL + "&symbol=IBM&apikey=" + env.getProperty("AV_API_KEY");

            Overview responseBody = restTemplate.getForObject(url, Overview.class);

            if (responseBody == null) {
                return ApiErrorHandling.customApiError("Did not receive response from AV",
                        HttpStatus.INTERNAL_SERVER_ERROR);

            } else if (responseBody.getSymbol() == null) {
                return ApiErrorHandling.customApiError("No data retrieved from AV",
                        HttpStatus.NOT_FOUND);
            }

            Overview saveOverview = overviewRepository.save(responseBody);

            return ResponseEntity.ok(saveOverview);


        }catch (DataIntegrityViolationException e){
            return ApiErrorHandling.customApiError("Can not upload duplicate stock data", HttpStatus.BAD_REQUEST);

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

            }else if (responseBody.getSymbol()== null){
                return ApiErrorHandling.customApiError("Invalid stock symbol: "+symbol,
                        HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(responseBody);


        } catch (Exception e) {
            return ApiErrorHandling.genericApiError(e);
        }
    }

    @PostMapping("/{symbol}")
    public ResponseEntity<?> uploadOverviewBySymbol (RestTemplate restTemplate, @PathVariable String symbol){
        try {

            String url = BASE_URL + "&symbol=" + symbol+ "&apikey=" + env.getProperty("AV_API_KEY");

            Overview responseBody = restTemplate.getForObject(url, Overview.class);




            Overview saveOverview = overviewRepository.save(responseBody);

            if(responseBody == null){
                return ApiErrorHandling.customApiError("Did not receive response from AV",
                        HttpStatus.INTERNAL_SERVER_ERROR);

            }else if (responseBody.getSymbol()== null){
                return ApiErrorHandling.customApiError("Invalid stock symbol: "+symbol,
                        HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(saveOverview);



        } catch (DataIntegrityViolationException e){
            return ApiErrorHandling.customApiError("Can not upload duplicate stock data", HttpStatus.BAD_REQUEST);

        }catch (Exception e) {
            return ApiErrorHandling.genericApiError(e);
        }
    }

    // get all delete all -- from the sql database.


    @GetMapping("/all")
    public ResponseEntity<?> getall(){
        try{
            Iterable<Overview> allOverviewProfiles = overviewRepository.findAll();
            return new ResponseEntity<>(allOverviewProfiles, HttpStatus.OK);
        }catch (Exception e){
            return ApiErrorHandling.genericApiError(e);
        }
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity<?> deleteAllFromDatabase(){
        try {
            long totalProfiles = overviewRepository.count();
            overviewRepository.deleteAll();
            return new ResponseEntity<>( "total deleted:" +totalProfiles, HttpStatus.OK
            );

        }catch (HttpClientErrorException e){
            return ApiErrorHandling.customApiError(e.getMessage(), e.getStatusCode());
        }catch (Exception e){
            return ApiErrorHandling.genericApiError(e);
        }
    }
}
