package utils;

public class TimeUtil {

    // time in milliseconds
    public static final long ONE_SECOND = 1000L;
    public static final long ONE_MINUTE = ONE_SECOND * 60;
    public static final long ONE_HOUR = ONE_MINUTE * 60;
    public static final long ONE_DAY = ONE_HOUR * 24;
    public static final long ONE_MONTH = ONE_DAY * 31;

    public static int daysRemaining(long futureMillisecond, long currentMilliseconds){
        long milliDifference = futureMillisecond - currentMilliseconds;

        if(milliDifference < 0){
            return 0;
        } else{
            return (int) (milliDifference / ONE_DAY);
        }
    }

    public static int hoursRemaining(long futureMilliseconds, long currentMilliseconds){
        long milliDifference = futureMilliseconds - currentMilliseconds;

        if(milliDifference < 0){
            return 0;
        } else{
            return (int) (milliDifference / ONE_HOUR);
        }
    }

    public static int minutesRemaining(long futureMillisecond, long currentMilliseconds){
        long milliDifference = futureMillisecond - currentMilliseconds;

        if(milliDifference < 0){
            return 0;
        } else{
            return (int) (milliDifference / ONE_MINUTE);
        }
    }

    public static long millisecondsRemaining(long futureMillisecond, long currentMullisecond){
        long milliDifference = futureMillisecond - currentMullisecond;

        return milliDifference > 0 ? milliDifference : 0;
    }
}
