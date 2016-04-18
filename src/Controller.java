import java.util.Random;

/**
 * Created by Martino Nikolovski on 4/12/16.
 */
public class Controller extends Thread {
    private Random random;
    private MuseumRide ride;
    public static long time = System.currentTimeMillis();

    public Controller (int id, MuseumRide ride) {
        setName("Controller-" + id);
        this.ride=ride;
        random = new Random();
    }

    public void msg(String m) {
        System.out.println("[" + (System.currentTimeMillis() - time) + "] " + getName() + ": " + m);
    }


    @Override
    public void run() {
        while(!checkNoMorePassengers()){
                try {
                    checkTickets();
                    ride.givePermission();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                catch (IllegalMonitorStateException imse){
                    continue;
                }
        }
    }

    private void checkTickets() throws InterruptedException {
        msg(" is checking for tickets.");
        sleep(random.nextInt(4000));
    }
    private synchronized boolean checkNoMorePassengers(){
        return ride.numPassengers==0;
    }

}
