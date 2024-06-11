package pl.jkubiena.ecommerce.sales;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.jkubiena.ecommerce.sales.cart.InMemoryCartStorage;
import pl.jkubiena.ecommerce.sales.offer.Offer;
import pl.jkubiena.ecommerce.sales.offer.OfferCalculator;
import pl.jkubiena.ecommerce.sales.productdetails.InMemoryProductDetailsProvider;
import pl.jkubiena.ecommerce.sales.reservation.ReservationRepository;
import pl.jkubiena.ecommerce.sales.reservation.SpyPaymentGateway;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

public class SalesTest {

    private InMemoryProductDetailsProvider productDetails;

    @BeforeEach
    void setUp() {
        this.productDetails = new InMemoryProductDetailsProvider();
    }
    @Test
    void itShowsOffer(){
        SalesFacade sales = thereIsSAlesFacade();
        String customerId = thereIsExampleCustomer("Emil");

        Offer offer = sales.getCurrentOffer(customerId);

        assertEquals(0, offer.getItemsCount());
        assertEquals(BigDecimal.ZERO, offer.getTotal());
    }

    private String thereIsExampleCustomer(String id) {
        return id;
    }

    private SalesFacade thereIsSAlesFacade() {
        return new SalesFacade(
                new InMemoryCartStorage(),
                new OfferCalculator(productDetails),
                new SpyPaymentGateway(),
                new ReservationRepository()
        );
    }

    @Test
    void itAllowsToAddProductToCart(){
        var customerId = thereIsExampleCustomer("Emil");
        var productId = thereIsProduct("product", BigDecimal.valueOf(10));

        SalesFacade sales = thereIsSAlesFacade();

        sales.addToCart(customerId, productId);

        Offer offer = sales.getCurrentOffer(customerId);
        assertEquals(BigDecimal.valueOf(10), offer.getTotal());
        assertEquals(1, offer.getItemsCount());

    }

    @Test
    void itAllowsToAddMultipleProductsToCart(){
        var customerId = thereIsExampleCustomer("Emil");
        var productA = thereIsProduct("product a", BigDecimal.valueOf(10));
        var productB = thereIsProduct("product b", BigDecimal.valueOf(20));

        SalesFacade sales = thereIsSAlesFacade();

        sales.addToCart(customerId, productA);
        sales.addToCart(customerId, productB);

        Offer offer = sales.getCurrentOffer(customerId);
        assertEquals(BigDecimal.valueOf(30), offer.getTotal());
        assertEquals(2, offer.getItemsCount());

    }

    @Test
    void itDoesNotShareCustomersCarts(){
        var customerA = thereIsExampleCustomer("Emil");
        var customerB = thereIsExampleCustomer("XYZ");
        var productA = thereIsProduct("product a", BigDecimal.valueOf(10));
        var productB = thereIsProduct("product b", BigDecimal.valueOf(20));

        SalesFacade sales = thereIsSAlesFacade();

        sales.addToCart(customerA, productA);
        sales.addToCart(customerB, productB);

        Offer offerA = sales.getCurrentOffer(customerA);
        assertEquals(BigDecimal.valueOf(10), offerA.getTotal());

        Offer offerB = sales.getCurrentOffer(customerB);
        assertEquals(BigDecimal.valueOf(20), offerB.getTotal());

    }



    private String thereIsProduct(String name, BigDecimal price) {
        return name;
    }

    @Test
    void itAllowsToRemoveProductFromCart(){

    }

    @Test
    void itAllowsToAcceptOffer(){
    }

}
