package com.ai.ollama.springai_ollama_llm.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
@Log4j2
public class OllamaChatService {

    private final OllamaChatModel chatModel;
    private final VectorStore vectorStore;
    @Autowired
    private OllamaEmbeddingModel embeddingModel;

    public OllamaChatService(OllamaChatModel chatModel, VectorStore vectorStore, OllamaChatModel chatModel1, OllamaEmbeddingModel ollamaEmbeddingModel, VectorStore vectorStore1) {
        this.chatModel = chatModel1;
        this.vectorStore = vectorStore1;

    }

    public Flux<String> processMessage(String messagePrompt) {

        Prompt prompt = new Prompt(messagePrompt);
        return chatModel.stream(prompt)
                .map(chatResponse -> {
                    System.out.println(chatResponse.getResult().getOutput().getText());
                    return chatResponse.getResult().getOutput().getText();
                })
                .delayElements(Duration.ofSeconds(1));
    }



    public String getEmbeddedModelResp(String userText) {
        return ChatClient.builder(chatModel)
                .build().prompt()
                .advisors(new QuestionAnswerAdvisor(vectorStore))
                .user(userText)
                .call()
                .chatResponse().getResult().getOutput().getText();
    }

    //This method will return the complete response with all chat metadata
    public Flux<ChatResponse> processMessageChatResponse(String gemma34Dtos) {
        return chatModel.stream(new Prompt(gemma34Dtos));
    }
}
