package com.tahanebti.ffkmda.specification;

import java.util.regex.Pattern;

public final class Constants {

    public static final String COLON = ":";
    public static final String COMA = ",";
    public static final String SEMICOMA = ";";
    public static final String AND = "&";
    public static final String OR = "|";
    public static final String PLUS = "+";
    public static final String ASTERISK = "*";
    public static final String DASH = "-";
    public static final String LESS_THAN_SIGN = "<";
    public static final String GREATER_THAN_SIGN = ">";
    public static final String CLOSING_EXCLAMATION = "!";
    public static final String TILDE = "~";
    public static final String PERCENT_SIGN = "%";
    public static final String DOT = ".";
    public static final String EQUAL = "=";
    
    public static final String IN_SEPARATOR = ",";
    public static final String KEY_SEPARATOR = ".";
    public static final String AND_SEPARATOR = "[and]";
    public static final String AND_SEPARATOR_GROUP = "[[and]]";
    public static final String OR_SEPARATOR = "[or]";
    public static final String OR_SEPARATOR_GROUP = "[[or]]";
    public static final String IN_SEPARATOR_REGEX = Pattern.quote(IN_SEPARATOR);
    public static final String KEY_SEPARATOR_REGEX = Pattern.quote(KEY_SEPARATOR);
    public static final String AND_REGEX = Pattern.quote(AND_SEPARATOR);
    public static final String AND_GROUP_REGEX = Pattern.quote(AND_SEPARATOR_GROUP);
    public static final String OR_REGEX = Pattern.quote(OR_SEPARATOR);
    public static final String OR_GROUP_REGEX = Pattern.quote(OR_SEPARATOR_GROUP);
    public static final String[] PAGEABLE_PARAMS = {"sort", "size", "page"};

    private Constants() {

    }

}