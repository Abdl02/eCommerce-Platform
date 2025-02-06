package com.ecommerce_platform.infra.util;

import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

@Component
public class EmailUtil {

    private Writer file;

    public void sendPaymentConfirmation(String toEmail, String subject, String body) throws IOException {
        BufferedWriter buffer = new BufferedWriter(file);

        buffer.write("Sending email to " + toEmail);
        buffer.write("Subject: " + subject);
        buffer.write("Body: " + body);
    }
}