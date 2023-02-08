package com.plx;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import io.quarkus.grpc.GrpcClient;
import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.Test;

@QuarkusTest
 class LanguageGrpcServiceItTest {

    @GrpcClient
    LanguageDetectionGrpc helloGrpc;


    private static final String t = """
                                     Mein Name ist Anna. Ich komme aus Österreich und lebe\s
                                     seit drei Jahren in Deutschland.\s
                                     Ich bin 15 Jahre alt und habe zwei Geschwister""";


    @Test
    public void testHello() {
        LanguageDetectionRequest languageDetectionRequest = LanguageDetectionRequest.newBuilder().setText(t).build();
        LanguageDetectionResponse detectionResponse =
            helloGrpc.detectLanguage(languageDetectionRequest).subscribeAsCompletionStage()
                .join();

        assertEquals("de", detectionResponse.getLanguage());
    }

}
