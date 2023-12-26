import java.io.*;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.*;

import static java.util.Arrays.*;
import static java.util.stream.Collectors.*;

/**
 * A simple Java-class that dumps to standard output the same information as 'hello.sh' script.
 */
public class Hello {

    private final static String RESOURCE_BUILD_ENV = "/env__esc_bs.txt";
    private final static String RESOURCE_BUILD_DOCKER_INFO = "/env-docker-system-info.txt";

    private static SortedMap<String, String> loadProps(URL resURL) {
        Properties props = new Properties();
        try (InputStream res = resURL.openStream()) {
            props.load(res);
        } catch (Exception ex) {
            System.err.printf("could not load properties from resource: %s\n", resURL);
            ex.printStackTrace(System.err);
        }
        return toSortedMap(props);
    }

    private static SortedMap<String, String> toSortedMap(Properties props) {
        return props.entrySet().stream().collect(toMap(
            e -> "" + e.getKey(), e -> "" + e.getValue(), (x, y) -> y, TreeMap::new));
    }

    private static void dumpProps(Map<String, String> props) {
        props.forEach((propName, propValue) -> System.out.printf("%s --> '%s'\n", propName, propValue));
    }

    private static void dumpCmd(String... cmd) {
        String commandLine = stream(cmd).collect(joining(" ", "$(", ")"));
        try {
            Process pr = new ProcessBuilder(cmd).redirectErrorStream(true).start();
            BufferedReader br = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            System.out.printf("%s = '%s'", commandLine, br.lines().collect(joining(" :\\n: ")));
            br.close();
            int exitCode = pr.waitFor();
            System.out.println(exitCode == 0 ? "" : " exitCode " + exitCode);
        } catch (Exception ex) {
            System.err.printf("could not execute the command-line: %s\n", commandLine);
            ex.printStackTrace(System.err);
            System.exit(-111);
        }
    }

    private static void dumpResource(URL resURL) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resURL.openStream()))) {
            br.lines().forEach(System.out::println);
        } catch (Exception ex) {
            System.err.printf("could not dump the content of resource: %s\n", resURL);
            ex.printStackTrace(System.err);
        }
    }

    /**
     * JVM entry-point.
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        System.out.printf("Hello from '%s.java' at %s\n", Hello.class.getName(), ZonedDateTime.now());
        System.out.println("=================================================");

        System.out.println("runtime-environment on start-up:");
        System.out.println("-------------------------------------------------");
        dumpCmd("uname", "-s"); // --kernel-name
        dumpCmd("uname", "-r"); // --kernel-release
        dumpCmd("uname", "-o"); // --operating-system
        dumpCmd("uname", "-m"); // --machine
        dumpCmd("uname", "-p"); // --processor
        dumpCmd("uname", "-n"); // --nodename
        dumpCmd("uname", "-i"); // --hardware-platform
        dumpCmd("uname", "-a"); // --all
        dumpProps(new TreeMap<>(System.getenv()));
        System.out.println("=================================================");

        System.out.println("java system-properties on start-up:");
        System.out.println("-------------------------------------------------");
        dumpProps(toSortedMap(System.getProperties()));
        System.out.println("=================================================");

        URL buildEnvURL = Hello.class.getResource(RESOURCE_BUILD_ENV);
        System.out.printf("build-environment when building the docker image '%s':\n", RESOURCE_BUILD_ENV);
        if (buildEnvURL == null) {
            System.err.printf("could not load the content of resource '%s'\n", RESOURCE_BUILD_ENV);
            System.exit(-222);
        }
        System.out.printf("content of resource (%s):\n", buildEnvURL);
        System.out.println("-------------------------------------------------");
        dumpProps(loadProps(buildEnvURL));
        System.out.println("=================================================");

        URL dockerInfoURL = Hello.class.getResource(RESOURCE_BUILD_DOCKER_INFO);
        System.out.printf("docker system-info when building the docker image '%s':\n", RESOURCE_BUILD_DOCKER_INFO);
        if (dockerInfoURL == null) {
            System.err.printf("could not load the content of resource '%s'\n", RESOURCE_BUILD_DOCKER_INFO);
            System.exit(-333);
        }
        System.out.printf("content of resource (%s):\n", dockerInfoURL);
        System.out.println("-------------------------------------------------");
        dumpResource(dockerInfoURL);
        System.out.println("=================================================");
    }
}
