package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.Impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.SendSMSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class SendSMSServiceImp implements SendSMSService {

    @Value("${sms.url}")
    private String smsUrl;

    @Value("${mytel.adapter.smsgw.clientId}")
    private String username;

    @Value("${mytel.adapter.smsgw.clientSecret}")
    private String password;

    private Logger logger = LoggerFactory.getLogger(SendSMSServiceImp.class);

    @Override
    public void sendSMS(String source, String address, String content) {
        logger.info("Send SMS from {}, address {}, content {}", source, address, content);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(username, password);

        // Build the request body with the new structure
        Map<String, String> body = new HashMap<>();
        body.put("requestId", generateRequestId());
        body.put("requestTime", Instant.now().toString());
        body.put("content", content);
        body.put("msisdn", address);
        body.put("sender", source);

        HttpEntity<?> request = new HttpEntity<>(body, headers);

        try {
            String response = restTemplate.postForObject(smsUrl, request, String.class);
            logger.info("Send SMS response {}", response);

            // Parse the response JSON (using a library like Jackson or Gson)
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode responseNode = objectMapper.readTree(response);

            String errorCode = responseNode.path("errorCode").asText();
            String message = responseNode.path("message").asText();

            if ("00000".equals(errorCode)) {
                JsonNode result = responseNode.path("result");
                String transactionId = result.path("transactionId").asText();
                String submittedAt = result.path("submittedAt").asText();
                String status = result.path("status").asText();

                // Log the transaction details or handle them as needed
                logger.info("SMS sent successfully with transactionId: {}, status: {}, submittedAt: {}", transactionId, status, submittedAt);
            } else {
                // Handle failure or non-zero errorCode
                logger.error("Failed to send SMS: {}", message);
            }
        } catch (Exception e) {
            logger.error("Exception occurred while sending SMS", e);
        }
    }

    // Generate unique requestId (you can use a UUID or a custom approach)
    private String generateRequestId() {
        return UUID.randomUUID().toString();
    }
}
