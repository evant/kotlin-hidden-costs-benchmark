package com.athaydes.kotlin;

import androidx.test.InstrumentationRegistry;

import java.io.File;
import java.util.concurrent.TimeUnit;

import dk.ilios.spanner.SpannerConfig;
import dk.ilios.spanner.config.RuntimeInstrumentConfig;

public class Config {

    public static SpannerConfig config(Class klass) {
        return config(klass, 1, TimeUnit.SECONDS);
    }

    public static SpannerConfig config(Class klass, int time, TimeUnit timeUnit) {
        File filesDir = InstrumentationRegistry.getTargetContext().getFilesDir();
        File resultsDir = new File(filesDir, "results");

        return new SpannerConfig.Builder()
                .saveResults(resultsDir, klass.getCanonicalName() + ".json") // Save results to disk
                .addInstrument(new RuntimeInstrumentConfig.Builder()
                        .warmupTime(500, TimeUnit.MILLISECONDS)
                        .timingInterval(time, timeUnit)
                        .build()) // Configure how benchmark is run/measured
                .warnIfWrongTestGranularity()
                .uploadResults()
                .build();
    }
}
