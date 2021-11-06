package Shop.Events;

import Core.SimEvent;
import Core.SimManager;
import Core.SimStats;
import Shop.Checkout;
import Shop.Client;
import Times.Priority;
import java.util.ArrayList;

public class EndOfServiceSec extends SimEvent
{
    public Checkout ch;
    public Checkout ch2;
    public double dt;
    public SimManager sim;
    private Client client;

    public EndOfServiceSec(double runTime, double dt, int simPriority, Checkout ch, Checkout ch2, Client c) {
        super(runTime + dt, simPriority);
        this.dt = dt;
        this.ch = ch;
        this.ch2 = ch2;
        this.sim = SimManager.getInstance();
        this.client = c;
    }

    @Override
    public void stateChange() {
        System.out.println(simManager.simTime() + SimStats.ANSI_PURPLE + " - klient nr " + client.id +
                " zostal obsluzony w kolejce drugiej i wyszedl ze sklepu." + SimStats.ANSI_RESET);
        ch2.isBusy = false;
        new StartOfServiceSec(simManager.simTime(), 0, Priority.produce(), ch, ch2);

        ArrayList<Double> stats = SimStats.allStats.get(client.id);
        stats.set(4, simManager.simTime());
        SimStats.allStats.put(client.id, stats);

        SimStats.lastEventTime = sim.simTime();
    }
}