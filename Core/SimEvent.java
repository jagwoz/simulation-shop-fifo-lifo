package Core;

public abstract class SimEvent {
    public double runTime;
    public int simPriority;
    public SimManager simManager;

    public SimEvent(double runTime, int simPriority) {
        this.runTime = runTime;
        this.simPriority = simPriority;
        this.simManager = SimManager.getInstance();
        this.simManager.registerSimEvent(this);
    }

    public abstract void stateChange();
}
