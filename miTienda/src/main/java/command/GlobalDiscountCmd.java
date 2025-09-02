package command;

import controller.DiscountCtrl;
import java.math.BigDecimal;

/**
 * Command: aplicar/limpiar descuento GLOBAL.
 */
public class GlobalDiscountCmd implements Command {

    private final DiscountCtrl discountCtrl;
    private final BigDecimal percentageOrNull; // 0..1 (null = limpiar)

    public GlobalDiscountCmd(DiscountCtrl discountCtrl, BigDecimal percentageOrNull) {
        this.discountCtrl = discountCtrl;
        this.percentageOrNull = percentageOrNull;
    }

    @Override
    public void Ejecutar() {
        discountCtrl.setGlobalPercentage(percentageOrNull);
    }
}
