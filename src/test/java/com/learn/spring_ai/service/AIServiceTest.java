package com.learn.spring_ai.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AIServiceTest {

    @Autowired
    private AIService aiService;

    @Test
    public void testGetJokes(){
        String joke =" Tell me a joke about corporate";
        var content = aiService.getAJoke(joke);
        System.out.println("Joke = "+ content);
    }

}