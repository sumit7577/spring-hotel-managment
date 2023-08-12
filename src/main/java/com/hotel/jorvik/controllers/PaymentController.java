package com.hotel.jorvik.controllers;

import com.hotel.jorvik.models.DTO.payment.CreatePayment;
import com.hotel.jorvik.models.DTO.payment.CreatePaymentResponse;
import com.hotel.jorvik.services.PaymentService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;
    private final PaymentService paymentService;

    @PostMapping("/create-payment-intent")
    public CreatePaymentResponse createPaymentIntent(@RequestBody CreatePayment createPayment) throws StripeException {
        int paymentAmount = 0;
        if (createPayment.getPaymentType() == CreatePayment.PaymentType.ROOM_PAYMENT) {
            paymentAmount = paymentService.getRoomPaymentAmount(createPayment.getRoomTypeId(), createPayment.getDateFrom(), createPayment.getDateTo());
        } else if (createPayment.getPaymentType() == CreatePayment.PaymentType.BICYCLE_PAYMENT) {
            paymentAmount = paymentService.getBicyclePaymentAmount(createPayment.getTimestampFrom());
        } else if (createPayment.getPaymentType() == CreatePayment.PaymentType.KAYAK_PAYMENT) {
            paymentAmount = paymentService.getKayakPaymentAmount(createPayment.getTimestampFrom());
        } else if (createPayment.getPaymentType() == CreatePayment.PaymentType.TENNIS_PAYMENT){
            paymentAmount = paymentService.getTennisPaymentAmount(createPayment.getTimestampFrom());
        } else {
            throw new IllegalArgumentException("Payment type not supported");
        }

        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(paymentAmount * 100L)
                        .setCurrency("eur")
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build()
                        )
                        .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);
        return new CreatePaymentResponse(paymentIntent.getClientSecret(),paymentAmount);
    }

    // Add logging
    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload, HttpServletRequest request) {
        // Validate the webhook signature
        String sigHeader = request.getHeader("Stripe-Signature");
        Event event = null;
        try {
            event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature.");
        }

        // Handle the event
        if (event.getType().equals("payment_intent.succeeded")) {
            PaymentIntent paymentIntent = (PaymentIntent) event.getData().getObject();
            System.out.println("PaymentIntent was successful!");
            // Handle payment success
        } else if (event.getType().equals("payment_intent.payment_failed")) {
            PaymentIntent paymentIntent = (PaymentIntent) event.getData().getObject();
            System.out.println("PaymentIntent failed!");
            // Handle payment failure
        }
        return ResponseEntity.ok("Success");
    }
}
