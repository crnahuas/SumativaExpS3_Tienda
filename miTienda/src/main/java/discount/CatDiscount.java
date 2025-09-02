package discount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import model.Product;

/**
 * Decorador concreto: descuento por categoría específica.
 */
public class CatDiscount extends Decorator {

    private final String targetCategory;
    private final BigDecimal percentage; // 0..1

    public CatDiscount(Component wrappee, String targetCategory, BigDecimal percentage) {
        super(wrappee);
        if (targetCategory == null || targetCategory.isBlank()) {
            throw new IllegalArgumentException("targetCategory requerido");
        }
        if (percentage == null || percentage.signum() < 0 || percentage.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("percentage debe estar entre 0 y 1");
        }
        this.targetCategory = targetCategory;
        this.percentage = percentage;
    }

    @Override
    public BigDecimal getFinalPrice(Product product) {
        BigDecimal base = super.getFinalPrice(product);
        if (product.getCategory().equalsIgnoreCase(targetCategory)) {
            BigDecimal factor = BigDecimal.ONE.subtract(percentage);
            return base.multiply(factor).setScale(2, RoundingMode.HALF_UP);
        }
        return base.setScale(2, RoundingMode.HALF_UP);
    }
}
