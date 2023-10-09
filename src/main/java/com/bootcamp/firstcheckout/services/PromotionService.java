package com.bootcamp.firstcheckout.services;

import com.bootcamp.firstcheckout.domains.enums.PromotionType;
import com.bootcamp.firstcheckout.domains.models.Cart;
import com.bootcamp.firstcheckout.domains.models.CartItem;
import com.bootcamp.firstcheckout.domains.models.Promotion;
import com.bootcamp.firstcheckout.daos.PromotionRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class PromotionService {
    private final PromotionRepository repository;

    public PromotionService(PromotionRepository repository) {
        this.repository = repository;
    }

    public Promotion calculateSameSellerPromotionDiscount(Cart cart) {
        List<CartItem> cartItems = cart.getCartItems();
        if (!CollectionUtils.isEmpty(cartItems) && cartItems.size() > Constants.ONE) {
            Optional<Promotion> sameSellerPromotionOptional = getPromotionById(PromotionType.SAME_SELLER_PROMOTION.getId());
            if(sameSellerPromotionOptional.isPresent()) {
                Promotion sameSellerPromotion = sameSellerPromotionOptional.get();
                calculateAndSetTotalDiscountForSameSellerPromotion(cart, sameSellerPromotion);
                if (!Objects.equals(Constants.ZERO, sameSellerPromotion.getCalculatedAmount())) {
                    log.info("Same seller promotion applied for cart {}", cart.getId());
                    return sameSellerPromotion;
                }
            }
        }
        log.info("Same seller promotion can not applied for cart {}", cart.getId());
        return null;
    }

    private static void calculateAndSetTotalDiscountForSameSellerPromotion(Cart cart, Promotion sameSellerPromotion) {
        int sellerId = cart.getCartItems().get(Constants.FIRST_ITEM_INDEX_IN_CART).getItem().getId();
        double totalDiscount = Constants.ZERO;
        for (CartItem cartItem : cart.getCartItems()) {
            if (cartItem.getItem().getSeller().getId() != sellerId) {
                log.info("Same seller promotion not applied for cart {}", cart.getId());
                break;
            }
            totalDiscount = totalDiscount + (cartItem.getItem().getPrice() * cartItem.getItem().getQuantity() * sameSellerPromotion.getAmount());
        }
        sameSellerPromotion.setCalculatedAmount(totalDiscount);
    }

    private Optional<Promotion> getPromotionById(Integer id) {
        return repository.findById(id);
    }

    public Promotion calculateCategoryPromotion(Cart cart) {
        List<CartItem> cartItems = cart.getCartItems();
        if (cartItems != null && !cartItems.isEmpty()) {
            Optional<Promotion> categoryPromotionOptional = getPromotionById(PromotionType.CATEGORY_PROMOTION.getId());
            if (categoryPromotionOptional.isPresent()) {
                Promotion categoryPromotion = categoryPromotionOptional.get();
                calculateCategoryPromotionDiscount(cart, categoryPromotion);
                if (categoryPromotion.getCalculatedAmount() != null && !Objects.equals(Constants.ZERO, categoryPromotion.getCalculatedAmount())) {
                    cart.setTotalPrice(cart.getTotalPrice() - categoryPromotion.getCalculatedAmount());
                    return categoryPromotion;
                }
            }
        }
        log.error("Category promotion can not applied for cart {}", cart.getId());
        return null;
    }

    public void calculateCategoryPromotionDiscount(Cart cart, Promotion categoryPromotion) {
        double totalDiscount = Constants.ZERO;
        if (cart.getCartItems() != null && !cart.getCartItems().isEmpty()) {
            for (CartItem cartItem : cart.getCartItems()) {
                if(cartItem.getItem() != null && cartItem.getItem().getCategory() != null) {
                    if (Objects.equals(cartItem.getItem().getCategory().getId(), Constants.CATEGORY_ID_WHICH_HAS_A_CATEGORY_PROMOTION)) {
                        totalDiscount = totalDiscount + (cartItem.getItem().getPrice() * cartItem.getItem().getQuantity() * categoryPromotion.getAmount());
                    }
                }
            }
            if(totalDiscount > Constants.ZERO) {
                log.info("Category promotion applied for cart {}", cart.getId());
                categoryPromotion.setCalculatedAmount(totalDiscount);
            }
        }
    }

    public Promotion calculateTotalPricePromotion(Cart cart) {
        if(cart.getCartItems() != null && !cart.getCartItems().isEmpty()) {
            double totalPrice = cart.getTotalPrice();
            return calculateTotalPricePromotionDiscount(cart, totalPrice);
        }
        log.info("Total price promotion can not applied for cart {}", cart.getId());
        return null;
    }

    private Promotion calculateTotalPricePromotionDiscount(Cart cart, double totalPrice) {
        Optional<Promotion> promotionOptional = getPromotionById(PromotionType.TOTAL_PRICE_PROMOTION.getId());
        if(promotionOptional.isPresent()) {
            Promotion promotion = promotionOptional.get();
            double totalDiscount;

            totalDiscount = setTotalPriceDiscount(totalPrice);
            log.info("Total price promotion applied for cart {}", cart.getId());
            promotion.setCalculatedAmount(totalDiscount);
            return promotion;
        }
        log.error("Total price promotion can not applied for cart {}", cart.getId());
        return null;
    }

    private static double setTotalPriceDiscount(double totalPrice) {
        double totalDiscount;
        if (totalPrice < Constants.TOTAL_PRICE_PROMOTION_5000) {
            totalDiscount = Constants.DISCOUNT_FOR_LESS_THAN_TOTAL_PRICE_PROMOTION_5000;
        } else if (totalPrice < Constants.TOTAL_PRICE_PROMOTION_10000) {
            totalDiscount = Constants.DISCOUNT_FOR_BETWEEN_TOTAL_PRICE_PROMOTION_5000_AND_10000;
        } else if (totalPrice < Constants.TOTAL_PRICE_PROMOTION_50000) {
            totalDiscount = Constants.DISCOUNT_FOR_BETWEEN_TOTAL_PRICE_PROMOTION_10000_AND_50000;
        } else {
            totalDiscount = Constants.MAX_DISCOUNT_TOTAL_PRICE_PROMOTION;
        }
        return totalDiscount;
    }

    public void applyBestPromotion(Cart cart) {
        List<Promotion> promotions = new ArrayList<>();
        promotions.add(calculateSameSellerPromotionDiscount(cart));
        promotions.add(calculateCategoryPromotion(cart));
        promotions.add(calculateTotalPricePromotion(cart));

        Promotion bestPromotion = getBestPromotion(promotions);

        if(bestPromotion.getCalculatedAmount() > Constants.ZERO) {
            cart.setPromotion(bestPromotion);
            double totalPrice = cart.getTotalPrice() - bestPromotion.getCalculatedAmount();
            if(totalPrice < Constants.ZERO) {
                totalPrice = Constants.ZERO;
            }
            cart.setTotalPrice(totalPrice);
            log.info("Best promotion which is {} applied for cart {}", cart.getPromotion().getTitle(), cart.getId());
        } else {
            log.error("No promotion applied for cart {}", cart.getId());
        }
    }

    private static Promotion getBestPromotion(List<Promotion> promotions) {
        final Promotion bestPromotion = new Promotion();
        bestPromotion.setAmount(Constants.ZERO);
        promotions.forEach(promotion -> {
            if (promotion != null) {
                if (promotion.getCalculatedAmount() > bestPromotion.getAmount()) {
                    bestPromotion.setId(promotion.getId());
                    bestPromotion.setTitle(promotion.getTitle());
                    bestPromotion.setType(promotion.getType());
                    bestPromotion.setIsPercent(promotion.getIsPercent());
                    bestPromotion.setAmount(promotion.getAmount());
                    bestPromotion.setCalculatedAmount(promotion.getCalculatedAmount());
                    bestPromotion.setStatus(promotion.getStatus());
                }
            }
        });
        return bestPromotion;
    }
}
