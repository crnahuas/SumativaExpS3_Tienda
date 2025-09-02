package model;

import discount.Component;
import java.math.BigDecimal;

/**
 * Componente concreto: precio sin descuento.
 */
public class BasePrice implements Component {

    @Override
    public BigDecimal getFinalPrice(Product product) {
        return product.getPrice();
    }
}
