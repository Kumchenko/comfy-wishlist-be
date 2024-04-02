package com.example.comfywishlist.controller;


import com.example.comfywishlist.entity.WishParseResult;
import com.example.comfywishlist.service.WishItemParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/parse")
public class ParseRestController {

    @Autowired
    private WishItemParser wishItemParser;
    private final Logger logger = LoggerFactory.getLogger(ParseRestController.class);

    @GetMapping
    public ResponseEntity<?> parseWishItem(@RequestParam("url") String productUrl) {
        try {
            WishParseResult result = wishItemParser.parseByUrl(productUrl);
            logger.info("Parsed price {} from url {}", result.getPrice(), productUrl);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> parseWishItemByHtml(@RequestBody(required = false) String htmlContent) {
        try {
            if (htmlContent == null) {
                throw new ParseException("No HTML content provided", 1);
            }

            WishParseResult result = wishItemParser.parseByHtml(htmlContent);
            logger.info("Parsed price {} from html", result.getPrice());

            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
