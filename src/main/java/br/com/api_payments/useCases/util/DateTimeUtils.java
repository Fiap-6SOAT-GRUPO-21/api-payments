package br.com.api_payments.useCases.util;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeUtils {

    public static String generateExpirationDatePayment() {
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        ZonedDateTime expirationDate = now.plus(30, ChronoUnit.MINUTES);
        ZonedDateTime expirationDateWithZone = expirationDate.withZoneSameInstant(ZoneOffset.ofHours(-4));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        return expirationDateWithZone.format(formatter);
    }
}
