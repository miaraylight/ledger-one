package com.miaraylight;

public class AnsiColors {
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";

    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m"; // Previously PURPLE
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static final String BRIGHT_RED = "\u001B[91m";
    public static final String BRIGHT_GREEN = "\u001B[92m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    public static final String BRIGHT_BLUE = "\u001B[94m";

    public static final String[] RAINBOW_COLORS = {
            RED,
            YELLOW,
            GREEN,
            CYAN,
            BLUE,
            MAGENTA,
            BRIGHT_RED,
            BRIGHT_GREEN,
            BRIGHT_YELLOW,
            BRIGHT_BLUE
    };
}
