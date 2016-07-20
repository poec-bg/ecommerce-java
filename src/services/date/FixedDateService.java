package services.date;

import java.time.Instant;

public abstract class FixedDateService extends DateService {

    public abstract Instant currentDateTime() ;

}