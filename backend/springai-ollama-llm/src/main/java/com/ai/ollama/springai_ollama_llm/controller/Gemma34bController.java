package com.ai.ollama.springai_ollama_llm.controller;

import com.ai.ollama.springai_ollama_llm.Gemma34bService.OllamaChatService;
import com.ai.ollama.springai_ollama_llm.dtos.Gemma34Dtos;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class Gemma34bController {

    @Autowired
    private OllamaChatService gemma34bService;


    @GetMapping(path = "/message/{prompt}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> promResponse(@PathVariable String prompt){
        return Flux
                .just("retry: 0\n") // Inject SSE instruction to client
                .concatWith(gemma34bService.processMessage(prompt));
    }


}
