package com.hotel.jorvik.security.implementation;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service class for sending emails.
 *
 * <p>This class provides functionality to send emails using SendGrid's API. It encapsulates the
 * process of setting up email content and dispatching the email to the specified recipient.
 */
@Service
@Slf4j
public class EmailSender {

  private final SendGrid sendGrid;

  @Value("${email.from}")
  private String emailFrom;

  @Autowired
  public EmailSender(SendGrid sendGrid) {
    this.sendGrid = sendGrid;
  }

  /**
   * Sends an email using the SendGrid email service.
   *
   * @param to The email address of the recipient.
   * @param subject The subject of the email.
   * @param content The content (body) of the email.
   * @throws IllegalArgumentException If the email could not be sent.
   */
  public void sendEmail(String to, String subject, String content) {
    Content contentEmail = new Content("text/plain", content);
    Email fromEmail = new Email(emailFrom);
    Email toEmail = new Email(to);
    Mail mail = new Mail(fromEmail, subject, toEmail, contentEmail);

    Request request = new Request();
    try {
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());
      Response response = sendGrid.api(request);
      log.info(response.getStatusCode() + " " + response.getBody() + " " + response.getHeaders());
      if (response.getStatusCode() != 202) {
        System.out.println(response);
        throw new RuntimeException("Email not sent");
      }
    } catch (IOException ex) {
      log.error(ex.getMessage(), ex);
    }
  }
}
