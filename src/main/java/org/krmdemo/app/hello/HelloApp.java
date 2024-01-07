package org.krmdemo.app.hello;

import org.krmdemo.restinfo.RestInfoKind;
import org.krmdemo.restinfo.util.DumpHelper;
import org.krmdemo.restinfo.util.SystemHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.time.ZonedDateTime;
import java.util.*;

import static org.krmdemo.restinfo.util.StreamUtils.toSortedMap;

/**
 * A simple Spring-Boot command-line application, that dumps to standard output
 * the same information as 'hello.sh' script and 'Hello.java'.
 */
@SpringBootApplication
@SuppressWarnings("resource")
@ComponentScan(basePackages = {"org.krmdemo.restinfo","rg.krmdemo.app.hello"})
public class HelloApp implements CommandLineRunner {

    private static final String RESOURCE_BUILD_GRADLE_ENV = "/gradle-env.properties";
    private static final String RESOURCE_BUILD_GRADLE_SYSTEM = "/gradle-system.properties";
    private static final String RESOURCE_BUILD_DOCKER_INFO = "/docker-system-info.txt";
    private static final String RESOURCE_BUILD_GRADLE_INFO = "/gradle-info.properties";
    private static final String RESOURCE_BUILD_MAVEN_SYSTEM_INFO = "/maven-system-info.txt";
    private static final String RESOURCE_BUILD_GIT_INFO = "/git.properties";
    private static final String RESOURCE_BUILD_INFO_SPRING_BOOT = "/META-INF/build-info.properties";

    @Value("${spring.application.name}")
    protected String applicationName;

    /**
     * Represents the set of rest-ingo kinds (build, strat-up, etc ...)
     */
    protected Set<RestInfoKind> restInfoKinds = RestInfoKind.ALL;

    /**
     * An instance of helper-class to work with standard {@link System} utility.
     */
    protected final SystemHelper sh;

    /**
     * An instance of helper-class to dump into standard output and logs.
     */
    protected final DumpHelper dh;

    public HelloApp(SystemHelper systemHelper) {
        this.sh = systemHelper;
        this.dh = new DumpHelper(systemHelper);
    }

    @Override
    @SuppressWarnings("java:S1192")
    public void run(String... args) {
        sh.out().printf("Hello from Spring-Boot application '%s' at %s%n", applicationName, ZonedDateTime.now());

        if (restInfoKinds.contains(RestInfoKind.START_UP_INFO)) {
            sh.out().println("=================================================");
            sh.out().println("runtime-environment on start-up:");
            sh.out().println("-------------------------------------------------");
            dh.dumpCmd("uname", "-s"); // --kernel-name
            dh.dumpCmd("uname", "-r"); // --kernel-release
            dh.dumpCmd("uname", "-o"); // --operating-system
            dh.dumpCmd("uname", "-m"); // --machine
            dh.dumpCmd("uname", "-p"); // --processor
            dh.dumpCmd("uname", "-n"); // --nodename
            dh.dumpCmd("uname", "-i"); // --hardware-platform
            dh.dumpCmd("uname", "-a"); // --all
            dh.dumpProps(new TreeMap<>(System.getenv()));
            sh.out().println("=================================================");
            sh.out().println("java system-properties on start-up:");
            sh.out().println("-------------------------------------------------");
            dh.dumpProps(toSortedMap(System.getProperties()));
            sh.out().println("=================================================");
        }

        if (restInfoKinds.contains(RestInfoKind.BUILD_INFO)) {
            dh.dumpBuildResource("docker system-info when building the docker image", RESOURCE_BUILD_DOCKER_INFO);
            dh.dumpBuildResource("build-environment when building the docker image", RESOURCE_BUILD_GRADLE_ENV, true);
            dh.dumpBuildResource("java system-properties when building the docker image", RESOURCE_BUILD_GRADLE_SYSTEM, true);
            dh.dumpBuildResource("maven-system-info when building the docker image", RESOURCE_BUILD_MAVEN_SYSTEM_INFO, true);
            dh.dumpBuildResource("spring-boot build-info when building the docker image", RESOURCE_BUILD_INFO_SPRING_BOOT);
        }

        if (restInfoKinds.contains(RestInfoKind.SCM_INFO)) {
            dh.dumpBuildResource("gradle-info when building the docker image", RESOURCE_BUILD_GRADLE_INFO, true);
            dh.dumpBuildResource("git-properties", RESOURCE_BUILD_GIT_INFO);
        }
    }

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(HelloApp.class, args)));
    }
}
