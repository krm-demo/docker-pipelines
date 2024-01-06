package org.krmdemo.restinfo.util;

import java.io.PrintStream;

/**
 * Utility version of {@link SystemHelper}. It's not used in production code,
 * because of much more complicated way to mock the static methods in tests.
 * <p/>
 * Nevertheless, it's doable and {@link SystemUtilsTest} demonstrates that.
 */
public class SystemUtils {

    public static PrintStream out() {
        return System.out;
    }

    public static PrintStream err() {
        return System.err;
    }

    public static void exit (int exitStatus) {
        System.exit(exitStatus);
    }
}
