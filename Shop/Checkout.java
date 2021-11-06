package Shop;

import java.util.LinkedList;

public class Checkout {
    public LinkedList<Client> clientsQueue;
    public boolean isBusy;

    public Checkout() {
        this.isBusy = false;
        this.clientsQueue = new LinkedList();
    }
}