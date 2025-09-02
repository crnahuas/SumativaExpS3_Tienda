package mitienda;

import command.AddToCartCmd;
import command.CategoryDiscountCmd;
import command.GlobalDiscountCmd;
import command.RemoveFromCartCmd;
import controller.CartCtrl;
import controller.DiscountCtrl;
import controller.ProductCtrl;
import model.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Menú de consola: orquesta acciones con Controladores y Commands.
 */
public class Menu {

    private final ProductCtrl productCtrl;
    private final CartCtrl cartCtrl;
    private final DiscountCtrl discountCtrl;

    public Menu(ProductCtrl productCtrl, CartCtrl cartCtrl, DiscountCtrl discountCtrl) {
        this.productCtrl = productCtrl;
        this.cartCtrl = cartCtrl;
        this.discountCtrl = discountCtrl;
    }

    /**
     * Inicia el bucle principal del menú.
     */
    public void start() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean running = true;
        while (running) {
            try {
                printHeader();
                String opt = prompt(br, "Elige una opción: ");
                switch (opt) {
                    case "1":
                        productCtrl.showProducts();
                        pause(br);
                        break;
                    case "2":
                        discountsSubmenu(br);
                        break;
                    case "3":
                        addProductFlow(br);
                        break;
                    case "4":
                        removeProductFlow(br);
                        break;
                    case "5":
                        discountCtrl.showActiveDiscounts();
                        pause(br);
                        break;
                    case "6":
                        cartCtrl.showCart();
                        pause(br);
                        break;
                    case "0":
                        running = false;
                        System.out.println("Saliendo... ¡Gracias!");
                        break;
                    default:
                        System.out.println("Opción inválida. Intenta nuevamente.\n");
                }
            } catch (IOException e) {
                System.out.println("Error de lectura: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Atención: " + e.getMessage() + "\n");
            }
        }
    }

    private void printHeader() {
        System.out.println("========================================");
        System.out.println("   Sistema de Pedidos Online  ");
        System.out.println("========================================");
        System.out.println("1) Ver catálogo de productos");
        System.out.println("2) Configurar descuentos");
        System.out.println("3) Agregar producto al carrito");
        System.out.println("4) Eliminar producto del carrito");
        System.out.println("5) Ver descuentos activos");
        System.out.println("6) Ver carrito");
        System.out.println("0) Salir");
        System.out.println("========================================");
    }

    /**
     * Submenú claro para descuentos.
     */
    private void discountsSubmenu(BufferedReader br) throws IOException {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Descuentos ---");
            System.out.println("1) Establecer % GLOBAL (10 | 10% | 0.10 | 0,10)");
            System.out.println("2) Limpiar % GLOBAL");
            System.out.println("3) Establecer % por CATEGORÍA");
            System.out.println("4) Limpiar % por CATEGORÍA");
            System.out.println("5) Ver resumen de descuentos");
            System.out.println("0) Volver");
            String op = prompt(br, "Opción: ");
            switch (op) {
                case "1":
                    String p = prompt(br, "% GLOBAL: ");
                    BigDecimal g = parsePercentFlexible(p);
                    new GlobalDiscountCmd(discountCtrl, g).Ejecutar();
                    discountCtrl.showActiveDiscounts();
                    break;
                case "2":
                    new GlobalDiscountCmd(discountCtrl, null).Ejecutar();
                    discountCtrl.showActiveDiscounts();
                    break;
                case "3":
                    String cat = prompt(br, "Categoría (ej: Pantalones): ");
                    if (cat.isBlank()) {
                        System.out.println("Categoría no puede ser vacía.");
                        break;
                    }
                    String pc = prompt(br, "% para '" + cat + "': ");
                    BigDecimal c = parsePercentFlexible(pc);
                    new CategoryDiscountCmd(discountCtrl, cat, c).Ejecutar();
                    discountCtrl.showActiveDiscounts();
                    break;
                case "4":
                    new CategoryDiscountCmd(discountCtrl, null, null).Ejecutar();
                    discountCtrl.showActiveDiscounts();
                    break;
                case "5":
                    discountCtrl.showActiveDiscounts();
                    break;
                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("Opción inválida.\n");
            }
        }
    }

    private void addProductFlow(BufferedReader br) throws IOException {
        productCtrl.showProducts();
        String id = prompt(br, "ID del producto (ej: P001): ");
        Product p = productCtrl.getCatalog().stream()
                .filter(prod -> prod.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado: " + id));
        int qty = parsePositiveInt(prompt(br, "Cantidad (>0): "));
        new AddToCartCmd(cartCtrl, p, qty).Ejecutar();
        System.out.println("Producto agregado.\n");
        cartCtrl.showCart();
        pause(br);
    }

    private void removeProductFlow(BufferedReader br) throws IOException {
        cartCtrl.showCart();
        String id = prompt(br, "ID del producto a eliminar (ej: P001): ");
        new RemoveFromCartCmd(cartCtrl, id).Ejecutar();
        System.out.println("Producto eliminado con éxito.\n");
        cartCtrl.showCart();
        pause(br);
    }

    // ---------- utilidades ----------
    private String prompt(BufferedReader br, String label) throws IOException {
        System.out.print(label);
        String s = br.readLine();
        return s == null ? "" : s.trim();
    }

    private void pause(BufferedReader br) throws IOException {
        System.out.print("Presiona ENTER para continuar...");
        br.readLine();
        System.out.println();
    }

    private int parsePositiveInt(String s) {
        try {
            int n = Integer.parseInt(s.trim());
            if (n <= 0) {
                throw new NumberFormatException();
            }
            return n;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Debes ingresar un entero positivo.");
        }
    }

    /**
     * Convierte "10", "10%", "0.10", "0,10" en 0..1 (BigDecimal).
     */
    private BigDecimal parsePercentFlexible(String raw) {
        String s = (raw == null ? "" : raw.trim());
        if (s.isEmpty()) {
            throw new IllegalArgumentException("Porcentaje requerido.");
        }
        s = s.replace("%", "").replace(",", ".");
        BigDecimal val;
        try {
            val = new BigDecimal(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Formato inválido. Usa 10, 10% o 0.10");
        }
        if (val.compareTo(BigDecimal.ONE) > 0) { // 10 -> 0.10
            val = val.divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP);
        }
        if (val.signum() < 0 || val.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("El porcentaje debe estar entre 0 y 100.");
        }
        return val;
    }
}
