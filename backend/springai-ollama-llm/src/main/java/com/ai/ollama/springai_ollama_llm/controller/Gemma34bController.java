package com.ai.ollama.springai_ollama_llm.controller;

import com.ai.ollama.springai_ollama_llm.service.OllamaChatService;
import com.ai.ollama.springai_ollama_llm.service.RagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class Gemma34bController {

    @Autowired
    private OllamaChatService gemma34bService;

    @Autowired
    private RagService ragService;

    @GetMapping("update/{fileName}")
    public String updateDataBase(@PathVariable String fileName) throws IOException {
        ragService.ingestPDFDataIntoDB(fileName);
        return "File Uploaded";
    }

    @GetMapping(path = "/message/{prompt}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> promResponse(@PathVariable String prompt) {
        return Flux
                .just("retry: 0\n") // Inject SSE instruction to client
                .concatWith(gemma34bService.processMessage(prompt));
    }

    @GetMapping(path = "/message/rag-embd/{prompt}")
    public String getEmbeddedModelResp(@PathVariable String prompt) {
        return gemma34bService.getEmbeddedModelResp(prompt);
    }

}
