package com.hotel.managment.security.implementation;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import java.io.IOException;
import java.util.Map;
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
   * Sends an email to the specified recipient.
   *
   * @param to            the recipient's email address
   * @param templateId    the ID of the email template to use
   * @param substitutions a map of substitutions to apply to the email template
   */
  public void sendEmail(String to, String templateId, Map<String, String> substitutions) {
    Email fromEmail = new Email(emailFrom);
    Email toEmail = new Email(to);
    Mail mail = new Mail();
    mail.setFrom(fromEmail);
    mail.setTemplateId(templateId);
    Personalization personalization = new Personalization();
    personalization.addTo(toEmail);

    // Add substitutions
    for (Map.Entry<String, String> entry : substitutions.entrySet()) {
      personalization.addDynamicTemplateData(entry.getKey(), entry.getValue());
    }

    mail.addPersonalization(personalization);

    Request request = new Request();
    try {
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());
      Response response = sendGrid.api(request);
      log.info(response.getStatusCode() +
          " "
          + response.getBody() +
          " "
          + response.getHeaders());
      if (response.getStatusCode() != 202) {
        throw new RuntimeException("Email not sent");
      }
    } catch (IOException ex) {
      log.error(ex.getMessage(), ex);
    }
  }
}