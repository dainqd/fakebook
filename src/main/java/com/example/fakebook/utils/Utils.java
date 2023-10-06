package com.example.fakebook.utils;

import com.example.fakebook.entities.Accounts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

    public static String generatorRandomToken(int length) {
        String CHARS = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ1234567890";
        Random random = new Random();
        StringBuilder token = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            token.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return token.toString();
    }

    public static String decimalToHex(long decimal) {
        return Long.toHexString(decimal);
    }

    public static String theMonth(int month) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }

    public static String theDay(int day) {
        String strDays = "";
        if (day > 31 || day < 0) {
            strDays = "31th";
        } else if (day == 1) {
            strDays = "1st";
        } else if (day == 2) {
            strDays = "2nd";
        } else if (day == 3) {
            strDays = "3rd";
        } else if (day == 21) {
            strDays = "21st";
        } else if (day == 22) {
            strDays = "22nd";
        } else if (day == 23) {
            strDays = "23rd";
        } else {
            String payDay = Integer.toString(day);
            strDays = payDay + "th";
        }
        return strDays;
    }

    public static String convertToString(String date) {
//        String pattern = "dd/MM/yyyy";
//        DateFormat df = new SimpleDateFormat(pattern);
//        String todayAsString = df.format(date);
//        String[] parts = todayAsString.split("-");
        String[] parts = date.split("/");
        String doneDay = parts[0];
        String doneMonth = parts[1];
        int intDay = Integer.parseInt(doneDay);
        String dayCuoi = Utils.theDay(intDay);
        int intMonth = Integer.parseInt(doneMonth);
        String monthCuoi = Utils.theMonth(intMonth - 1);
        String s = dayCuoi + ", " + monthCuoi + ", " + parts[2];
        return s;
    }

//    public static String getUsername() {
//        Accounts adminName = (Accounts) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println(adminName);
////        Pattern pattern = Pattern.compile("username=([^,\\s]+)");
////        Matcher matcher = pattern.matcher(adminName);
////        String username;
////        if (matcher.find()) {
////            username = matcher.group(1);
////        } else {
////            username = "admin";
////        }
//        return adminName.getUsername();
//    }

    public static String getUsername() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }


    private static final NavigableMap<Long, String> suffixes = new TreeMap<>();

    static {
        suffixes.put(1_000L, "k");
        suffixes.put(1_000_000L, "M");
        suffixes.put(1_000_000_000L, "G");
        suffixes.put(1_000_000_000_000L, "T");
        suffixes.put(1_000_000_000_000_000L, "P");
        suffixes.put(1_000_000_000_000_000_000L, "E");
    }

    public static String formatIntToViews(long value) {
        if (value < 1000) return Long.toString(value);
        Map.Entry<Long, String> e = suffixes.floorEntry(value);
        Long divideBy = e.getKey();
        String suffix = e.getValue();
        long truncated = value / (divideBy / 10);
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }
}
