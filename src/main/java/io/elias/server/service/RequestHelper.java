package io.elias.server.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import io.elias.server.util.Constant;

@Service
public class RequestHelper {

    public String getRandomJoke() {
        StringBuilder request;
        String res = null;
        try {
            request = getRequest(new URL(Constant.RANDOM_JOKE));
            JsonNode parent = new ObjectMapper().readTree(request.toString());
            res = parent.path("value").asText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public String getJokeByCategoryFromREST(String category) {
        StringBuilder request;
        String res = null;
        try {
            request = getRequest(new URL(Constant.JOKE_FROM_CATEGORY.replace("{category}", category)));
            JsonNode parent = new ObjectMapper().readTree(request.toString());
            res = parent.path("value").asText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public List<String> getAllCategories() {
        ObjectMapper objectMapper = new ObjectMapper();
        StringBuilder request;
        List<String> result = new ArrayList<>();
        try {
            request = getRequest(new URL(Constant.CATEGORIES));
            JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, String.class);
            result = objectMapper.readValue(request.toString(), type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static StringBuilder getRequest(URL url) {
        StringBuilder request = new StringBuilder();
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String input;
            while (reader.ready()) {
                input = reader.readLine();
                request.append(input);
            }
        } catch (IOException ignored) {

        }
        return request;
    }

}
