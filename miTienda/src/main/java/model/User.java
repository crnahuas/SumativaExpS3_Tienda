package model;

/**
 * Modelo de usuario/cliente.
 */
public class User {

    private final String id;
    private final String fullName;
    private final String email;

    public User(String id, String fullName, String email) {
        if (id == null || fullName == null || email == null) {
            throw new IllegalArgumentException("Parámetros nulos no permitidos");
        }
        this.id = id;
        this.fullName = fullName;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }
}
