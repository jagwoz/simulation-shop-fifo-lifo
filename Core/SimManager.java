package Core;

public class SimManager {
    public double currentSimTime, endSimTime;
    public static SimManager simMgr;
    public SimCalendar simCalendar;

    public SimManager() {
        this.currentSimTime = 0;
        simCalendar = new SimCalendar();
    }

    public static SimManager getInstance() {
        if (simMgr == null) {
            simMgr = new SimManager();
        }
        return simMgr;
    }

    public double simTime()
    {
        return currentSimTime;
    }

    public void registerSimEvent(SimEvent simEvent)
    {
        simCalendar.add(simEvent);
    }

    public void start() {
        System.out.println("SIMULATION START");
        run();
        System.out.println("SIMULATION END");
    }

    public void run() {
        for(currentSimTime = 0; currentSimTime <= endSimTime; ) {
            SimEvent event = nextEvent();
            if(event == null || event.runTime > endSimTime) {
                break;
            }
            currentSimTime = event.runTime;
            event.stateChange();
        }
    }

    public void setEndSimTime(double endSimTime)
    {
        this.endSimTime = endSimTime;
    }

    public SimEvent nextEvent()
    {
        return simCalendar.getFirst();
    }
}