package com.learn.spring_ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AIService {

    private final ChatClient chatClient;;

    public String getAJoke(String Jokes){
        return chatClient.prompt()
                .user("Give me a joke on the topic = "+ Jokes)
                .call()
                .content();
    }
}
