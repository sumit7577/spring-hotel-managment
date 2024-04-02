package com.hotel.managment.controllers;

import com.hotel.managment.models.EntertainmentReservation;
import com.hotel.managment.models.Payment;
import com.hotel.managment.models.Reservation;
import com.hotel.managment.models.RoomReservation;
import com.hotel.managment.models.dto.payment.CreatePayment;
import com.hotel.managment.models.dto.payment.CreatePaymentResponse;
import com.hotel.managment.security.SecurityTools;
import com.hotel.managment.services.BookingService;
import com.hotel.managment.services.PaymentService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class serves as the controller for managing payment-related operations in the API. It
 * provides endpoints for creating payment intents for room reservations and entertainment bookings,
 * handling webhook events from Stripe, and access control through role-based authorization.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

  @Value("${stripe.webhook.secret}")
  private String webhookSecret;

  private final PaymentService paymentService;
  private final BookingService bookingService;
  private final SecurityTools securityTools;

  /**
   * Creates a payment intent for a reservation or booking, and returns the client secret along with
   * payment details.
   *
   * @param createPayment The request body containing payment details and reservation information.
   * @return CreatePaymentResponse containing the client secret and payment amount.
   * @throws StripeException if there is an issue with Stripe integration.
   */
  @PostMapping("/create-payment-intent")
  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_CLEANER', 'ROLE_RESTAURANT')")
  public CreatePaymentResponse createPaymentIntent(@RequestBody CreatePayment createPayment)
      throws StripeException {
    log.info("Create payment intent request: {}", createPayment);

    int reservationId = 0;

    if (createPayment.getReservationId() != null) {
      if (createPayment.getPaymentType().equals("Room")) {
        RoomReservation roomReservation =
            bookingService.getRoomReservation(createPayment.getReservationId());
        validateReservation(roomReservation);
        reservationId = roomReservation.getId();
        createPayment.setRoomTypeId(roomReservation.getRoom().getRoomType().getId());
        createPayment.setDateFrom(roomReservation.getFromDate().toString());
        createPayment.setDateTo(roomReservation.getToDate().toString());
      } else {
        EntertainmentReservation entertainmentReservation =
            bookingService.getEntertainmentReservation(createPayment.getReservationId());
        validateReservation(entertainmentReservation);
        reservationId = entertainmentReservation.getId();
        createPayment.setDateFrom(entertainmentReservation.getDateFrom().toString());
        createPayment.setDateTo(entertainmentReservation.getDateTo().toString());
      }
    } else {
      reservationId = paymentService.createReservation(createPayment);
    }

    int paymentAmount = paymentService.getPaymentAmount(createPayment);

    PaymentIntentCreateParams params =
        PaymentIntentCreateParams.builder()
            .setAmount(paymentAmount * 100L)
            .setCurrency("eur")
            .setAutomaticPaymentMethods(
                PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                    .setEnabled(true)
                    .build())
            .putMetadata("paymentAmount", String.valueOf(paymentAmount))
            .putMetadata("reservationId", String.valueOf(reservationId))
            .putMetadata("paymentType", createPayment.getPaymentType())
            .build();
    PaymentIntent paymentIntent = PaymentIntent.create(params);
    return new CreatePaymentResponse(paymentIntent.getClientSecret(), paymentAmount, reservationId);
  }

  /**
   * Handles incoming webhook events from Stripe, such as payment success or failure.
   *
   * @param payload The JSON payload of the webhook event.
   * @param request The HTTP servlet request containing the Stripe-Signature header for signature
   *     verification.
   * @return ResponseEntity indicating the result of webhook event handling.
   */
  @PostMapping("/webhook")
  public ResponseEntity<String> handleWebhook(
      @RequestBody String payload, HttpServletRequest request) {
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

      int paymentAmount = Integer.parseInt(paymentIntent.getMetadata().get("paymentAmount"));
      int reservationId = Integer.parseInt(paymentIntent.getMetadata().get("reservationId"));
      Payment payment = paymentService.createPayment(paymentAmount);
      String paymentType = paymentIntent.getMetadata().get("paymentType");

      if (paymentType.equals("Room")) {
        bookingService.addPaymentToRoomReservation(reservationId, payment);
      } else {
        bookingService.addPaymentToEntertainmentReservation(reservationId, payment);
      }
      log.info("Payment succeeded: {}", paymentIntent.getId());
    } else if (event.getType().equals("payment_intent.payment_failed")) {
      PaymentIntent paymentIntent = (PaymentIntent) event.getData().getObject();
      log.info("Payment failed: {}", paymentIntent.getLastPaymentError().getMessage());
    }
    return ResponseEntity.ok("Success");
  }

  private void validateReservation(Reservation reservation) {
    if (reservation == null) {
      throw new IllegalArgumentException("Reservation not found");
    }
    if (reservation.getUser().getId() != securityTools.retrieveUserData().getId()) {
      throw new IllegalArgumentException("Reservation not found");
    }
    if (reservation.getPayment() != null) {
      throw new IllegalArgumentException("Reservation already paid");
    }
  }
}
