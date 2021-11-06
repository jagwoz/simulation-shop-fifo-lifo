package Times;

public class TimeBetweenServiceSec extends Distribution {
    public static double produce()
    {
        return triangularDistribution(3,5,7);
    }
}
