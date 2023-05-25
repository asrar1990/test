package com.bn.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sbose on 16/5/23.
 */
@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
@Slf4j
public class AccountController {
    
    @GetMapping
    public ResponseEntity<String> index() {
        return ResponseEntity.ok().body("Welcome to Account Service!");
    }

    @GetMapping("profile")
    public ResponseEntity<String> accountByEmail(@RequestParam(name = "email") String email) {
        log.info("Requested email to check, {}", email);
        return ResponseEntity.ok().body("");
    }
}