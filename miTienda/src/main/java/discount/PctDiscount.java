package discount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import model.Product;

/**
 * Decorador concreto: descuento global por porcentaje (ej. 10%).
 */
public class PctDiscount extends Decorator {

    private final BigDecimal percentage; // 0..1

    public PctDiscount(Component wrappee, BigDecimal percentage) {
        super(wrappee);
        if (percentage == null || percentage.signum() < 0 || percentage.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("percentage debe estar entre 0 y 1");
        }
        this.percentage = percentage;
    }

    @Override
    public BigDecimal getFinalPrice(Product product) {
        BigDecimal base = super.getFinalPrice(product);
        BigDecimal factor = BigDecimal.ONE.subtract(percentage);
        return base.multiply(factor).setScale(2, RoundingMode.HALF_UP);
    }
}
