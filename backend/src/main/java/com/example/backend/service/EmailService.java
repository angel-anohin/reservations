package com.example.backend.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public EmailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }
    @Async
    public void sendReservationEmail(String to, String name, String reservationDate, Long reservationId) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("reservationDate", reservationDate);
        context.setVariable("reservationId", reservationId);

        String html = templateEngine.process("reservation-email.html", context);

        helper.setFrom("angelanohin8@gmail.com");
        helper.setTo(to);
        helper.setSubject("Потвърждение за резервация");
        helper.setText(html, true); // true = HTML
        mailSender.send(message);
    }
}
