package org.krmdemo.app.hello;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZonedDateTime;
import java.util.*;

import static org.krmdemo.restinfo.util.DumpUtils.dumpCmd;
import static org.krmdemo.restinfo.util.DumpUtils.dumpProps;
import static org.krmdemo.restinfo.util.StreamUtils.toSortedMap;

/**
 * A simple Spring-Boot command-line application, that dumps to standard output
 * the same information as 'hello.sh' script and 'Hello.java'.
 */
@SpringBootApplication
public class HelloApp implements CommandLineRunner {

    @Override
    public void run(String... args) {
        System.out.printf("Hello from '%s' at %s\n", this.getClass().getSimpleName(), ZonedDateTime.now());
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

        // TODO: dump the rest and move the logic to a proper classes and packages
    }

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(HelloApp.class, args)));
    }
}
