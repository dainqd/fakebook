package com.example.fakebook.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.text.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
public class Utils {
    public static LocalDateTime converLocalDateTime(String dateTime) {
        ZoneId zoneId = ZoneId.of("Europe/London");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        LocalDateTime localDateTime = null;
        try {
            Date parsedDate = dateFormat.parse(dateTime);
            localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(parsedDate.getTime()), zoneId);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return localDateTime;
    }

    public static java.sql.Date convertDate(String datetime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = df.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (java.sql.Date) date;
    }

    public static String convertString(Date datetime) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(datetime);
    }

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static String formatDate(LocalDateTime localDateTime) {
        return formatter.format(localDateTime);
    }

    public static String formatNumber(double value) {
        DecimalFormat myFormatter = new DecimalFormat("###############.########");
        return myFormatter.format(value);
    }

    public static boolean isValidEmail(String email) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@"
                + "[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern)
                .matcher(email)
                .matches();
    }

    public static String generatorVerifyCode(int length) {
        String key = "";
        do {
            key = generatorRandomStringNumber(length);
        } while (key.indexOf("0") == 0);
        return key;
    }

    public static String generatorRandomStringNumber(int length) {
        String CHARS = "1234567890";
        Random random = new Random();
        StringBuilder token = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            token.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return token.toString();
    }
}
