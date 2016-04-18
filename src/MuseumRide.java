import java.util.Scanner;
import java.util.Vector;

/**
 * Created by Martino Nikolovski on 4/12/16.
 */
public class MuseumRide {
    public static int numPassengers, numCars, numControllers, numSeats;
    public Object permission = new Object();
    public Object seats = new Object();
    public static Vector<Object> waitingPassengers = new Vector<>();
    public Vector<Car> carsParked = new Vector<>();

    public static void loadConfiguration(String[] args){
        if(args.length!=4){
            Scanner input = new Scanner(System.in);
            System.out.print("Enter the number of passengers: ");
            numPassengers =input.nextInt();
            System.out.print("Enter the number of cars: ");
            numCars =input.nextInt();
            System.out.print("Enter the number of controllers: ");
            numControllers =input.nextInt();
            System.out.print("Enter the number of seats: ");
            numSeats=input.nextInt();
        }
        else{
            numPassengers =Integer.parseInt(args[0]);
            numCars =Integer.parseInt(args[1]);
            numControllers =Integer.parseInt(args[2]);
            numSeats=Integer.parseInt(args[3]);
        }
    }

    public void lineUp() throws InterruptedException {
        Object lineSpot = new Object();
        synchronized (lineSpot){
                while(true){
                    waitingPassengers.addElement(lineSpot);
                    lineSpot.wait(); //passengers waiting on different object
                    break;
                }
        }
    }

    public void loadPassengers(Car car) throws InterruptedException {
        // while there are passengers and there are seats left in the car
        while(!waitingPassengers.isEmpty() && car.getNumSeats()>0){
            //signal the first passenger in the line
            synchronized (waitingPassengers.elementAt(0)){
                waitingPassengers.elementAt(0).notify();
                waitingPassengers.removeElementAt(0);
                car.setNumSeats(car.getNumSeats()-1);
            }
        }
        //remove the car from the parked cars
        synchronized (carsParked){ carsParked.removeElement(car);}
    }

    public void takeARide(Passenger passenger) throws InterruptedException {
        // passenger takes a seat
            synchronized (seats){
                passenger.msg(" entered a car.");
                seats.wait();
                passenger.msg(" has left the car and went home.");
            }

    }

    public void askForPermission(Car car) throws InterruptedException {
        //car is waiting for permission from a controller
        synchronized (permission){
            car.msg("is waiting for permission");
            permission.wait();
            car.msg(" has permission to ride around the Park!");
        }
    }

    public synchronized void givePermission(){
        //controller gives permission to the requesting car
        synchronized (permission){
            permission.notify();
        }

    }

    public void unloadPassengers(Car car){
        synchronized (seats){
            for (int i = 0; i < numSeats-car.getNumSeats(); i++) {
                seats.notify();
            }
            car.msg("Unloaded all the passengers");
            car.setNumSeats(numSeats);
        }
    }


    public static void main (String [] args){
        loadConfiguration(args);
        MuseumRide ride = new MuseumRide();

        for (int i = 0; i < numCars; i++) {
            Car car = new Car(i+1,ride);
            car.setNumSeats(numSeats);
            car.start();
        }

        for (int i = 0; i < numPassengers; i++) {
            Passenger passenger = new Passenger(i + 1,ride);
            passenger.setNumPassengers(numPassengers);
            passenger.start();
        }

        for (int i = 0; i < numControllers; i++) {
            Controller controller = new Controller(i+1,ride);
            controller.start();
        }


    }



}
