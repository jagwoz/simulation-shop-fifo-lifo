package Shop.Events;

import Core.SimManager;
import Core.SimEvent;
import Core.SimStats;
import Shop.Checkout;
import Shop.Client;
import Times.TimeBetweenClients;
import Times.Priority;
import Times.TimeImpatiently;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class NewClient extends SimEvent {
    public Checkout ch;
    public Checkout ch2;
    public SimManager sim;

    public NewClient(double runTime, int simPriority, Checkout ch, Checkout ch2) {
        super(runTime, simPriority);
        this.ch = ch;
        this.ch2 = ch2;
        this.sim = SimManager.getInstance();
    }

    @Override
    public void stateChange() {
        Client client = new Client();
        ch.clientsQueue.add(client);

        System.out.println(simManager.simTime() + SimStats.ANSI_YELLOW +  " - nowy klient o nr " + client.id +
                " wszedl do kolejki pierwszej." + SimStats.ANSI_RESET);
        if (!ch.isBusy) {
            new StartOfServiceFirst(simManager.simTime(), 0, Priority.produce(), ch, ch2);
        } else {
            double time = TimeImpatiently.produce();
            System.out.println(simManager.simTime() + SimStats.ANSI_WHITE + " - klient nr " + client.id +
                    " zaczal sie niecierpliwic w kolejce pierwszej (planowany czas: " +
                    (simManager.simTime() + time) + ")." + SimStats.ANSI_RESET);
            new Impatience(simManager.simTime(), time, Priority.produce(), ch, ch2, client, 1);
        }
        new NewClient( simManager.simTime() + TimeBetweenClients.produce(), Priority.produce(), ch, ch2);

        ArrayList<Double> stats = new ArrayList<>(Collections.nCopies(6, 0.0));
        stats.set(0, simManager.simTime());
        SimStats.allStats.put(client.id, stats);

        SimStats.checkouts.put(simManager.simTime(), new ArrayList<>(Arrays.asList(
                ch.clientsQueue.size(),
                ch2.clientsQueue.size()
        )));

        SimStats.lastEventTime = simManager.simTime();
    }
}