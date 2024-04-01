package com.example.comfywishlist.service;

import com.example.comfywishlist.entity.Price;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;

@Service
public class WishItemParser {

    private static final Logger logger = LoggerFactory.getLogger(WishItemParser.class);

    public Price parseWishItem(String url) throws IOException, ParseException {
        Document doc = Jsoup.connect(url).get();
        Element price = doc.selectFirst("div.price__current");

        if (price != null) {
            String cleanPrice = price.text().replaceAll("[^\\d.]", "");
            double convertedPrice = Double.parseDouble(cleanPrice);
            logger.info("Parsed price {} from url: {}", convertedPrice, url);
            return new Price(convertedPrice);
        } else {
            throw new ParseException("Can't get price from url: " + url, 1);
        }
    }
}
