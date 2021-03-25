package com.github.suloginscene.lib.time;

import java.time.format.DateTimeFormatter;


public class DateTimeFormatters {

    public static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final String DATE_REGEXP = "\\d\\d\\d\\d-\\d\\d-\\d\\d";
    public static final String DATE_MESSAGE = "숫자 yyyy-MM-dd 형식";

}
