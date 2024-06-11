package pl.jkubiena.ecommerce.infrastructure;

import pl.jkubiena.ecommerce.sales.payment.PaymentDetails;
import pl.jkubiena.ecommerce.sales.payment.PaymentGateway;
import pl.jkubiena.ecommerce.sales.payment.RegisterPaymentRequest;

public class PayUPaymentGateway implements PaymentGateway {
    @Override
    public PaymentDetails registerPayment(RegisterPaymentRequest registerPaymentRequest) {
        return null;
    }
}
