package com.bootcamp.firstcheckout.services;

import com.bootcamp.firstcheckout.config.CommonMapper;
import com.bootcamp.firstcheckout.controllers.requests.AddAnItemToCartRequest;
import com.bootcamp.firstcheckout.domains.models.Item;
import com.bootcamp.firstcheckout.daos.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ItemService {
    private final ItemRepository repository;
    private final CategoryService categoryService;
    private final SellerService sellerService;
    private final CommonMapper mapper = CommonMapper.INSTANCE;

    public ItemService(ItemRepository repository, CategoryService categoryService, SellerService sellerService) {
        this.repository = repository;
        this.categoryService = categoryService;
        this.sellerService = sellerService;
    }

    public Item initializeAnItemFromAddAnItemToCartRequest(AddAnItemToCartRequest request){
        Optional<Item> itemOptional = repository.findById(request.getItemId());
        if(itemOptional.isPresent()) {
            Item item = itemOptional.get();
            item.setCategory(categoryService.getCategoryOrSaveCategoryIfItIsNotExist(request.getCategoryId()));
            item.setSeller(sellerService.getSellerOrSaveSellerIfItIsNotExist(request.getSellerId()));
            item.setPrice(request.getPrice());
            item.setQuantity(request.getQuantity());
            log.info("Item {} found", request.getItemId());
            return itemOptional.get();
        }
        return createItem(request);
    }

    private Item createItem(AddAnItemToCartRequest request) {
        Item item = mapper.addAnItemToCartRequestToItem(request);
        item.setCategory(categoryService.getCategoryOrSaveCategoryIfItIsNotExist(request.getCategoryId()));
        item.setSeller(sellerService.getSellerOrSaveSellerIfItIsNotExist(request.getSellerId()));
        repository.save(item);
        log.info("Item {} created", item.getId());
        return item;
    }
}
