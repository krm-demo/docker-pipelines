package org.krmdemo.restinfo.util;

import java.io.*;
import java.util.*;

import java.net.URL;
import static java.util.Arrays.*;
import static java.util.stream.Collectors.*;

/**
 * Utility class to dump the data into standard output and logs.
 */
public class DumpUtils {

    /**
     * Sort and dump the key-value pairs per each line in <code>"%s --> '%s'"</code> format.
     * @param props key-value pairs as {@link Map}
     */
    public static void dumpProps(Map<String, String> props) {
        props.forEach((propName, propValue) -> systemOut.printf("%s --> '%s'%n", propName, propValue));
    }

    /**
     * Dump the result (standard output and error) of a command-line.
     * @param cmd a command with arguments to execute as a child process
     */
    public static void dumpCmd(String... cmd) {
        String commandLine = stream(cmd).collect(joining(" ", "$(", ")"));
        try {
            Process pr = new ProcessBuilder(cmd).redirectErrorStream(true).start();
            BufferedReader br = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            systemOut.printf("%s = '%s'", commandLine, br.lines().collect(joining(" :\\n: ")));
            br.close();
            int exitCode = pr.waitFor();
            systemOut.println(exitCode == 0 ? "" : " exitCode " + exitCode);
        } catch (Exception ex) {
            systemErr.printf("could not execute the command-line: %s%n", commandLine);
            ex.printStackTrace(systemErr);
            Thread.currentThread().interrupt();
            System.exit(-111);
        }
    }

    /**
     * Dump the content of resource
     * @param resURL URL of resource to dump
     */
    public static void dumpResource(URL resURL) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resURL.openStream()))) {
            br.lines().forEach(systemOut::println);
        } catch (Exception ex) {
            systemErr.printf("could not dump the content of resource: %s%n", resURL);
            ex.printStackTrace(systemErr);
        }
    }

    /**
     * Dump the content of build-resource (an exit with error, if no such resource)
     * @param message a prefix of output message
     * @param resourcePath path in class-loader of this class
     */
    public static void dumpBuildResource(String message, String resourcePath) {
        dumpBuildResource(message, resourcePath, false);
    }

    /**
     * Dump the content of build-resource
     * @param message a prefix of output message
     * @param resourcePath path in class-loader of this class
     * @param ignoreIfAbsent if false - an exit with error; otherwise - silently skip
     */
    public static void dumpBuildResource(String message, String resourcePath, boolean ignoreIfAbsent) {
        URL resourceURL = DumpUtils.class.getResource(resourcePath);
        if (resourceURL == null && ignoreIfAbsent) {
            return;
        }
        systemOut.printf("%s when building the docker image '%s':%n", message, resourcePath);
        if (resourceURL == null) {
            systemErr.printf("could not load the content of resource '%s'%n", resourcePath);
            System.exit(-111);
        }
        systemOut.printf("content of resource (%s):%n", resourceURL);
        systemOut.println("-------------------------------------------------");
        dumpResource(resourceURL);
        systemOut.println("=================================================");
    }

    public static PrintStream systemOut() {
        return systemOut;
    }

    public static PrintStream systemErr() {
        return systemErr;
    }

    @SuppressWarnings("java:S106")
    protected static PrintStream systemOut = System.out;

    @SuppressWarnings("java:S106")
    protected static PrintStream systemErr = System.err;


    /**
     * Prohibit creating the instance for utility class (sonar issue - "java:S1118")
     */
    protected DumpUtils() {
    }
}
