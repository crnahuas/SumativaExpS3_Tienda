package model;

import discount.CatDiscount;
import discount.Component;
import discount.PctDiscount;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * Singleton que centraliza la aplicación de descuentos. Construye el pipeline
 * Decorator según la configuración activa.
 */
public class DiscountManager {

    private static volatile DiscountManager instance; // DCL thread-safe

    private DiscountManager() {
    }

    /**
     * Obtiene la instancia única (lazy, thread-safe).
     * @return 
     */
    public static DiscountManager getInstance() {
        if (instance == null) {
            synchronized (DiscountManager.class) {
                if (instance == null) {
                    instance = new DiscountManager();
                }
            }
        }
        return instance;
    }

// Configuración activa (opcional):
    private Optional<BigDecimal> globalPercentage = Optional.empty();
    private Optional<String> categoryForDiscount = Optional.empty();
    private Optional<BigDecimal> categoryPercentage = Optional.empty();

    /**
     * Define porcentaje global (0..1) o limpia si null.
     * @param pctOrNull
     */
    public void setGlobalPercentage(BigDecimal pctOrNull) {
        this.globalPercentage = (pctOrNull == null) ? Optional.empty() : Optional.of(pctOrNull);
    }

    /**
     * Define descuento por categoría o limpia si nulls.
     * @param categoryOrNull
     * @param pctOrNull
     */
    public void setCategoryDiscount(String categoryOrNull, BigDecimal pctOrNull) {
        if (categoryOrNull == null || pctOrNull == null) {
            this.categoryForDiscount = Optional.empty();
            this.categoryPercentage = Optional.empty();
        } else {
            this.categoryForDiscount = Optional.of(categoryOrNull);
            this.categoryPercentage = Optional.of(pctOrNull);
        }
    }

    /**
     * Aplica descuentos activos a un producto construyendo el pipeline.
     * @param product
     * @return 
     */
    public BigDecimal applyDiscounts(Product product) {
        Component pipeline = new BasePrice();
        if (globalPercentage.isPresent()) {
            pipeline = new PctDiscount(pipeline, globalPercentage.get());
        }
        if (categoryForDiscount.isPresent() && categoryPercentage.isPresent()) {
            pipeline = new CatDiscount(pipeline, categoryForDiscount.get(), categoryPercentage.get());
        }
        return pipeline.getFinalPrice(product);
    }

    /**
     * Resumen legible del estado de descuentos.
     * @return 
     */
    public String summary() {
        StringBuilder sb = new StringBuilder("Descuentos activos: ");
        if (globalPercentage.isPresent()) {
            sb.append(String.format("Global %.0f%%. ", globalPercentage.get().multiply(BigDecimal.valueOf(100))));
        }
        if (categoryForDiscount.isPresent() && categoryPercentage.isPresent()) {
            sb.append(String.format("Categoría '%s' %.0f%%.", categoryForDiscount.get(), categoryPercentage.get().multiply(BigDecimal.valueOf(100))));
        }
        String base = sb.toString();
        return base.equals("Descuentos activos: ") ? "Descuentos activos: ninguno." : base;
    }
}
