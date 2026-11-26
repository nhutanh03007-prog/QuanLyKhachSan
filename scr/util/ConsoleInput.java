package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public final class ConsoleInput {
    private static final BufferedReader READER =
            new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

    private ConsoleInput() {
    }

    public static BufferedReader getReader() {
        return READER;
    }

    public static String readLine(BufferedReader reader) {
        try {
            String line = reader.readLine();
            if (line == null) {
                System.exit(0);
            }
            return line;
        } catch (IOException e) {
            return "";
        }
    }

    public static String readLine() {
        return readLine(READER);
    }
}
