package discount;

import java.math.BigDecimal;
import model.Product;

/**
 * Interfaz requerida por la pauta: dada un Product, retorna su precio final.
 */
public interface Component {

    /**
     * Retorna el precio final del producto según la cadena de descuentos.
     * @param product
     * @return 
     */
    BigDecimal getFinalPrice(Product product);
}
