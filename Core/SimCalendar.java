package Core;

import java.util.Comparator;
import java.util.PriorityQueue;

public class SimCalendar {
    public PriorityQueue<SimEvent> simEventQueue;

    public SimCalendar() {
        simEventQueue = new PriorityQueue<>(Comparator.comparing((SimEvent a)
                -> a.runTime).thenComparing((a) -> a.simPriority));
    }

    public SimEvent getFirst()
    {
        return simEventQueue.poll();
    }

    public void add(SimEvent simEvent)
    {
        simEventQueue.add(simEvent);
    }
}