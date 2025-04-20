package com.sky.controller.admin;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class DeepSeekController {
    @Value("${ai.deepseek.api-key}")
    private String apiKey;

    @PostMapping("/chat")
    public String chat(@RequestParam String prompt) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost("https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions");
        post.setHeader("Authorization", "Bearer " + apiKey);
        post.setHeader("Content-Type", "application/json");

        String jsonBody = String.format("{\"model\": \"deepseek-r1\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}", prompt);
        post.setEntity(new StringEntity(jsonBody));

        try (CloseableHttpResponse response = client.execute(post)) {
            return EntityUtils.toString(response.getEntity());
        }
    }
}