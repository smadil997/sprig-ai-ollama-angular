package com.ai.ollama.springai_ollama_llm.Gemma34bService;

import com.ai.ollama.springai_ollama_llm.dtos.Gemma34Dtos;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
@Log4j2
public class OllamaChatService {

    private final OllamaChatModel chatModel;

    public OllamaChatService(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public Flux<String> processMessage(String messagePrompt) {
        Prompt prompt = new Prompt(messagePrompt);
        // Stream response and map to just the content
        return chatModel.stream(prompt)
                .map(chatResponse -> {
                    System.out.println(chatResponse.getResult().getOutput().getText());
                    return chatResponse.getResult().getOutput().getText();
                })
                .delayElements(Duration.ofSeconds(1));
    }

    //This method will return the complete response with all chat metadata
    public Flux<ChatResponse> processMessageChatResponse(String gemma34Dtos) {
        return chatModel.stream(new Prompt(gemma34Dtos));
    }
}
