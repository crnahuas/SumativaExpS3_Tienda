package command;

import controller.DiscountCtrl;
import java.math.BigDecimal;

/**
 * Command: aplicar/limpiar descuento por CATEGORÍA.
 */
public class CategoryDiscountCmd implements Command {

    private final DiscountCtrl discountCtrl;
    private final String categoryOrNull; // null = limpiar
    private final BigDecimal percentageOrNull; // 0..1 (null = limpiar)

    public CategoryDiscountCmd(DiscountCtrl discountCtrl, String categoryOrNull, BigDecimal percentageOrNull) {
        this.discountCtrl = discountCtrl;
        this.categoryOrNull = categoryOrNull;
        this.percentageOrNull = percentageOrNull;
    }

    @Override
    public void Ejecutar() {
        discountCtrl.setCategoryDiscount(categoryOrNull, percentageOrNull);
    }
}
