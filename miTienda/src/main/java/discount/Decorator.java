package discount;

import java.math.BigDecimal;
import model.Product;

/**
 * Clase abstracta Decorator. Envuelve un
 * Component y delega su comportamiento.
 */
public abstract class Decorator implements Component {

    protected final Component wrappee;

    protected Decorator(Component wrappee) {
        this.wrappee = wrappee;
    }

    @Override
    public BigDecimal getFinalPrice(Product product) {
        return wrappee.getFinalPrice(product);
    }
}
