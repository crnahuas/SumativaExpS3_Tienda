package model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Modelo de producto (precio bruto sin descuentos).
 */
/**
 * Modelo de producto (precio bruto sin descuentos).
 */
public class Product {

    private final String id;
    private final String name;
    private final String category;
    private final BigDecimal price; // precio bruto

    public Product(String id, String name, String category, BigDecimal price) {
        if (id == null || name == null || category == null || price == null) {
            throw new IllegalArgumentException("Parámetros nulos no permitidos");
        }
        if (price.signum() < 0) {
            throw new IllegalArgumentException("Precio no puede ser negativo");
        }
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
