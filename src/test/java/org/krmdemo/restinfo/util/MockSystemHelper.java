package org.krmdemo.restinfo.util;

import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.commons.io.output.WriterOutputStream;

import java.io.*;

import static java.lang.String.format;

public class MockSystemHelper implements SystemHelper {

    public static class ExitException extends RuntimeException {
        @SuppressWarnings("unused")
        public ExitException() {
            super("exit(...)");
        }
        public ExitException(int exitStatus) {
            super(format("exit(%d)", exitStatus));
        }
    }

    final StringBuilder sbOut = new StringBuilder();
    final StringBuilder sbErr = new StringBuilder();

    final PrintStream out = printStream(sbOut);
    final PrintStream err = printStream(sbErr);

    @Override
    public PrintStream out() {
        return this.out;
    }

    @Override
    public PrintStream err() {
        return this.err;
    }

    @Override
    public void exit(int exitStatus) {
        throw new ExitException(exitStatus);
    }

    public String outAsString() {
        return sbOut.toString();
    }

    public String errAsString() {
        return sbErr.toString();
    }

    private static PrintStream printStream(StringBuilder sb) {
        try {
            OutputStream outputStream = WriterOutputStream.builder()
                .setWriter(new StringBuilderWriter(sb))
                .setWriteImmediately(true).get();
            return new PrintStream(outputStream);
        } catch (IOException ioEx) {
            throw new IllegalStateException("cannot create PrintStream from StringBuilder", ioEx);
        }
    }
}
