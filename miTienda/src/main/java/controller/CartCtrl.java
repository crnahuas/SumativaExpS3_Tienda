package controller;

import model.Order;
import model.Product;
import view.CartView;

/**
 * Controlador del carrito/pedido.
 */
public class CartCtrl {

    private final Order order;
    private final CartView view;

    public CartCtrl(Order order, CartView view) {
        this.order = order;
        this.view = view;
    }

    /**
     * Agrega producto al carrito.
     * @param p
     * @param qty
     */
    public void addProduct(Product p, int qty) {
        order.addProduct(p, qty);
    }

    /**
     * Elimina producto por ID si existe.
     * @param productId
     * @return 
     */
    public boolean removeProductById(String productId) {
        return order.removeProductById(productId);
    }

    /**
     * Obtiene el pedido.
     * @return 
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Muestra el carrito.
     */
    public void showCart() {
        view.render(order);
    }
}
