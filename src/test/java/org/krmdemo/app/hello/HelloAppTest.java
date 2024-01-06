package org.krmdemo.app.hello;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.krmdemo.restinfo.RestInfoKind;
import org.krmdemo.restinfo.util.MockSystemHelper;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit-test for {@link HelloApp}
 */
@Slf4j
class HelloAppTest {

    private final MockSystemHelper mockSh = new MockSystemHelper();
    private final HelloApp helloApp = new HelloApp(mockSh);

    @Test
    void testNone() {
        assertThat(mockSh.outAsString()).isEmpty();
        assertThat(mockSh.errAsString()).isEmpty();
        helloApp.restInfoKinds = RestInfoKind.NONE;
        helloApp.applicationName = "testNone()";
        helloApp.run();
        log.debug("without rest-info:");
        log.debug(mockSh.toString());
        assertThat(mockSh.outAsString())
            .startsWith("Hello")
            .contains("'testNone()'");
        assertThat(mockSh.errAsString()).isEmpty();
    }

    @Test
    void testStartUp() {
        assertThat(mockSh.outAsString()).isEmpty();
        assertThat(mockSh.errAsString()).isEmpty();
        helloApp.restInfoKinds = EnumSet.of(RestInfoKind.START_UP_INFO);
        helloApp.applicationName = "testStartUp()";
        helloApp.run();
        log.debug("with only {}:", helloApp.restInfoKinds);
        log.debug(mockSh.outAsString());
        assertThat(mockSh.outAsString())
            .contains("'testStartUp()'")
            .doesNotContain("testNone");
        assertThat(mockSh.errAsString()).isEmpty();
    }
}
