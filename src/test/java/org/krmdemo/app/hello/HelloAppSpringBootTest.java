package org.krmdemo.app.hello;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.krmdemo.restinfo.util.SystemHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
class HelloAppSpringBootTest {

    @Autowired
    HelloApp helloApp;

    @Test
    void testContextLoaded() {
        assertNotNull(helloApp);
        assertSame(SystemHelper.DEFAULT, helloApp.sh);
        assertSame(System.out, SystemHelper.DEFAULT.out());
        assertSame(System.err, SystemHelper.DEFAULT.err());
    }
}
