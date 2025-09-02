package command;

import controller.CartCtrl;
import model.Product;

/**
 * Command: agregar un producto al carrito.
 */
public class AddToCartCmd implements Command {

    private final CartCtrl cartCtrl;
    private final Product product;
    private final int quantity;

    public AddToCartCmd(CartCtrl cartCtrl, Product product, int quantity) {
        this.cartCtrl = cartCtrl;
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public void Ejecutar() {
        cartCtrl.addProduct(product, quantity);
    }
}
