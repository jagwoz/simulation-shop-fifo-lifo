package Times;

public class TimeBetweenClients extends Distribution {
    public static int id = 0;

    public static double produce()
    {
        return triangularDistribution( 1, 4, 6);
    }

    public static void incrementIdNew()
    {
        id++;
    }
}