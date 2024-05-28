package com.example.sapbusinessone.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BusinessOneService {

    private static final Logger logger = LoggerFactory.getLogger(BusinessOneService.class);

    private final String SERVICE_LAYER_URL = "http://10.31.22.68:8082/b1s/v1/";

    private String sessionId;
    private String routeId;

    public String login(String username, String password, String companyDB) {
        RestTemplate restTemplate = new RestTemplate();
        String url = SERVICE_LAYER_URL + "Login";

        // Create request body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("CompanyDB", companyDB);
        requestBody.put("UserName", username);
        requestBody.put("Password", password);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create request entity
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            logger.info("Sending login request to: " + url);
            logger.info("Request headers: " + headers);
            logger.info("Request body: " + requestBody);

            // Send request and receive response
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);

            logger.info("Response status code: " + response.getStatusCode());
            logger.info("Response headers: " + response.getHeaders());
            logger.info("Response body: " + response.getBody());

            // Check if the response contains an error
            if (response.getBody().containsKey("error")) {
                logger.error("Login failed: " + response.getBody().get("error").toString());
                return "Login failed: " + response.getBody().get("error").toString();
            }

            // Extract session ID and route ID from the response headers
            HttpHeaders responseHeaders = response.getHeaders();
            for (String header : responseHeaders.get("Set-Cookie")) {
                if (header.startsWith("B1SESSION")) {
                    sessionId = header.split(";")[0];
                }
                if (header.startsWith("ROUTEID")) {
                    routeId = header.split(";")[0];
                }
            }

            return "Login successful";
        } catch (HttpClientErrorException e) {
            logger.error("HTTP error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
            return "HTTP error: " + e.getStatusCode();
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            return "An error occurred: " + e.getMessage();
        }
    }

    public String testConnection() {
        if (sessionId == null || routeId == null) {
            return "Not logged in";
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = SERVICE_LAYER_URL + "Ping";

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", sessionId + "; " + routeId);

        // Create request entity
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // Send request and receive response
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        return response.getBody();
    }

    public String getOrders(String filter) {
        if (sessionId == null || routeId == null) {
            return "Not logged in";
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = SERVICE_LAYER_URL + "Orders" + (filter != null ? "?" + filter : "");

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", sessionId + "; " + routeId);

        // Create request entity
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        try {
            logger.info("Sending GET request to: " + url);
            logger.info("Request headers: " + headers);

            // Send request and receive response
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

            logger.info("Response status code: " + response.getStatusCode());
            logger.info("Response headers: " + response.getHeaders());
            logger.info("Response body: " + response.getBody());

            return response.getBody();
        } catch (HttpClientErrorException e) {
            logger.error("HTTP error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
            return "HTTP error: " + e.getStatusCode();
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            return "An error occurred: " + e.getMessage();
        }
    }

    public String getOrderById(String id) {
        if (sessionId == null || routeId == null) {
            return "Not logged in";
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = SERVICE_LAYER_URL + "Orders(" + id + ")";

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", sessionId + "; " + routeId);

        // Create request entity
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        try {
            logger.info("Sending GET request to: " + url);
            logger.info("Request headers: " + headers);

            // Send request and receive response
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

            logger.info("Response status code: " + response.getStatusCode());
            logger.info("Response headers: " + response.getHeaders());
            logger.info("Response body: " + response.getBody());

            return response.getBody();
        } catch (HttpClientErrorException e) {
            logger.error("HTTP error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
            return "HTTP error: " + e.getStatusCode();
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            return "An error occurred: " + e.getMessage();
        }
    }

    public String getAllUserDefinedObjects() {
        if (sessionId == null || routeId == null) {
            return "Not logged in";
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = SERVICE_LAYER_URL + "UserObjectsMD";

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", sessionId + "; " + routeId);

        // Create request entity
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        try {
            logger.info("Sending GET request to: " + url);
            logger.info("Request headers: " + headers);

            // Send request and receive response
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

            logger.info("Response status code: " + response.getStatusCode());
            logger.info("Response headers: " + response.getHeaders());
            logger.info("Response body: " + response.getBody());

            return response.getBody();
        } catch (HttpClientErrorException e) {
            logger.error("HTTP error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
            return "HTTP error: " + e.getStatusCode();
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            return "An error occurred: " + e.getMessage();
        }
    }

    public String getUserDefinedObjectById(String id) {
        if (sessionId == null || routeId == null) {
            return "Not logged in";
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = SERVICE_LAYER_URL + "UserObjectsMD('" + id + "')";

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", sessionId + "; " + routeId);

        // Create request entity
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        try {
            logger.info("Sending GET request to: " + url);
            logger.info("Request headers: " + headers);

            // Send request and receive response
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

            logger.info("Response status code: " + response.getStatusCode());
            logger.info("Response headers: " + response.getHeaders());
            logger.info("Response body: " + response.getBody());

            return response.getBody();
        } catch (HttpClientErrorException e) {
            logger.error("HTTP error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
            return "HTTP error: " + e.getStatusCode();
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            return "An error occurred: " + e.getMessage();
        }
    }
    public List<String> getUsers(String filter) {
        if (sessionId == null || routeId == null) {
            return null;
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = SERVICE_LAYER_URL + "Users" + (filter != null ? "?" + filter : "");

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", sessionId + "; " + routeId);

        // Create request entity
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        try {
            logger.info("Sending GET request to: " + url);
            logger.info("Request headers: " + headers);

            // Send request and receive response
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Map.class);

            logger.info("Response status code: " + response.getStatusCode());
            logger.info("Response headers: " + response.getHeaders());
            logger.info("Response body: " + response.getBody());

            List<Map<String, Object>> users = (List<Map<String, Object>>) response.getBody().get("value");
            List<String> userCodes = new ArrayList<>();
            for (Map<String, Object> user : users) {
                userCodes.add((String) user.get("UserCode"));
            }

            return userCodes;
        } catch (HttpClientErrorException e) {
            logger.error("HTTP error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
            return null;
        } catch (Exception e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            return null;
        }
    }
}
