//package com.example.account.remote;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestHeader;
//
//@FeignClient(name = "auth-service", url = "http://localhost:8082")
//public interface AuthClient {
//
//    @PostMapping("/auth/validate")
//    ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token);
//}