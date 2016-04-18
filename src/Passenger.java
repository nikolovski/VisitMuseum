import java.util.Random;

/**
 * Created by Martino Nikolovski on 4/12/16.
 */
public class Passenger extends Thread{
    private Random random;
    private static int numPassengers;
    private MuseumRide ride;
    public static long time = System.currentTimeMillis();

    public static void setNumPassengers(int numPassengers) {
        Passenger.numPassengers = numPassengers;
    }

    public Passenger(int id, MuseumRide ride) {
        setName("Passenger-" + id);
        this.ride = ride;
        random = new Random();
    }

    public void msg(String m) {
        System.out.println("[" + (System.currentTimeMillis() - time) + "] " + getName() + ": " + m);
    }

    @Override
    public void run() {
        try {
            wanderAround();
            ride.lineUp();
            ride.takeARide(this);
            synchronized ((Object)ride.numPassengers){
                ride.numPassengers--;
                if(ride.numPassengers==0) ride.carsParked.notifyAll();
            }

        } catch (InterruptedException e) {
        }
        catch (IllegalMonitorStateException imse){

        }

    }


    private void wanderAround() throws InterruptedException {
        msg(" is wandering around the museum.");
        sleep(random.nextInt(5000));
        msg(" has decided to line up.");
    }

}
