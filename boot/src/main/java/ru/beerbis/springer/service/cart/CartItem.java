package ru.beerbis.springer.service.cart;

public class CartItem {
    private final Integer productId;
    private final Integer count;

    public CartItem(Integer productId, Integer count) {
        this.productId = productId;
        this.count = count;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getCount() {
        return count;
    }
}
