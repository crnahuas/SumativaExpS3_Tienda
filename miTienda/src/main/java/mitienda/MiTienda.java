package mitienda;

import controller.CartCtrl;
import controller.DiscountCtrl;
import controller.ProductCtrl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import model.Order;
import model.Product;
import model.User;
import view.CartView;
import view.DiscountView;
import view.ProductView;

public class MiTienda {

    public static void main(String[] args) {
        // --- Datos base (Model) ---
        List<Product> catalog = new ArrayList<>();
        catalog.add(new Product("P001", "Polera básica", "Poleras", new BigDecimal("9990")));
        catalog.add(new Product("P002", "Pantalón denim", "Pantalones", new BigDecimal("24990")));
        catalog.add(new Product("P003", "Chaqueta", "Abrigos", new BigDecimal("39990")));

        User user = new User("U001", "Juan Pérez", "juan.perez@gmail.com");
        Order order = new Order(user);

        // --- Vistas (View) ---
        ProductView productView = new ProductView();
        CartView cartView = new CartView();
        DiscountView discountView = new DiscountView();

        // --- Controladores (Controller) ---
        ProductCtrl productCtrl = new ProductCtrl(catalog, productView);
        CartCtrl cartCtrl = new CartCtrl(order, cartView);
        DiscountCtrl discountCtrl = new DiscountCtrl(discountView);

        // --- Menú de consola (OJO: ConsoleMenu, no Menu) ---
        Menu menu = new Menu(productCtrl, cartCtrl, discountCtrl);
        menu.start();
    }
}
