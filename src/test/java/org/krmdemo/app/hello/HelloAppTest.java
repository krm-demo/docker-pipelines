package org.krmdemo.app.hello;

import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.commons.io.output.WriterOutputStream;
import org.junit.jupiter.api.*;
import org.krmdemo.restinfo.RestInfoKind;
import org.krmdemo.restinfo.util.DumpUtils;

import java.io.*;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit-test for {@link HelloApp}
 */
@Slf4j
class HelloAppTest extends DumpUtils {

    private final HelloApp helloApp = new HelloApp();

    private final StringBuilder sbOut = new StringBuilder();
    private final StringBuilder sbErr = new StringBuilder();

    @BeforeEach
    public void before() {
        systemOut = printStream(sbOut);
        systemErr = printStream(sbErr);
    }

    @AfterEach
    public void after() {
        systemOut = System.out;
        systemErr = System.err;
    }

    @Test
    void testNone() {
        assertThat(sbOut).isEmpty();
        assertThat(sbErr).isEmpty();
        helloApp.restInfoKinds = RestInfoKind.NONE;
        helloApp.applicationName = "testNone()";
        helloApp.run();
        log.debug("without rest-info:");
        log.debug(sbOut.toString());
        assertThat(sbOut).startsWith("Hello");
        assertThat(sbOut).contains("'testNone()'");
        assertThat(sbErr).isEmpty();
    }

    @Test
    void testStartUp() {
        assertThat(sbOut).isEmpty();
        assertThat(sbErr).isEmpty();
        helloApp.restInfoKinds = EnumSet.of(RestInfoKind.START_UP_INFO);
        helloApp.applicationName = "testStartUp()";
        helloApp.run();
        log.debug("with only {}:", helloApp.restInfoKinds);
        log.debug(sbOut.toString());
        assertThat(sbOut).contains("'testStartUp()'");
        assertThat(sbOut).doesNotContain("testNone");
        assertThat(sbErr).isEmpty();
    }

    private static @Nonnull PrintStream printStream(@Nonnull StringBuilder sb) {
        try {
            OutputStream outputStream = WriterOutputStream.builder()
                .setWriter(new StringBuilderWriter(sb))
                .setWriteImmediately(true).get();
            return new PrintStream(outputStream);
        } catch (IOException ioEx) {
            throw new IllegalStateException("cannot create PrintStream from StringBuilder", ioEx);
        }
    }
}
