package com.learn.spring_ai.service;

import com.learn.spring_ai.dto.Joke;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AIService {

    private final ChatClient chatClient;;

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
