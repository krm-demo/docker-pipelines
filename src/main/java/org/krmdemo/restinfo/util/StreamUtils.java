package org.krmdemo.restinfo.util;

import java.util.*;

import static java.util.stream.Collectors.*;

/**
 * Utility-class to work with java-streams.
 */
public class StreamUtils {

    public static SortedMap<String, String> toSortedMap(Properties props) {
        return props.entrySet().stream().collect(toMap(
            e -> "" + e.getKey(), e -> "" + e.getValue(), (x, y) -> y, TreeMap::new));
    }

    /**
     * Prohibit creating the instance for utility class (sonar issue - "java:S1118")
     */
    private StreamUtils() {
    }
}
