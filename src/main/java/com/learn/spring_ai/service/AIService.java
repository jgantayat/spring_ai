package com.learn.spring_ai.service;

import com.learn.spring_ai.dto.Joke;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AIService {

    private final ChatClient chatClient;;
    private final EmbeddingModel embeddingModel;
    private final VectorStore vectorStore;
    public float[] getEmbedding(String text){
        return embeddingModel.embed(text);
    }

    public void IngestData(String text){
        Document document = new Document(text);
        vectorStore.add(List.of(document));
    }

    public void ingestDocumentToVectorStore(){
       List<Document> documents = List.of(new Document("Spring Boot is a framework that simplifies the development of Java applications by providing a set of tools and conventions for building and deploying applications quickly and easily.",
               Map.of("title", "Spring Boot",
                       "source", "https://spring.io/projects/spring-boot")),
               new Document("A their theft the story of a filmaker from the store room journal.",
                       Map.of("title", "A story stolen from the store room",
                               "source", "https://www.youtube.com/watch?v=QH2-TGUlwu4")),
               new Document("The cat is on the table.",
                       Map.of("title", "The cat is on the table",
                               "source", "https://www.youtube.com/watch?v=QH2-TGUlwu4")),
               new Document("The dog is in the garden.",
                       Map.of("title", "The dog is in the garden",
                               "source", "https://www.youtube.com/watch?v=QH2-TGUlwu4")));
        vectorStore.add((documents));
    }

    public List<Document> getSimilaritySearch(String text){
        return vectorStore.similaritySearch(SearchRequest.builder()
                        .query(text)
                        .topK(2)
                        .similarityThreshold(0.3)
                .build());
    }

    public String getAJoke(String topic){

        String systemPrompt = """
                Yor are sarcastic joker, you joke poetic jokes in four lines.
                You don't make jokes about politics.
                Give a joke on this {topic}.
                """;
        PromptTemplate promptTemplate = new PromptTemplate(systemPrompt);
        String renderedText = promptTemplate.render(Map.of("topic", topic));

        var response = chatClient.prompt()
                .user(renderedText)
                .advisors(new SimpleLoggerAdvisor())
                .call()
                .entity(Joke.class);// response wrapping in  record/DTO object format in JSON
//                .chatClientResponse();

//        return chatClient.prompt()
//                .user("Give me a joke on the topic = "+ topic)
//                .call()
//                .content();

//        return response.chatResponse().getResult().getOutput().getText();
        return response.text();
    }

}
