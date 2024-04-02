package com.example.comfywishlist.service;

import com.example.comfywishlist.entity.WishParseResult;
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

    public WishParseResult parseByUrl(String url) throws IOException, ParseException {
        String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36 OPR/108.0.0.0";
        Document doc = Jsoup.connect(url).userAgent(userAgent).get();
        return parse(doc, url);
    }

    public WishParseResult parseByHtml(String html) throws IOException, ParseException {
        Document doc = Jsoup.parse(html);
        return parse(doc, "html");
    }

    private WishParseResult parse(Document doc, String source) throws ParseException {
        Element price = doc.selectFirst("div.price__current");
        Element name = doc.selectFirst("h1.gen-tab__name");

        if (price != null && name != null) {
            String cleanPrice = price.text().replaceAll("[^\\d.]", "");
            double convertedPrice = Double.parseDouble(cleanPrice);

            String cleanName = name.text().trim();

            logger.info("Parsed price {} from {}", convertedPrice, source);
            return new WishParseResult(convertedPrice, cleanName);
        } else {
            throw new ParseException("Can't get data from the provided " + source, 1);
        }
    }
}
