package dev.ubaid.learnspringai.multimodel;

import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

@RestController
@RequestMapping
public class ImageGeneration {
    
    public record ImageResp(String prompt ,String url) {};
    private final OpenAiImageModel openAiImageModel;

    public ImageGeneration(OpenAiImageModel openAiImageModel) {
        this.openAiImageModel = openAiImageModel;
    }
    
    @GetMapping("/generate-image")
    public ResponseEntity<ImageResp> generateImage(
            @RequestParam(defaultValue = "A beautiful sunset over mountains") String prompt
    ) throws IOException {
        ImageOptions options = OpenAiImageOptions.builder()
                .width(1024)
                .height(1024)
                .quality("hd")
                .style("vivid")
                .responseFormat("b64_json")
                .build();

        ImagePrompt imagePrompt = new ImagePrompt(prompt, options);
        ImageResponse imageResponse = openAiImageModel.call(imagePrompt);
        
        String b64Json = imageResponse.getResult().getOutput().getB64Json();
        byte[] imageBytes = Base64.getDecoder().decode(b64Json);

        Path path = Files.createTempFile("image", ".jpg");
        Files.write(path, imageBytes);
        
        return ResponseEntity.ok(new ImageResp(prompt, path.toUri().toString()));
    }
}
