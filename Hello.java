import java.net.URL;
import java.time.ZonedDateTime;
import java.util.*;

import static java.util.stream.Collectors.*;

/**
 * A simple Java-class that dumps to standard output the same information as 'hello.sh' script.
 */
public class Hello {

    private final static String RESOURCE_BUILD_ENV = "/env__esc_bs.txt";

    private static SortedMap<String, String> loadProps(URL resURL) throws Exception {
        Properties props = new Properties();
        props.load(resURL.openStream());
        return toSortedMap(props);
    }

    private static SortedMap<String, String> toSortedMap(Properties props) {
        return props.entrySet().stream().collect(toMap(
            e -> "" + e.getKey(), e -> "" + e.getValue(), (x, y) -> y, TreeMap::new));
    }

    private static void  dumpProps(Map<String, String> props) {
        props.forEach((propName, propValue) -> System.out.printf("%s --> '%s'\n", propName, propValue));
    }

    /**
     * JVM entry-point.
     * @param args command-line arguments
     */
    public static void main(String[] args) throws Exception {
        System.out.printf("Hello from '%s.java' at %s\n", Hello.class.getName(), ZonedDateTime.now());
        System.out.println("=================================================");

        System.out.println("runtime-environment on start-up:");
        System.out.println("-------------------------------------------------");
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
            System.exit(-111);
        }
        System.out.printf("content of resource (%s):\n", buildEnvURL);
        System.out.println("-------------------------------------------------");
        dumpProps(loadProps(buildEnvURL));

        System.out.println("=================================================");
    }
}
