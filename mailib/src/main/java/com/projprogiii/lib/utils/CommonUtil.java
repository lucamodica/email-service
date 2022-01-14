package com.projprogiii.lib.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class CommonUtil {

    public static boolean validateEmail(String email){
        return Pattern.matches("^(.+)@unito.it", email);
    }

    public static String formatDate(Date date){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormatter.format(date);
    }

    public static String receiversToString(List<String> list) {
        StringBuilder s = new StringBuilder();
        for (String adress: list ) {
            s.append(", ").append(adress);
        }
        return s.toString();
    }

}
