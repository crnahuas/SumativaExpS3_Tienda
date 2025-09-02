package model;

import java.util.Objects;

/**
 * Item del carrito: producto + cantidad.
 */
public class CartItem {

    private final Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("product null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity > 0");
        }
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increment(int delta) {
        if (delta <= 0) {
            throw new IllegalArgumentException("delta > 0");
        }
        quantity += delta;
    }

    public void decrement(int delta) {
        if (delta <= 0) {
            throw new IllegalArgumentException("delta > 0");
        }
        if (quantity - delta <= 0) {
            throw new IllegalArgumentException("No puede quedar en 0 o negativo");
        }
        quantity -= delta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CartItem)) {
            return false;
        }
        return product.equals(((CartItem) o).product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product);
    }
}
