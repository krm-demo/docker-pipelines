package org.krmdemo.restinfo;

import java.util.*;

import static java.util.Collections.*;
import static java.util.EnumSet.*;

/**
 * This enumeration represents the kind of information about running application origin.
 */
public enum RestInfoKind {

    /**
     * Information about <b>S</b>ource-<b>C</b>ontrol-<b>M</b>anagement system,
     * where application sources were taken from for further build, push and run.
     * At the moment only data about local GIT-repository and remote Git-Hub and Bit-Bucket
     * are provided.
     */
    SCM_INFO,

    /**
     * Information about where, how and when the application sources were built.
     * The build could be performed locally (via IDE or command-line) or remotely,
     * using continues-integration abilities of Git-Hub, Bit-Bucket, Jenkins, Travis, etc.
     */
    BUILD_INFO,

    /**
     * Information about where and when the application was deployed and started.
     */
    START_UP_INFO;

    /**
     * This set represents all kind of information is requested
     */
    public static final Set<RestInfoKind> ALL = unmodifiableSet(allOf(RestInfoKind.class));

    /**
     * This set indicates that none of information is requested
     */
    public static final Set<RestInfoKind> NONE = unmodifiableSet(noneOf(RestInfoKind.class));
}
