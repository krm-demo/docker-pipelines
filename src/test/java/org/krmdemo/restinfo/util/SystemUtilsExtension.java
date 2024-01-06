package org.krmdemo.restinfo.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.slf4j.event.Level;
import org.springframework.boot.test.context.TestConfiguration;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.*;

import static org.mockito.ArgumentMatchers.anyInt;

@Slf4j
@TestConfiguration
public class SystemUtilsExtension implements InvocationInterceptor {

    private final static Level LVL = Level.INFO;
    private final static AtomicInteger instanceCount = new AtomicInteger();

    final int instanceNum;
    final ThreadLocal<MockSystemHelper> systemHelperHolder = ThreadLocal.withInitial(MockSystemHelper::new);

    public SystemUtilsExtension(int... expectedExitStatuses) {
        this.instanceNum = instanceCount.incrementAndGet();
        log.atLevel(LVL).log("{}({}) instance is created with expected exit-statuses: {}",
            getClass().getSimpleName(), instanceNum, expectedExitStatuses);

    }

    @Override
    public void interceptTestMethod(Invocation<Void> invocation,
                                    ReflectiveInvocationContext<Method> invocationContext,
                                    ExtensionContext extensionContext) throws Throwable {
        String target = invocationContext.getTarget()
            .map(SystemUtilsTest.class::cast)
            .map(tst -> "TST(" + tst.instanceNum + ")").orElse("<<null>>");
        Method targetMethod = invocationContext.getExecutable();
        log.atLevel(LVL).log("{}({}).interceptTestMethod of {}.{}",
            getClass().getSimpleName(), instanceNum, target, targetMethod.getName());
        try (var mock = Mockito.mockStatic(SystemUtils.class)) {
            mock.when(SystemUtils::out).thenReturn(systemHelperHolder.get().out());
            mock.when(SystemUtils::err).thenReturn(systemHelperHolder.get().err());
            mock.when(() -> SystemUtils.exit(anyInt())).thenThrow(MockSystemHelper.ExitException.class);
            expectExit(mock);
            invocation.proceed();
        }
    }

    public String outAsString() {
        return systemHelperHolder.get().outAsString();
    }

    public String errAsString() {
        return systemHelperHolder.get().errAsString();
    }

    private void expectExit(MockedStatic<SystemUtils> mock) {
        // TODO: mock the behavior for enumerate expected exit-statuses
        //mock.when(() -> SystemUtils.exit(anyInt())).thenAnswer(inv -> inv.getArgument(0, int.class));
    }
}
