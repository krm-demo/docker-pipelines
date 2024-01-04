package org.krmdemo.app.hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.*;

import static org.krmdemo.restinfo.util.DumpUtils.dumpBuildResource;
import static org.krmdemo.restinfo.util.DumpUtils.dumpCmd;
import static org.krmdemo.restinfo.util.DumpUtils.dumpProps;
import static org.krmdemo.restinfo.util.DumpUtils.dumpResource;
import static org.krmdemo.restinfo.util.StreamUtils.toSortedMap;

/**
 * A simple Spring-Boot command-line application, that dumps to standard output
 * the same information as 'hello.sh' script and 'Hello.java'.
 */
@SpringBootApplication
public class HelloApp implements CommandLineRunner {

    private final static String RESOURCE_BUILD_DOCKER_INFO = "/docker-system-info.txt";
    private final static String RESOURCE_BUILD_GIT_INFO = "/git.properties";
    private final static String RESOURCE_BUILD_INFO_SPRING_BOOT = "/META-INF/build-info.properties";

    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public void run(String... args) {
        System.out.printf("Hello from Spring-Boot application '%s' at %s\n", applicationName, ZonedDateTime.now());
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

        dumpBuildResource("docker system-info", RESOURCE_BUILD_DOCKER_INFO);
        dumpBuildResource("git-properties", RESOURCE_BUILD_GIT_INFO);
        dumpBuildResource("spring-boot build-info", RESOURCE_BUILD_INFO_SPRING_BOOT);
    }

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(HelloApp.class, args)));
    }
}
