package pl.jkubiena.ecommerce.sales.cart;

import pl.jkubiena.ecommerce.sales.cart.Cart;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryCartStorage {
    Map<String,Cart> carts;

    public InMemoryCartStorage() {
        this.carts = new HashMap<>();
    }

    public Optional<Cart> findByCustomer(String customerId) {
        return Optional.ofNullable(carts.get(customerId));
    }

    public void save(String customerId, Cart cart) {
        carts.put(customerId, cart);
    }
}