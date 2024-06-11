package pl.jkubiena.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.jkubiena.ecommerce.catalog.ArrayListProductStorage;
import pl.jkubiena.ecommerce.catalog.ProductCatalog;
import pl.jkubiena.ecommerce.infrastructure.PayUPaymentGateway;
import pl.jkubiena.ecommerce.sales.SalesFacade;
import pl.jkubiena.ecommerce.sales.cart.InMemoryCartStorage;
import pl.jkubiena.ecommerce.sales.offer.OfferCalculator;
import pl.jkubiena.ecommerce.sales.reservation.ReservationRepository;

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
        return new SalesFacade(
                new InMemoryCartStorage(),
                new OfferCalculator(),
                new PayUPaymentGateway(),
                new ReservationRepository()
        );
    }
}
