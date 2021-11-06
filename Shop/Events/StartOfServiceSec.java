package Shop.Events;

import Core.SimEvent;
import Core.SimManager;
import Core.SimStats;
import Shop.Checkout;
import Shop.Client;
import Times.Priority;
import Times.TimeBetweenServiceSec;
import java.util.ArrayList;
import java.util.Arrays;

public class StartOfServiceSec extends SimEvent {
    public Checkout ch;
    public Checkout ch2;
    public double dt;
    public SimManager sim;

    public StartOfServiceSec(double runTime, double dt, int simPriority, Checkout ch, Checkout ch2) {
        super(runTime + dt, simPriority);
        this.dt = dt;
        this.ch = ch;
        this.ch2 = ch2;
        this.sim = SimManager.getInstance();
    }

    @Override
    public void stateChange() {
        if (!ch2.clientsQueue.isEmpty()) {
            Client client = ch2.clientsQueue.remove(ch2.clientsQueue.size() - 1);

            System.out.println(simManager.simTime() + " - klient nr " + client.id + " zaczal byc obslugiwany na stanowisku drugim.");
            ch2.isBusy = true;
            new EndOfServiceSec(simManager.simTime(), TimeBetweenServiceSec.produce(), Priority.produce(), ch, ch2, client);

            ArrayList<Double> stats = SimStats.allStats.get(client.id);
            stats.set(3, simManager.simTime());
            SimStats.allStats.put(client.id, stats);

            SimStats.checkouts.put(simManager.simTime(), new ArrayList<>(Arrays.asList(
                    ch.clientsQueue.size(),
                    ch2.clientsQueue.size()
            )));

            SimStats.lastEventTime = sim.simTime();
        }
    }
}