package dev.ubaid.learnspringai.multimodel;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images")
public class ImageDetection {
    
    @Value("classpath:/images/image1.jpg")
    Resource simpleImage;
    
    private final ChatClient chatClient;
    
    public ImageDetection(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }
    
    @GetMapping("to/text")
    public String imageToText() {
        return chatClient.prompt()
                .user(c -> {
                    c.text("Can you please describe what you see in the following image?");
                    c.media(MimeTypeUtils.IMAGE_JPEG, simpleImage);
                })
                .call()
                .content();
    }
    
}
