package com.sky.controller.admin;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sky.entity.DeeseekRequest;


@Slf4j
@RestController
public class AIController {

    private final Gson gson = new Gson();


    @PostMapping("tall")
    public String tallQuestion(@RequestParam String question) throws IOException, UnirestException {

        log.info("调试deepseek");

        Unirest.setTimeouts(0, 0);

//DeeseekRequest: 自己的实体类名称

        List<DeeseekRequest.Message> messages = new ArrayList<>();
//给deepSeek一个角色
        messages.add(DeeseekRequest.Message.builder().role("system").content("你是一个语言学家").build());

// question：说你自己想说的话
        messages.add(DeeseekRequest.Message.builder().role("user").content(question).build());

        DeeseekRequest requestBody = DeeseekRequest.builder()
                .model("deepseek-chat")
                .messages(messages)
                .build();
        HttpResponse<String> response = Unirest.post("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Bearer "+"sk-e21e54d23f084f51bcf687ebaabd80af")
                .body(gson.toJson(requestBody))
                .asString();

        log.info("返回为： {}",response.getBody());
        return  response.getBody();

    }

}