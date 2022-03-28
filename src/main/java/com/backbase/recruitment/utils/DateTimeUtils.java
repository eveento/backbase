package com.backbase.recruitment.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateTimeUtils {

    private final DateTimeFormatter fullIsoFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public String formatToFullIsoDate(LocalDateTime dateTime){
        return fullIsoFormat.format(dateTime);
    }
}
