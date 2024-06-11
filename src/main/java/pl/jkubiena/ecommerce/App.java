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
import pl.jkubiena.ecommerce.sales.productdetails.ProductCatalogProductDetailsProvider;
import pl.jkubiena.ecommerce.sales.productdetails.ProductDetailsProvider;
import java.math.BigDecimal;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        System.out.println("TEST");
        SpringApplication.run(App.class,args);
    }

    @Bean
    ProductCatalog createMyProductCatalog() {
        ProductCatalog productCatalog = new ProductCatalog(new ArrayListProductStorage());
        productCatalog.addProduct("Lego set 1", "nice one", BigDecimal.valueOf(10));
        productCatalog.addProduct("Lego set 2", "nice one", BigDecimal.valueOf(10));
        productCatalog.addProduct("Lego set 3", "nice one", BigDecimal.valueOf(10));

        return productCatalog;
    }

    @Bean
    SalesFacade createSales(ProductDetailsProvider productDetailsProvider){
        return new SalesFacade(
                new InMemoryCartStorage(),
                new OfferCalculator(productDetailsProvider),
                new PayUPaymentGateway(),
                new ReservationRepository()
        );
    }

    @Bean
    ProductDetailsProvider createProductDetailsProvider(ProductCatalog catalog) {
        return new ProductCatalogProductDetailsProvider(catalog);
    }


}
