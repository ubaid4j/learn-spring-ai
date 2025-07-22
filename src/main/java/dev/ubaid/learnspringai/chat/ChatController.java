package dev.ubaid.learnspringai.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ChatController {
    
    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }
    
    @GetMapping("/chat")
    public String chat() {
        return chatClient.prompt()
                .user("Tell me an interesting fact about Java")
                .call()
                .content();
    }
    
    @GetMapping("/stream")
    public Flux<String> stream() {
        return chatClient.prompt()
                .user("I am visiting Islamabad soon, can you give me 10 places I must visit?")
                .stream()
                .content();
    }
    
    @GetMapping("/joke")
    public ChatResponse joke() {
        return chatClient.prompt()
                .user("Tell me a dad job about dogs")
                .call()
                .chatResponse();
    }
}
