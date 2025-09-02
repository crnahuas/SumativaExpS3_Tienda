package controller;

import java.math.BigDecimal;
import model.DiscountManager;
import view.DiscountView;

/**
 * Controlador para configuración y visualización de descuentos.
 */
public class DiscountCtrl {

    private final DiscountManager dm = DiscountManager.getInstance();
    private final DiscountView view;

    public DiscountCtrl(DiscountView view) {
        this.view = view;
    }

    /**
     * Configura porcentaje global (0..1) o null para limpiar.
     * @param pctOrNull
     */
    public void setGlobalPercentage(BigDecimal pctOrNull) {
        dm.setGlobalPercentage(pctOrNull);
    }

    /**
     * Configura descuento por categoría o limpia si nulls.
     * @param categoryOrNull
     * @param pctOrNull
     */
    public void setCategoryDiscount(String categoryOrNull, BigDecimal pctOrNull) {
        dm.setCategoryDiscount(categoryOrNull, pctOrNull);
    }

    /**
     * Muestra resumen de descuentos activos.
     */
    public void showActiveDiscounts() {
        view.render(dm.summary());
    }
}
