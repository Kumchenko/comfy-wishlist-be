package com.example.comfywishlist.service;

import com.example.comfywishlist.entity.WishItem;
import com.example.comfywishlist.repository.WishItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class WishItemService {

    @Autowired
    private WishItemRepository wishItemRepository;
    @Autowired
    private ExcelService excelService;
    private Logger logger = LoggerFactory.getLogger(WishItemService.class);

    public List<WishItem> getAllWishItems() {
        List<WishItem> wishItems = wishItemRepository.findAll();
        logger.info("Extracted {} wishItems from database", wishItems.size());
        return wishItems;
    }

    public boolean deleteWishItemById(long id) {
        wishItemRepository.deleteById(id);
        if (wishItemRepository.findById(id).isPresent()) {
            logger.warn("Cannot delete wishItem with (id = {})", id);
            throw new RuntimeException("Cannot delete wishItem (id =" + id + ")");
        }
        logger.info("Delete wishItem (id = {})", id);
        return true;
    }

    public WishItem save(WishItem newWishItem) {
        WishItem saved = wishItemRepository.save(newWishItem);
        logger.info("Saved new wishItem: {}", saved);
        return saved;
    }

    public byte[] getExcelFile() throws IOException {
        List<WishItem> wishItems = getAllWishItems();
        byte[] excel = excelService.createExcelFile(wishItems);
        logger.info("Created excel file with {} wishItems", wishItems.size());
        return excel;
    }
}
