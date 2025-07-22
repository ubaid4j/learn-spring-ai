package dev.ubaid.learnspringai;

import jakarta.annotation.PostConstruct;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.http.HttpClient;
import java.util.logging.LogManager;

@SpringBootApplication
public class LearnSpringAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnSpringAiApplication.class, args);
    }

    @PostConstruct
    public void initLoggingBridge() {
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();
    }
}
