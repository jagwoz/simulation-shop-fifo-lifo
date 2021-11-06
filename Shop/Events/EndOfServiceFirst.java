package Shop.Events;

import Core.SimManager;
import Core.SimEvent;
import Core.SimStats;
import Shop.*;
import Times.Priority;
import Times.TimeImpatiently;
import java.util.ArrayList;
import java.util.Arrays;

public class EndOfServiceFirst extends SimEvent {
    public Checkout ch;
    public Checkout ch2;
    public double dt;
    public SimManager sim;
    private Client client;

    public EndOfServiceFirst(double runTime, double dt, int simPriority, Checkout ch, Checkout ch2, Client c) {
        super(runTime + dt, simPriority);
        this.dt = dt;
        this.ch = ch;
        this.ch2 = ch2;
        this.sim = SimManager.getInstance();
        this.client = c;
    }

    @Override
    public void stateChange() {
        System.out.println(simManager.simTime() + SimStats.ANSI_GREEN + " - klient nr " + client.id +
                " zostal obsluzony w kolejce pierwszej i stanął w kolejce drugiej." + SimStats.ANSI_RESET);
        ch.isBusy = false;
        new StartOfServiceFirst(simManager.simTime(), 0, Priority.produce(), ch, ch2);

        ch2.clientsQueue.add(client);
        if (!ch2.isBusy) {
            new StartOfServiceSec(simManager.simTime(), 0, Priority.produce(), ch, ch2);
        } else {
            double time = TimeImpatiently.produce();
            System.out.println(simManager.simTime() + SimStats.ANSI_BLUE + " - klient nr " + client.id +
                    " zaczal sie niecierpliwic w kolejce drugiej (planowany czas: " +
                    (simManager.simTime() + time) + ")." + SimStats.ANSI_RESET);
            new Impatience(simManager.simTime(), time, Priority.produce(), ch, ch2, client, 2);
        }

        ArrayList<Double> stats = SimStats.allStats.get(client.id);
        stats.set(2, simManager.simTime());
        SimStats.allStats.put(client.id, stats);

        SimStats.checkouts.put(simManager.simTime(), new ArrayList<>(Arrays.asList(
                ch.clientsQueue.size(),
                ch2.clientsQueue.size()
        )));

        SimStats.lastEventTime = sim.simTime();
    }
}