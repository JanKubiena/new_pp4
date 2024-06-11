package pl.jkubiena.ecommerce.sales;


import pl.jkubiena.ecommerce.sales.cart.Cart;
import pl.jkubiena.ecommerce.sales.cart.InMemoryCartStorage;
import pl.jkubiena.ecommerce.sales.offer.AcceptOfferRequest;
import pl.jkubiena.ecommerce.sales.offer.Offer;
import pl.jkubiena.ecommerce.sales.offer.OfferCalculator;
import pl.jkubiena.ecommerce.sales.payment.PaymentDetails;
import pl.jkubiena.ecommerce.sales.payment.RegisterPaymentRequest;
import pl.jkubiena.ecommerce.sales.payment.PaymentGateway;
import pl.jkubiena.ecommerce.sales.reservation.Reservation;
import pl.jkubiena.ecommerce.sales.reservation.ReservationDetail;
import pl.jkubiena.ecommerce.sales.reservation.ReservationRepository;

import java.util.UUID;

public class SalesFacade {
    private InMemoryCartStorage cartStorage;
    private OfferCalculator offerCalculator;
    private PaymentGateway paymentGateway;
    private ReservationRepository reservationRepository;

    public SalesFacade(InMemoryCartStorage cartStorage, OfferCalculator offerCalculator, PaymentGateway paymentGateway, ReservationRepository reservationRepository) {
        this.cartStorage = cartStorage;
        this.offerCalculator = offerCalculator;
        this.paymentGateway = paymentGateway;
        this.reservationRepository = reservationRepository;
    }

    public Offer getCurrentOffer(String customerId) {
        Cart cart = loadCartForCustomer(customerId);

        Offer currentOffer = offerCalculator.calculateOffer(cart.getLines());

        return currentOffer;
    }

    public ReservationDetail acceptOffer(String customerId, AcceptOfferRequest acceptOfferRequest) {
        String reservationId = UUID.randomUUID().toString();
        Offer offer = this.getCurrentOffer(customerId);

        PaymentDetails paymentDetails = paymentGateway.registerPayment(
                RegisterPaymentRequest.of(reservationId, acceptOfferRequest, offer.getTotal())
        );
        Reservation reservation = Reservation.of(
                reservationId,
                customerId,
                acceptOfferRequest,
                offer,
                paymentDetails);

        reservationRepository.add(reservation);

        return new ReservationDetail(reservationId, paymentDetails.getPaymentUrl());
    }

    public void addToCart(String customerId, String productId) {
        Cart cart = loadCartForCustomer(customerId);

        cart.addProduct(productId);

        cartStorage.save(customerId, cart);
    }

    private Cart loadCartForCustomer(String customerId) {
        return cartStorage.findByCustomer(customerId)
                .orElse(Cart.empty());
    }
}