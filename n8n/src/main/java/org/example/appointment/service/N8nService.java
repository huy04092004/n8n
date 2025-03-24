package org.example.appointment.service;

import org.example.appointment.model.Appointment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class N8nService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String appointmentWebhookUrl = "http://localhost:5678/webhook-test/appointment";
    private final String cancellationWebhookUrl = "http://localhost:5678/webhook-test/cancel-appointment";

    public void sendAppointmentToN8n(Appointment appointment) {
        sendToN8n(appointmentWebhookUrl, appointment);
    }

    public void sendCancellationToN8n(Appointment appointment) {
        sendToN8n(cancellationWebhookUrl, appointment);
    }

    private void sendToN8n(String url, Appointment appointment) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Appointment> request = new HttpEntity<>(appointment, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            System.out.println("Webhook response: " + response.getStatusCode());
        } catch (Exception e) {
            System.err.println("Lỗi khi gửi webhook đến n8n: " + e.getMessage());
        }
    }
}
