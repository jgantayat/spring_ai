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

    @Test
    public void testGetEmbedding(){
        String text = "What is Spring Boot?";
        var embedding = aiService.getEmbedding(text);
        System.out.println("Embedding length = "+ embedding.length);
    }

    @Test
    public void testIngestData(){
        String text = "Spring Boot is a framework that simplifies the development of Java applications by providing a set of tools and conventions for building and deploying applications quickly and easily.";
        aiService.IngestData(text);
    }

    @Test
    public void testIngestDocumentToVectorStore(){
        aiService.ingestDocumentToVectorStore();
    }

    @Test
    public void testGetSimilaritySearch(){
        String text = "What is Spring Boot?";
        var documents = aiService.getSimilaritySearch(text);
        System.out.println("Documents = "+ documents);
    }
}