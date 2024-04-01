package com.example.comfywishlist.controller;

import com.example.comfywishlist.entity.WishItem;
import com.example.comfywishlist.service.WishItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListRestController {

    @Autowired
    private WishItemService wishItemService;
    private Logger logger = LoggerFactory.getLogger(WishListRestController.class);

    @GetMapping("/test")
    public String getTest() {
        return "test works";
    }

    @PostMapping
    public ResponseEntity<WishItem> saveWishItem(@RequestBody WishItem wishItem) {
        try {
            WishItem saved = wishItemService.save(wishItem);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            logger.warn("Problem to save new wishItem: {}", wishItem);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<WishItem>> getAllWishItems() {
        try {
            List<WishItem> all = wishItemService.getAllWishItems();
            return ResponseEntity.ok(all);
        } catch (Exception e) {
            logger.warn("Problem to get all wishItems");
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> getExcelFile() {
        try {
            byte[] createdFile = wishItemService.getExcelFile();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "wishlist.xlsx");
            return ResponseEntity.ok().headers(headers).body(createdFile);
        } catch (IOException e) {
            logger.warn("Problem to get excel");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteWishItemById(@PathVariable("id") long id) {
        try {
            boolean deleted = wishItemService.deleteWishItemById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.warn("Problem to delete wishItem (id = {})", id);
            return ResponseEntity.internalServerError().build();
        }
    }
}
