package com.bootcamp.firstcheckout.services;

import com.bootcamp.firstcheckout.config.CommonMapper;
import com.bootcamp.firstcheckout.controllers.requests.AddAnItemToCartRequest;
import com.bootcamp.firstcheckout.controllers.requests.AddVasItemToAnItemInCartRequest;
import com.bootcamp.firstcheckout.domains.dtos.CartDTO;
import com.bootcamp.firstcheckout.domains.enums.ItemType;
import com.bootcamp.firstcheckout.domains.enums.Status;
import com.bootcamp.firstcheckout.domains.models.*;
import com.bootcamp.firstcheckout.daos.CartRepository;
import com.bootcamp.firstcheckout.exceptions.custom.CartValidationException;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static com.bootcamp.firstcheckout.exceptions.constants.CartValidationErrorConstants.*;

@Slf4j
@Service
public class CartService {
    @Getter
    private Cart cart;
    private final CartRepository repository;
    private final PromotionService promotionService;
    private final CommonMapper mapper = CommonMapper.INSTANCE;

    public CartService(CartRepository repository, PromotionService promotionService) {
        this.repository = repository;
        this.promotionService = promotionService;
    }

    @PostConstruct
    public void init() {
        cart = getDefaultCart();
    }

    private Cart getDefaultCart() {
        Optional<Cart> cartOptional = repository.findById(Constants.DEFAULT_CART_ID);
        if(cartOptional.isPresent()) {
            log.info("Cart {} found", cartOptional.get().getId());
            return cartOptional.get();
        }
        return createNewCartAndSave();
    }

    private Cart createNewCartAndSave() {
        Cart cart = new Cart();
        cart.setId(Constants.DEFAULT_CART_ID);
        cart.setStatus(Status.ACTIVE);
        cart.setTotalPrice(Constants.ZERO);
        repository.save(cart);
        log.info("Cart {} created", cart.getId());
        return cart;
    }

    public boolean addAnItemToCart(Item item) {
        CartItem cartItem = new CartItem();
        cartItem.setItem(item);
        getCart().getCartItems().add(cartItem);
        promotionService.applyBestPromotion(getCart());
        repository.save(getCart());
        log.info("Item {} added to cart {}", item.getId(), getCart().getId());
        return true;
    }

    public void validateAddAnItemToCartRequest(AddAnItemToCartRequest request) {
        checkItemQuantityByItemIdInCart(request);
        checkCartItemSize();
        checkDigitalItemSizeInCart();
        checkItemTypeInCart(ItemType.getItemType(request.getCategoryId()));
        checkTotalItemAndVasItemSize();
        checkCartTotalPrice();
    }

    public boolean addVasItemToAnItemInCart(VasItem vasItem, Integer itemId) {
        getCart().getCartItems().stream()
                .filter(obj -> Objects.equals(obj.getItem().getId(), itemId))
                .forEach(obj -> obj.getVasItems().add(vasItem));
        log.info("VasItem {} added to item {}", vasItem.getId(), itemId);
        promotionService.applyBestPromotion(getCart());
        repository.save(getCart());
        return true;
    }

    public void validateAddVasItemToAnItemInCart(AddVasItemToAnItemInCartRequest request) {
        checkVasItemPrice(request);
        checkTotalItemAndVasItemSize();
        checkCartTotalPrice();
        checkVasItemSize();
        checkItemIdBeforeAddVasItemCart(request.getItemId());
        checkVasItemCategoryIdAndSellerId(request);
    }

    public boolean removeAnItemFromCart(Integer itemId) {
        boolean removed = getCart()
                .getCartItems()
                .removeIf(cartItem -> Objects.equals(cartItem.getItem().getId(), itemId));
        if(removed && !getCart().getCartItems().isEmpty()) {
            promotionService.applyBestPromotion(getCart());
            repository.save(getCart());
            log.info("Item {} removed from cart", itemId);
            return true;
        }
        log.error("Item {} can not removed from cart", itemId);
        return removed;
    }

    public boolean resetCart() {
        getCart().getCartItems().clear();
        getCart().setPromotion(null);
        getCart().setTotalPrice(Constants.ZERO);
        repository.save(getCart());
        log.info("Cart {} reset", getCart().getId());
        return true;
    }

    public CartDTO getCartDTO() {
        return mapper.cartToCartDto(cart);
    }

    public void checkCartItemSize() {
        if(!(getCart().getCartItems().size() <= Constants.MAX_UNIQUE_ITEM_VALUE_FOR_A_CART)) {
            log.error("Cart {} has {} items", getCart().getId(), getCart().getCartItems().size());
            throw new CartValidationException(CART_MAX_UNIQUE_ITEM_SIZE_ERROR_CODE, CART_MAX_UNIQUE_ITEM_SIZE_ERROR_MESSAGE);
        }
    }

    public void checkTotalItemAndVasItemSize() {
        int itemCount = getCart().getCartItems().size();
        int vasItemCount = getCart().getCartItems().stream().mapToInt(item -> item.getVasItems().size()).sum();
        if(!(itemCount + vasItemCount <= Constants.MAX_ITEM_VALUE_FOR_A_CART)) {
            log.error("Cart {} has {} items and {} vas items. Total item count is {}", getCart().getId(), itemCount, vasItemCount, itemCount + vasItemCount);
            throw new CartValidationException(CART_MAX_ITEM_SIZE_ERROR_CODE, CART_MAX_ITEM_SIZE_ERROR_MESSAGE);
        }
    }

    public void checkItemQuantityByItemIdInCart(AddAnItemToCartRequest request) {
        Optional<CartItem> cartItemOptional = getCart().getCartItems().stream().filter(cartItem -> cartItem.getItem().getId() == request.getItemId()).findFirst();
        if(cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            int totalQuantity = cartItem.getItem().getQuantity() + request.getQuantity();
            if(!(totalQuantity <= Constants.MAX_ITEM_QUANTITY_VALUE_FOR_AN_ITEM)) {
                log.error("Item {} has {} quantity", cartItem.getItem().getId(), cartItem.getItem().getQuantity());
                throw new CartValidationException(MAX_QUANTITY_FOR_AN_ITEM_ERROR_CODE, MAX_QUANTITY_FOR_AN_ITEM_ERROR_MESSAGE);
            }
        }
    }

    public void checkCartTotalPrice() {
        if(!(getCart().getTotalPrice() <= Constants.MAX_TOTAL_PRICE_FOR_A_CART)) {
            log.error("Cart {} has {} total price", getCart().getId(), getCart().getTotalPrice());
            throw new CartValidationException(CART_HIGH_TOTAL_PRICE_ERROR_CODE, CART_HIGH_TOTAL_PRICE_ERROR_MESSAGE);
        }
    }

    public void checkItemTypeInCart(ItemType itemType) {
        if(!getCart().getCartItems().stream().allMatch(cartItem -> cartItem.getItem().getCategory().getId() == itemType.getCategoryId())) {
            log.error("Cart {} can not have different item types", getCart().getId());
            throw new CartValidationException(CART_DIFFERENT_ITEM_TYPES_ERROR_CODE, CART_DIFFERENT_ITEM_TYPES_ERROR_MESSAGE);
        }
    }

    public void checkVasItemSize() {
        boolean result = true;
        Integer itemId = null;
        for(CartItem cartItem : getCart().getCartItems()) {
            if (cartItem.getVasItems().size() >= Constants.MAX_VAS_ITEM_VALUE_FOR_A_DEFAULT_ITEM) {
                result = false;
                itemId = cartItem.getItem().getId();
                break;
            }
        }
        if (!result && itemId != null) {
            log.error("Cart {} has too many vas items for Item {}", getCart().getId(), itemId);
            throw new CartValidationException(CART_MAX_VAS_ITEM_SIZE_FOR_AN_ITEM_ERROR_CODE, CART_MAX_VAS_ITEM_SIZE_FOR_AN_ITEM_ERROR_MESSAGE);
        }
    }

    public void checkDigitalItemSizeInCart() {
        if(!(getCart().getCartItems().stream().filter(cartItem -> cartItem.getItem().getType().getCategoryId() == ItemType.DIGITAL_ITEM.getCategoryId()).count() <= Constants.MAX_DIGITAL_ITEM_VALUE_IN_A_CART)) {
            log.error("Cart {} has too many digital items", getCart().getId());
            throw new CartValidationException(CART_MAX_DIGITAL_ITEM_SIZE_ERROR_CODE, CART_MAX_DIGITAL_ITEM_SIZE_ERROR_MESSAGE);
        }
    }


    public void checkItemIdBeforeAddVasItemCart(Integer itemId) { //addVasItemToItemInCart
        if(!Constants.ITEM_IDS_WHICH_CAN_HAVE_VAS_ITEM.contains(itemId)) {
            log.error("Item {} can not have vas item", itemId);
            throw new CartValidationException(ITEM_IS_NOT_SUITABLE_TO_ADD_VAS_ITEM_ERROR_CODE, ITEM_IS_NOT_SUITABLE_TO_ADD_VAS_ITEM_ERROR_MESSAGE);
        }
    }

    public void checkVasItemCategoryIdAndSellerId(AddVasItemToAnItemInCartRequest request) { //addVasItemToItemInCart
        if(Constants.VAS_ITEM_CATEGORY_ID != request.getCategoryId() || Constants.VAS_ITEM_SELLER_ID != request.getSellerId()) {
            log.error("Item {} can not have vas item", request.getItemId());
            throw new CartValidationException(ITEM_IS_NOT_SUITABLE_TO_ADD_VAS_ITEM_ERROR_CODE, ITEM_IS_NOT_SUITABLE_TO_ADD_VAS_ITEM_ERROR_MESSAGE);
        }
    }

    public void checkVasItemPrice(AddVasItemToAnItemInCartRequest request) { //addVasItemToItemInCart
        getCart().getCartItems().forEach(cartItem -> {
            if(Objects.equals(cartItem.getItem().getId(), request.getItemId())) {
                if(request.getPrice() > cartItem.getItem().getPrice()) {
                    log.error("Item {} can not have vas item {} because of its price", request.getItemId(), request.getVasItemId());
                    throw new CartValidationException(ITEM_IS_NOT_SUITABLE_TO_ADD_VAS_ITEM_BECAUSE_OF_VAS_ITEMS_PRICE_ERROR_CODE, ITEM_IS_NOT_SUITABLE_TO_ADD_VAS_ITEM_BECAUSE_OF_VAS_ITEMS_PRICE_ERROR_MESSAGE);
                }
            }
        });
    }
}
