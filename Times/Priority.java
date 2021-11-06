package Times;

public class Priority extends Distribution {
    public static int produce()
    {
        return (int) triangularDistribution(1,5,10);
    }
}
