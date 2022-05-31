package com.careerdevs.alphavantageapiv1.controllers;

import com.careerdevs.alphavantageapiv1.models.Overview;
import com.careerdevs.alphavantageapiv1.repositories.OverviewRepository;
import com.careerdevs.alphavantageapiv1.utils.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/overview")
public class OverviewController {

    @Autowired
    private Environment env;

    @Autowired
    private OverviewRepository overviewRepository;
    private final String BASE_URL = "https://www.alphavantage.co/query?function=OVERVIEW";



    @GetMapping("/{symbol}")
    public ResponseEntity<?> dynamicOverview(RestTemplate restTemplate, @PathVariable String symbol){
        try {

            String url = BASE_URL + "&symbol=" + symbol + "&apikey=" + env.getProperty("AV_API_KEY");

            Overview responseBody = restTemplate.getForObject(url, Overview.class);


            if (responseBody == null) {
                ApiError.throwErr(500, "Did not receive response from AV");


            } else if (responseBody.getSymbol() == null) {
                ApiError.throwErr(404, "Invalid stock symbol: " + symbol);

            }
            return ResponseEntity.ok(responseBody);

        }catch (HttpClientErrorException e){
            return ApiError.customApiError(e.getMessage(),e.getStatusCode().value());
        } catch (Exception e) {
            return ApiError.genericApiError(e);
        }
    }

    @PostMapping("/{symbol}")
    public ResponseEntity<?> uploadOverviewBySymbol (RestTemplate restTemplate, @PathVariable String symbol){
        try {

            String url = BASE_URL + "&symbol=" + symbol+ "&apikey=" + env.getProperty("AV_API_KEY");

            Overview responseBody = restTemplate.getForObject(url, Overview.class);




            Overview saveOverview = overviewRepository.save(responseBody);

            if(responseBody == null){
                ApiError.throwErr(500, "Did not receive response from AV");

            }else if (responseBody.getSymbol()== null){
                ApiError.throwErr(404, "Invalid stock symbol: "+symbol);

            }
            return ResponseEntity.ok(saveOverview);



        } catch (HttpClientErrorException e){
            return ApiError.customApiError(e.getMessage(),e.getStatusCode().value());
        }catch (DataIntegrityViolationException e){
            return ApiError.customApiError("Can not upload duplicate stock data", 400);

        }catch (Exception e) {
            return ApiError.genericApiError(e);
        }
    }

    // get all delete all -- from the sql database.


    @GetMapping("/all")
    public ResponseEntity<?> getall(){
        try{
            Iterable<Overview> allOverviewProfiles = overviewRepository.findAll();
            return ResponseEntity.ok(allOverviewProfiles);
//            return new ResponseEntity<>(allOverviewProfiles, HttpStatus.OK);
        }catch (HttpClientErrorException e){

            return ApiError.customApiError(e.getMessage(),e.getStatusCode().value());
        }catch (Exception e){
            return ApiError.genericApiError(e);
        }
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity<?> deleteAllFromDatabase(){
        try {
            long totalProfiles = overviewRepository.count();
            if(totalProfiles == 0) return ResponseEntity.ok("No Overviews to delete ");

            overviewRepository.deleteAll();
//            return new ResponseEntity<>( "total deleted:" +totalProfiles, HttpStatus.OK
//            );
            return ResponseEntity.ok("deleted Overviews:"+ totalProfiles);

//        }catch (HttpClientErrorException e){
//            return ApiErrorHandling.customApiError(e.getMessage(), e.getStatusCode());
        }catch (HttpClientErrorException e){
            return ApiError.customApiError(e.getMessage(),e.getStatusCode().value());
        }catch (Exception e){
            return ApiError.genericApiError(e);
        }
    }

    // GET ONE OVERVIEW BY ID FROM DATABASE
    @GetMapping("/getbyid/{Id}")
    private ResponseEntity<?> getById(@PathVariable("Id")String overViewId){
        try{
            if(ApiError.isStrNan(overViewId)){
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, overViewId+ ": is not a valid id");

            }
            long oId = Long.parseLong(overViewId);
            Optional<Overview> foundOverView = overviewRepository.findById(oId);
            if(foundOverView.isEmpty()){
                ApiError.throwErr(404, "Overview with id: "+ oId + " not found.");


            }
            return new ResponseEntity<>(foundOverView, HttpStatus.OK);
//        }catch (NumberFormatException e){
//            return ApiErrorHandling.customApiError("ID must be a number", 400 );
//
//
        }catch (HttpClientErrorException e){
            return ApiError.customApiError(e.getMessage(),e.getStatusCode().value());
        }catch (Exception e){
            return ApiError.genericApiError(e);
        }
    }

    @GetMapping("/symbol/{symbol}")
    private ResponseEntity<?> getOverviewBySymbol(@PathVariable String symbol){
        try{
//            if(ApiError.isStrNan(overViewId)){
//                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, overViewId+ ": is not a valid id");
//
//            }

            Overview foundOverView = overviewRepository.findBySymbol(symbol);
            if(foundOverView == null){
                ApiError.throwErr(404, "Overview with symbol: "+ symbol + " not found.");


            }
            return ResponseEntity.ok(foundOverView);

        }catch (HttpClientErrorException e){
            return ApiError.customApiError(e.getMessage(),e.getStatusCode().value());
        }catch (Exception e){
            return ApiError.genericApiError(e);
        }
    }

    // find by excahnge
    @GetMapping("/exchange/{exchange}")
    private ResponseEntity<?> getOverviewByExchange(@PathVariable String exchange){
        try{
            //take notes on 37:00
            List<Overview> foundOverView = overviewRepository.findByExchange(exchange);
            if(foundOverView.isEmpty()){
                ApiError.throwErr(404, exchange + " did not match any overview.");
            }
            return ResponseEntity.ok(foundOverView);
        }catch (HttpClientErrorException e){
            return ApiError.customApiError(e.getMessage(),e.getStatusCode().value());
        }catch (Exception e){
            return ApiError.genericApiError(e);
        }
    }

    // find by Country
    @GetMapping("/country/{country}")
    private ResponseEntity<?> getOverviewByCountry(@PathVariable String country) {
        try {
            List<Overview> foundOverview = overviewRepository.findByCountry(country);
            if(foundOverview.isEmpty()){
                ApiError.throwErr(404, country + "did not match any overview. ");
            }
            return ResponseEntity.ok(foundOverview);
        }catch (HttpClientErrorException e){
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());

        }catch (Exception e){
            return ApiError.genericApiError(e);
        }

    }
    // find by currency
    @GetMapping("/currency/{currency}")
    private ResponseEntity<?> getOverviewByCurrecy(@PathVariable String currency){
        try {
            List<Overview> foundOverview = overviewRepository.findByCurrency(currency);

            if(foundOverview.isEmpty()){
                ApiError.throwErr(404, currency+ "did not match any overview.");

            }
            return ResponseEntity.ok(foundOverview);
        }catch (HttpClientErrorException e){
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
        }catch (Exception e){
            return ApiError.genericApiError(e);
        }
    }

    //Sector
    @GetMapping("/sector/{sector}")
    private ResponseEntity<?> getOverviewBySector(@PathVariable String sector){
        try {
            List<Overview> foundOverview = overviewRepository.findBySector(sector);
            if(foundOverview.isEmpty()){
                 ApiError.throwErr(404, sector + "did not match any overview");


            }
            return ResponseEntity.ok(foundOverview);
        }catch (HttpClientErrorException e){
            return ApiError.customApiError(e.getMessage(),e.getStatusCode().value());
        }catch (Exception e){
            return ApiError.genericApiError(e);
        }
    }

    //@JsonProperty("AssetType")
    //    @Column(name= "asset_type", nullable = false)
    //    private String assetType;
    @GetMapping("/assetType/{assetType}")
    private ResponseEntity<?> getOverviewByAsset (@PathVariable("assetType") String assetType){
        try {
            List<Overview> findByOverview = overviewRepository.findByAssetType(assetType);
            if(findByOverview.isEmpty()){
                ApiError.throwErr(404, assetType + " did not match any overview");

            }
            return ResponseEntity.ok(findByOverview);
        }catch (HttpClientErrorException e){
            return ApiError.customApiError(e.getMessage(),e.getStatusCode().value());
        }catch (Exception e){
            return ApiError.genericApiError(e);
        }

    }


    //try deleting something from database by ID

    @DeleteMapping("/id/{Id}")
    public ResponseEntity<?> deleteOverviewById (@PathVariable("Id") String overViewId){
        try{
            if (ApiError.isStrNan(overViewId)){
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, overViewId + ": is not Valid");

            }
            long oId = Long.parseLong(overViewId);
            Optional<Overview> deleteOverview = overviewRepository.findById(oId);

            if (deleteOverview.isEmpty()){
                ApiError.throwErr(400,"Overview with id" + overViewId + " did not match any overview");


            }
            overviewRepository.deleteById(oId);
            return ResponseEntity.ok(deleteOverview);

        }catch (HttpClientErrorException e){
            return ApiError.customApiError(e.getMessage(),e.getStatusCode().value());
        }catch (Exception e){
            return ApiError.genericApiError(e);
        }
    }

    //delete by exchange
    @DeleteMapping("/exchange/{exchange}")
    public ResponseEntity<?> deleteByExchange (@PathVariable String exchange){
        try{
//            if (ApiError.isStrNan(exchange)){
//                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, exchange + ": is not Valid");
//
//            }
            List<Overview> deleteOverview = overviewRepository.findByExchange(exchange);

            if (deleteOverview.isEmpty()){
                ApiError.throwErr(400,"Overview with id" + exchange + " did not match any overview");


            }
            overviewRepository.deleteByExchange(exchange);
            return ResponseEntity.ok(deleteOverview);

        }catch (HttpClientErrorException e){
            return ApiError.customApiError(e.getMessage(),e.getStatusCode().value());
        }catch (Exception e){
            return ApiError.genericApiError(e);
        }
    }

}
