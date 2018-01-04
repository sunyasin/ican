package back.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by Alexey Bezverkhiy (alexey.bezverkhiy@yandex.ru)
 * on 027 27.12.16 @ 18:38
 */
public class DateUtil {

    public static final DateTimeFormatter DATE_FORMATTER_DMY_TIME =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public static final DateTimeFormatter DATE_FORMATTER_FS_DMY_TIME =
            DateTimeFormatter.ofPattern("yyyy-MM-dd\'T\'HH-mm-ss");


    public static Date now() {
        return now(0);
    }

    public static Date now(long dayShift) {
        return Date.from(LocalDateTime.now().plusDays(dayShift)
                .atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date parse(String source, DateTimeFormatter formatter) {
        return createAtDayWithTime(LocalDateTime.parse(source, formatter));
    }

    public static Date createAtDayWithTime(LocalDateTime localDateTime) {
        return Date.from(localDateTime
                .atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String format(Date date, DateTimeFormatter formatter) {
        return createLocalDateTime(date).format(formatter);
    }

    public static LocalDateTime createLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}
