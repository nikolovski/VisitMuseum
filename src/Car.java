import java.util.Random;

/**
 * Created by Martino Nikolovski on 4/12/16.
 */
public class Car extends Thread {
    private Random random;
    private int numSeats;
    private MuseumRide ride;
    public static long time = System.currentTimeMillis();

    public Car(int id, MuseumRide ride) {
        setName("Car-" + id);
        this.ride=ride;
        random=new Random();
    }

    public int getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    public void msg(String m) {
        System.out.println("[" + (System.currentTimeMillis() - time) + "] " + getName() + ": " + m);
    }



    @Override
    public void run() {
        while(ride.numPassengers>0) {
            try {
                park();
                ride.loadPassengers(this);
                ride.askForPermission(this);
                rideAround();
                ride.unloadPassengers(this);
                sleep(random.nextInt(2000));
                if(checkNoMorePassengers()) ride.carsParked.notifyAll();

            } catch (InterruptedException e) {
            } catch (IllegalMonitorStateException ime) {
                continue;
            }
        }
    }

    public void park() throws InterruptedException {
        sleep(random.nextInt(5000));
        msg(" is ready to load passengers.");
        ride.carsParked.add(this);
    }

    private void rideAround() throws InterruptedException {
        msg(" is on a tour around the Park.");
        sleep(random.nextInt(10000));
        msg(" has returned from the tour and unloading the passengers.");
    }

    private synchronized boolean checkNoMorePassengers(){
        return ride.numPassengers==0;
    }

}
