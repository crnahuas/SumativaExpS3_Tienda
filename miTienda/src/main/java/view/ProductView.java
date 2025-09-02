package view;

import java.util.List;
import model.Product;

/**
 * Vista simple de catálogo.
 */
public class ProductView {

    /**
     * Imprime catálogo con id, nombre, categoría y precio.
     */
    public void render(List<Product> products) {
        System.out.println("=== Catálogo de productos ===");
        for (Product p : products) {
            System.out.printf("- [%s] %s | Cat: %s | Precio: $%s%n",
                    p.getId(), p.getName(), p.getCategory(), p.getPrice());
        }
        System.out.println();
    }
}
