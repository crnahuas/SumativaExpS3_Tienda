package view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import model.CartItem;
import model.DiscountManager;
import model.Order;

/**
 * Vista del carrito/pedido (muestra totales con descuentos).
 */
public class CartView {

    public void render(Order order) {
        System.out.println("=== Carrito de " + order.getUser().getFullName() + " ===");
        if (order.getItems().isEmpty()) {
            System.out.println("(vacío)\n");
            return;
        }
        DiscountManager dm = DiscountManager.getInstance();
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem it : order.getItems()) {
            BigDecimal unit = dm.applyDiscounts(it.getProduct());
            BigDecimal sub = unit.multiply(BigDecimal.valueOf(it.getQuantity()))
                    .stripTrailingZeros();
            total = total.add(sub);
            System.out.printf("- %s x%d | Unit:$%s | Sub:$%s%n",
                    it.getProduct().getName(), it.getQuantity(), unit, sub);
        }
        System.out.println("----------------------------");
        System.out.println("Total con descuentos: $" + order.totalWithDiscounts());
        System.out.println();
    }
}
