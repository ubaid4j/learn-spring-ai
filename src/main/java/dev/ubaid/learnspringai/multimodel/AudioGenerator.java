package dev.ubaid.learnspringai.multimodel;

import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.ai.openai.audio.speech.SpeechResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class AudioGenerator {

    public record AudioResp(String prompt, String file) {}
    
    private final OpenAiAudioSpeechModel speechModel;

    public AudioGenerator(OpenAiAudioSpeechModel speechModel) {
        this.speechModel = speechModel;
    }
    
    //TODO audio generation is not supported by Google Gemini API 
    public ResponseEntity<AudioResp> generateSpeech(
            @RequestParam(defaultValue = "Its a great time to be a Java & Spring developer") String text
    ) throws IOException {
        
        OpenAiAudioSpeechOptions options = OpenAiAudioSpeechOptions.builder()
                .model("")
                .voice(OpenAiAudioApi.SpeechRequest.Voice.ALLOY)
                .responseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                .speed(1.0f)
                .build();
        
        SpeechPrompt speechPrompt = new SpeechPrompt(text, options);
        SpeechResponse resp = speechModel.call(speechPrompt);
        byte[] audioBytes = resp.getResult().getOutput();

        Path audioPath = Files.createTempFile("audio", ".mp3");
        Files.write(audioPath, audioBytes);
        
        return ResponseEntity.ok(new AudioResp(text, audioPath.toUri().toString()));
    }
}
