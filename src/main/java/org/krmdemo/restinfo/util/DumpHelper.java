package org.krmdemo.restinfo.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

import static java.util.Arrays.*;
import static java.util.stream.Collectors.*;

/**
 * Helper-class to dump the data into standard output and logs.
 */
@SuppressWarnings("resource")
public class DumpHelper {

    /**
     * An instance of helper to work with standard {@link System} utility.
     */
    protected final SystemHelper sh;

    public DumpHelper(SystemHelper systemHelper) {
        this.sh = systemHelper;
    }

    /**
     * Sort and dump the key-value pairs per each line in <code>"%s --> '%s'"</code> format.
     *
     * @param props key-value pairs as {@link Map}
     */
    public void dumpProps(Map<String, String> props) {
        props.forEach((propName, propValue) -> sh.out().printf("%s --> '%s'%n", propName, propValue));
    }

    /**
     * Dump the result (standard output and error) of a command-line.
     *
     * @param cmd a command with arguments to execute as a child process
     */
    public void dumpCmd(String... cmd) {
        String commandLine = stream(cmd).collect(joining(" ", "$(", ")"));
        try {
            Process pr = new ProcessBuilder(cmd).redirectErrorStream(true).start();
            BufferedReader br = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            sh.out().printf("%s = '%s'", commandLine, br.lines().collect(joining(" :\\n: ")));
            br.close();
            int exitCode = pr.waitFor();
            sh.out().println(exitCode == 0 ? "" : " exitCode " + exitCode);
        } catch (Exception ex) {
            sh.err().printf("could not execute the command-line: %s%n", commandLine);
            ex.printStackTrace(sh.err());
            Thread.currentThread().interrupt();
            sh.exit(111);
        }
    }

    /**
     * Dump the content of resource
     *
     * @param resURL URL of resource to dump
     */
    public void dumpResource(URL resURL) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resURL.openStream()))) {
            br.lines().forEach(sh.out()::println);
        } catch (Exception ex) {
            sh.err().printf("could not dump the content of resource: %s%n", resURL);
            ex.printStackTrace(sh.err());
        }
    }

    /**
     * Dump the content of build-resource (an exit with error, if no such resource)
     *
     * @param message      a prefix of output message
     * @param resourcePath path in class-loader of this class
     */
    public void dumpBuildResource(String message, String resourcePath) {
        dumpBuildResource(message, resourcePath, false);
    }

    /**
     * Dump the content of build-resource
     *
     * @param message        a prefix of output message
     * @param resourcePath   path in class-loader of this class
     * @param ignoreIfAbsent if false - an exit with error; otherwise - silently skip
     */
    public void dumpBuildResource(String message, String resourcePath, boolean ignoreIfAbsent) {
        URL resourceURL = DumpHelper.class.getResource(resourcePath);
        if (resourceURL == null && ignoreIfAbsent) {
            return;
        }
        sh.out().printf("%s when building the docker image '%s':%n", message, resourcePath);
        if (resourceURL == null) {
            sh.err().printf("could not load the content of resource '%s'%n", resourcePath);
            sh.exit(222);
        }
        sh.out().printf("content of resource (%s):%n", resourceURL);
        sh.out().println("-------------------------------------------------");
        dumpResource(resourceURL);
        sh.out().println("=================================================");
    }
}
