package controller;

import java.util.List;
import model.Product;
import view.ProductView;

/**
 * Controlador para catálogo de productos.
 */
public class ProductCtrl {

    private final List<Product> catalog;
    private final ProductView view;

    public ProductCtrl(List<Product> catalog, ProductView view) {
        this.catalog = catalog;
        this.view = view;
    }

    /**
     * Exponer catálogo (para menú).
     * @return 
     */
    public List<Product> getCatalog() {
        return catalog;
    }

    /**
     * Muestra la lista de productos.
     */
    public void showProducts() {
        view.render(catalog);
    }
}
