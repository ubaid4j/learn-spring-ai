package dev.ubaid.learnspringai.output;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("vacations")
public class VacationPlan {
    
    private final ChatClient chatClient;
    
    public VacationPlan(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }
    
    @GetMapping("/unstructured")
    public String unstructured() {
        return chatClient.prompt()
                .user("I want to plan a trip to Hawaii. Give me a list of things to do")
                .call()
                .content();
    }
    
    @GetMapping("/structured")
    public Itinerary structured() {
        return chatClient.prompt()
                .user("I want to plan a trip to Hawaii. Give me a list of things to do.")
                .call()
                .entity(Itinerary.class);
    }
}
