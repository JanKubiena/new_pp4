package pl.jkubiena.ecommerce.catalog;

import java.math.BigDecimal;
import java.util.*;

public class ProductCatalog {
    private ArrayList<Product> products;
    ArrayListProductStorage arrayListProductStorage;

    public ProductCatalog() {
        this.products = new ArrayList<>();
        this.arrayListProductStorage = new ArrayListProductStorage();
    }

    public List<Product> allProducts() {
        return Collections.unmodifiableList(products);
        return arrayListProductStorage.allProducts();
    }

    public String addProduct(String name, String description) {
        UUID id = UUID.randomUUID();

        Product newProduct = new Product(id, name, description);

        products.add(newProduct);
        arrayListProductStorage.addProduct(newProduct);

        return id.toString();
    }

    public Product getProductBy(String id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .get();
        return arrayListProductStorage.getProductBy(id);
    }

    public void changePrice(String id, BigDecimal price) {
        Product loadedProduct = getProductBy(id);
        loadedProduct.changePrice(price);
    }

}
