package pl.jkubiena.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.jkubiena.ecommerce.catalog.ArrayListProductStorage;
import pl.jkubiena.ecommerce.catalog.ProductCatalog;
import pl.jkubiena.ecommerce.sales.SalesFacade;

@SpringBootApplication
public class App {
    public static void main(String[] args){
        System.out.println("Here we go!");

        SpringApplication.run(App.class, args);
    }

    @Bean
    ProductCatalog createMyProductCatalog() {
        ProductCatalog productCatalog = new ProductCatalog(new ArrayListProductStorage());
        productCatalog.addProduct("Legoset 9231", "Nice One");
        productCatalog.addProduct("Legoset 7832", "Nice Two");
        productCatalog.addProduct("Legoset 1046", "Nice Three");

        return productCatalog;
    }

    @Bean
    SalesFacade createSales(){
        return new SalesFacade();
    }
}
