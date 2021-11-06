package Shop;

import Core.SimManager;
import Times.TimeBetweenClients;

public class Client {
    public double startTime;
    public int id;

    public Client() {
        TimeBetweenClients.incrementIdNew();
        id = TimeBetweenClients.id;
        startTime = SimManager.simMgr.simTime();
    }
}
