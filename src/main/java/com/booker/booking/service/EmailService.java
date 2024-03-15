package com.booker.booking.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(
            JavaMailSender javaMailSender
    ) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String to, String subject, String body) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public String generateEmail(
            String customerName,
            String bookingId,
            LocalDate checkInDate,
            LocalDate checkOutDate,
            int numberOfPeople,
            BigDecimal totalPrice
    ) {
        String emailTemplate = "Assunto: Confirmação de Reserva\n\n" +
                "Olá %s,\n\n" +
                "Agradecemos por escolher nosso estabelecimento para sua estadia! Estamos muito felizes em recebê-lo(a).\n\n" +
                "Aqui estão os detalhes da sua reserva:\n\n" +
                "- Número da Reserva: %s\n" +
                "- Nome do Cliente: %s\n" +
                "- Data de Check-in: %s\n" +
                "- Data de Check-out: %s\n" +
                "- Número de Pessoas: %d\n\n" +
                "Total a Pagar: R$ %.2f\n\n" +
                "Se houver alguma informação adicional que você precise ou se tiver alguma dúvida, não hesite em nos contatar. Estamos aqui para ajudar!\n\n" +
                "Agradecemos novamente por escolher nosso estabelecimento. Mal podemos esperar para recebê-lo(a) em breve!\n\n" +
                "Atenciosamente,\n" +
                "Equipe de Reservas";

        String formattedCheckInDate = checkInDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String formattedCheckOutDate = checkOutDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        return String.format(
                emailTemplate,
                customerName,
                bookingId,
                customerName,
                formattedCheckInDate,
                formattedCheckOutDate,
                numberOfPeople,
                totalPrice
        );
    }
}
