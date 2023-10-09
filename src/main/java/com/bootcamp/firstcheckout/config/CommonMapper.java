package com.bootcamp.firstcheckout.config;

import com.bootcamp.firstcheckout.controllers.requests.AddAnItemToCartRequest;
import com.bootcamp.firstcheckout.controllers.requests.AddVasItemToAnItemInCartRequest;
import com.bootcamp.firstcheckout.domains.dtos.*;
import com.bootcamp.firstcheckout.domains.models.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CommonMapper {
    CommonMapper INSTANCE = Mappers.getMapper( CommonMapper.class );

    @Mapping(source = "category", target = "categoryDTO")
    @Mapping(source = "seller", target = "sellerDTO")
    @Mapping(source = "promotion", target = "promotionDTO")
    ItemDTO itemToItemDto(Item item);
    CategoryDTO categoryToCategoryDto(Category category);
    SellerDTO sellerToSellerDto(Seller seller);
    PromotionDTO promotionToPromotionDto(Promotion promotion);
    VasItemDTO vasItemToVasItemDto(VasItem vasItem);
    List<VasItemDTO> vasItemsToVasItemDTOs(List<VasItem> vasItems);
    CartItemDTO cartItemToCartItemDTO(CartItem cartItem);
    List<CartItemDTO> cartItemsToCartItemDTOs(List<CartItem> cartItems);
    @Mapping(source = "itemId", target = "id")
    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "sellerId", target = "seller.id")
    Item addAnItemToCartRequestToItem(AddAnItemToCartRequest addAnItemToCartRequest);
    @Mapping(source = "vasItemId", target = "id")
    @Mapping(source = "categoryId", target = "category.id")
    VasItem addVasItemToAnItemInCartRequestToVasItem(AddVasItemToAnItemInCartRequest addVasItemToAnItemInCartRequest);
    CartDTO cartToCartDto(Cart cart);

}
