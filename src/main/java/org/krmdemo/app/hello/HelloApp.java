package org.krmdemo.app.hello;

import org.krmdemo.restinfo.RestInfoKind;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZonedDateTime;
import java.util.*;

import static org.krmdemo.restinfo.util.DumpUtils.dumpBuildResource;
import static org.krmdemo.restinfo.util.DumpUtils.dumpCmd;
import static org.krmdemo.restinfo.util.DumpUtils.dumpProps;
import static org.krmdemo.restinfo.util.DumpUtils.systemOut;
import static org.krmdemo.restinfo.util.StreamUtils.toSortedMap;

/**
 * A simple Spring-Boot command-line application, that dumps to standard output
 * the same information as 'hello.sh' script and 'Hello.java'.
 */
@SpringBootApplication
@SuppressWarnings("java:S1192")
public class HelloApp implements CommandLineRunner {

    private static final String RESOURCE_BUILD_DOCKER_INFO = "/docker-system-info.txt";
    private static final String RESOURCE_BUILD_GIT_INFO = "/git.properties";
    private static final String RESOURCE_BUILD_INFO_SPRING_BOOT = "/META-INF/build-info.properties";

    @Value("${spring.application.name}")
    String applicationName;

    /**
     * Represents the set of rest-ingo kinds (build, strat-up, etc ...)
     */
    Set<RestInfoKind> restInfoKinds = RestInfoKind.ALL;

    @Override
    public void run(String... args) {
        systemOut().printf("Hello from Spring-Boot application '%s' at %s%n", applicationName, ZonedDateTime.now());

        if (restInfoKinds.contains(RestInfoKind.START_UP_INFO)) {
            systemOut().println("=================================================");
            systemOut().println("runtime-environment on start-up:");
            systemOut().println("-------------------------------------------------");
            dumpCmd("uname", "-s"); // --kernel-name
            dumpCmd("uname", "-r"); // --kernel-release
            dumpCmd("uname", "-o"); // --operating-system
            dumpCmd("uname", "-m"); // --machine
            dumpCmd("uname", "-p"); // --processor
            dumpCmd("uname", "-n"); // --nodename
            dumpCmd("uname", "-i"); // --hardware-platform
            dumpCmd("uname", "-a"); // --all
            dumpProps(new TreeMap<>(System.getenv()));
            systemOut().println("=================================================");
            systemOut().println("java system-properties on start-up:");
            systemOut().println("-------------------------------------------------");
            dumpProps(toSortedMap(System.getProperties()));
            systemOut().println("=================================================");
        }

        if (restInfoKinds.contains(RestInfoKind.BUILD_INFO)) {
            dumpBuildResource("docker system-info", RESOURCE_BUILD_DOCKER_INFO);
            dumpBuildResource("spring-boot build-info", RESOURCE_BUILD_INFO_SPRING_BOOT);
        }

        if (restInfoKinds.contains(RestInfoKind.SCM_INFO)) {
            dumpBuildResource("git-properties", RESOURCE_BUILD_GIT_INFO);
        }
    }

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(HelloApp.class, args)));
    }
}
