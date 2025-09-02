package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Pedido/Carrito del usuario.
 */
public class Order {

    private final User user;
    private final List<CartItem> items = new ArrayList<>();

    public Order(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user null");
        }
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    /**
     * Agrega o acumula cantidad de un producto.
     * @param product
     * @param qty
     */
    public void addProduct(Product product, int qty) {
        if (qty <= 0) {
            throw new IllegalArgumentException("qty > 0");
        }
        for (CartItem it : items) {
            if (it.getProduct().equals(product)) {
                it.increment(qty);
                return;
            }
        }
        items.add(new CartItem(product, qty));
    }

   /** Elimina el producto por ID (ignora mayúsculas/minúsculas).
    * @param productId
    * @return  */
   public boolean removeProductById(String productId) {
        if (productId == null || productId.isBlank()) return false;
        final String id = productId.trim();
        return items.removeIf(ci -> ci.getProduct().getId().equalsIgnoreCase(id));
    }

    /**
     * Calcula total con descuentos activos (via DiscountManager + Decorator).
     * @return 
     */
    public BigDecimal totalWithDiscounts() {
        DiscountManager dm = DiscountManager.getInstance();
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem it : items) {
            BigDecimal unit = dm.applyDiscounts(it.getProduct());
            total = total.add(unit.multiply(BigDecimal.valueOf(it.getQuantity())));
        }
        return total;
    }
 
}
