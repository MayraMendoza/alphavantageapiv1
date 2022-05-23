package com.careerdevs.alphavantageapiv1.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiErrorHandling {
    public static ResponseEntity <?> genericApiError(Exception e){
        System.out.println(e.getMessage());
        System.out.println(e.getClass());
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
    public static ResponseEntity<?> customApiError(String message, HttpStatus status){
        return ResponseEntity.status(status).body(message);
    }

    // check if string is a number
    public static boolean isStrNan (String strNum){
        if(strNum == null){
            return true;
        }
        try{
            Integer.parseInt(strNum);
        }catch (NumberFormatException e){
            return true;
        }
        return false;
    }
}
