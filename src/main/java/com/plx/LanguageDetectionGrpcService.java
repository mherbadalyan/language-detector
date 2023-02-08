package com.plx;

import javax.inject.Inject;

import com.github.pemistahl.lingua.api.Language;
import com.github.pemistahl.lingua.api.LanguageDetector;
import io.quarkus.grpc.GrpcService;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;

@GrpcService
public class LanguageDetectionGrpcService implements LanguageDetectionGrpc {
    @Inject
    LanguageDetector languageDetectionBean;

    @Override
    public Uni<LanguageDetectionResponse> detectLanguage(LanguageDetectionRequest request) {
        String text = request.getText();
        final Language detectedLanguage = languageDetectionBean.detectLanguageOf(text);
        return Uni.createFrom().item(detectedLanguage)
            .map(msg -> LanguageDetectionResponse.newBuilder().setLanguage(msg.getIsoCode639_1().toString()).build());
    }
}