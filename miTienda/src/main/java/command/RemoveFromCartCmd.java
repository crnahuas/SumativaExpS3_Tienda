package command;

import controller.CartCtrl;

/**
 * Command: eliminar un producto del carrito por ID.
 */
public class RemoveFromCartCmd implements Command {

    private final CartCtrl cartCtrl;
    private final String productId;

    public RemoveFromCartCmd(CartCtrl cartCtrl, String productId) {
        this.cartCtrl = cartCtrl;
        this.productId = productId;
    }

    @Override
    public void Ejecutar() {
        cartCtrl.removeProductById(productId);
    }
}
