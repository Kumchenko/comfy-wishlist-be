package com.example.comfywishlist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class WishParseResult {
    private double price;
    private String name;
}
