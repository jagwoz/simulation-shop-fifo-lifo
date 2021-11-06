package Shop.Events;

import Core.SimManager;
import Core.SimEvent;
import Core.SimStats;
import Shop.Checkout;
import Shop.Client;
import Times.Priority;
import Times.TimeBetweenServiceFirst;
import java.util.ArrayList;
import java.util.Arrays;

public class StartOfServiceFirst extends SimEvent {
    public Checkout ch;
    public Checkout ch2;
    public double dt;
    public SimManager sim;

    public StartOfServiceFirst(double runTime, double dt, int simPriority, Checkout ch, Checkout ch2) {
        super(runTime + dt, simPriority);
        this.dt = dt;
        this.ch = ch;
        this.ch2 = ch2;
        this.sim = SimManager.getInstance();
    }

    @Override
    public void stateChange() {
        if (!ch.clientsQueue.isEmpty()) {
            Client client = ch.clientsQueue.remove(0);

            System.out.println(simManager.simTime() + SimStats.ANSI_RED + " - klient nr " + client.id +
                    " zaczal byc obslugiwany na stanowisku pierwszym." + SimStats.ANSI_RESET);
            ch.isBusy = true;
            new EndOfServiceFirst(simManager.simTime(), TimeBetweenServiceFirst.produce(), Priority.produce(), ch, ch2, client);

            ArrayList<Double> stats = SimStats.allStats.get(client.id);
            stats.set(1, simManager.simTime());
            SimStats.allStats.put(client.id, stats);

            SimStats.checkouts.put(simManager.simTime(), new ArrayList<>(Arrays.asList(
                    ch.clientsQueue.size(),
                    ch2.clientsQueue.size()
            )));

            SimStats.lastEventTime = sim.simTime();
        }
    }
}