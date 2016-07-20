package services.date;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

public abstract class DateService {

    private static DateService instance = new SystemDateService();

    protected DateService() {
    }

    public static DateService get() {
        return instance;
    }

    public static void configureWith(DateService dateService) {
        instance = dateService;
    }

    public Instant now() {
        return instance.currentDateTime();
    }

    public abstract Instant currentDateTime();
}
