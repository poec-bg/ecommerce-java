package services.date;

import java.time.Instant;

public class SystemDateService extends DateService {
    @Override
    public Instant currentDateTime() {
        return Instant.now();
    }
}