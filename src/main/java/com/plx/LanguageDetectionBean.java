package com.plx;

import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

import com.github.pemistahl.lingua.api.LanguageDetector;
import com.github.pemistahl.lingua.api.LanguageDetectorBuilder;

@Singleton
public class LanguageDetectionBean {

    @Produces
    @Default
    public LanguageDetector getDetector() {
        return LanguageDetectorBuilder.fromAllLanguages().build();
    }
}
