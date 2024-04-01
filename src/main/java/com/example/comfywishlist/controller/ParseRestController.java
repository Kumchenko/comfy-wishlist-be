package com.example.comfywishlist.controller;


import com.example.comfywishlist.entity.Price;
import com.example.comfywishlist.service.WishItemParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/parse")
public class ParseRestController {

    @Autowired
    private WishItemParser wishItemParser;
    private Logger logger = LoggerFactory.getLogger(ParseRestController.class);

    @PostMapping
    public ResponseEntity<?> parseWishItem(@RequestParam("url") String productUrl) {
        try {
            Price price = wishItemParser.parseWishItem(productUrl);
            logger.info("Parsed price {} from url {}", price.getPrice(), productUrl);
            return ResponseEntity.ok(price);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
