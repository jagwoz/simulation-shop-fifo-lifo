package Shop.Events;

import Core.SimManager;
import Core.SimEvent;
import Core.SimStats;
import Shop.Checkout;
import Shop.Client;
import java.util.ArrayList;
import java.util.Arrays;

public class Impatience extends SimEvent {
    public Checkout ch;
    public Checkout ch2;
    public double dt;
    public SimManager sim;
    private Client client;
    private int nr;

    public Impatience(double runTime, double dt, int simPriority, Checkout ch, Checkout ch2, Client c, int i) {
        super(runTime + dt, simPriority);
        this.dt = dt;
        this.ch = ch;
        this.ch2 = ch2;
        this.sim = SimManager.getInstance();
        this.client = c;
        this.nr = i;
    }

    @Override
    public void stateChange() {
        if (ch.clientsQueue.contains(client) && nr == 1) {
            ch.clientsQueue.remove(client);

            System.out.println(simManager.simTime() + SimStats.ANSI_BLACK + " - klient nr " + client.id +
                    " zniecierpliwil sie w kolejce 1 i wyszedl ze sklepu." + SimStats.ANSI_RESET);

            ArrayList<Double> stats = SimStats.allStats.get(client.id);
            stats.set(5, simManager.simTime());
            SimStats.allStats.put(client.id, stats);
            SimStats.lastEventTime = sim.simTime();

            SimStats.checkouts.put(simManager.simTime(), new ArrayList<>(Arrays.asList(
                    ch.clientsQueue.size(),
                    ch2.clientsQueue.size()
            )));
        }

        if (ch2.clientsQueue.contains(client) && nr == 2) {
            ch2.clientsQueue.remove(client);

            System.out.println(simManager.simTime() + SimStats.ANSI_CYAN + " - klient nr " + client.id +
                    " zniecierpliwil sie w kolejce 2 i wyszedl ze sklepu." + SimStats.ANSI_RESET);

            ArrayList<Double> stats = SimStats.allStats.get(client.id);
            stats.set(5, simManager.simTime());
            SimStats.allStats.put(client.id, stats);
            SimStats.lastEventTime = sim.simTime();

            SimStats.checkouts.put(simManager.simTime(), new ArrayList<>(Arrays.asList(
                    ch.clientsQueue.size(),
                    ch2.clientsQueue.size()
            )));
        }
    }
}