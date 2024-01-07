package org.krmdemo.restinfo.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.slf4j.event.Level;

import java.util.concurrent.atomic.*;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SuppressWarnings("resource")
class SystemUtilsTest {

    private final static Level LVL = Level.INFO;

    private final static AtomicInteger instanceCount = new AtomicInteger();
    final int instanceNum;

    @RegisterExtension
    final static SystemUtilsExtension systemUtilsExt = new SystemUtilsExtension();

    public SystemUtilsTest() {
        this.instanceNum = instanceCount.incrementAndGet();
    }

    @Test
    void testOne() {
        log.atLevel(LVL).log("TST({}).testOne()  - systemUtilsExt({})",
            instanceNum, systemUtilsExt.instanceNum);
        SystemUtils.out().print("[1]");
        SystemUtils.out().print("[2]");
        SystemUtils.out().print("[3]");
        SystemUtils.out().println();
        SystemUtils.out().println("---------");
        assertThat(systemUtilsExt.outAsString()).isEqualTo("""
            [1][2][3]
            ---------
            """);
        assertThat(systemUtilsExt.errAsString()).isEmpty();
    }
}
