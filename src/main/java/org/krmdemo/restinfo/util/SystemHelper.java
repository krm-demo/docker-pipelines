package org.krmdemo.restinfo.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.PrintStream;

/**
 * Helper-interface to work with standard {@link System} utility.
 * <p/>
 *
 */
public interface SystemHelper {

    SystemHelper DEFAULT = new SystemHelper() {};

    @Configuration
    class Config {
        @Bean SystemHelper systemHelper() {
            return DEFAULT;
        }
    }

    @SuppressWarnings("java:S106")
    default PrintStream out() {
        return System.out;
    }

    @SuppressWarnings("java:S106")
    default PrintStream err() {
        return System.err;
    }

    default void exit (int exitStatus) {
         System.exit(exitStatus);
    }
}
