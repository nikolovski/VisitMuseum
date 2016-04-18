# VisitMuseum
Concurrent programming project using Monitors

There are m passengers, n cars, and c controllers; 
Passengers wander around the museum for a while, then line up to take a ride in a car (have each passenger block on a different object).
Each car has s seats.When a car is available (empty), it loads the passengers (loading thepassengers must be done in a FCFS order, similar to the way done in rwcv.java); 
Once the car is full (In your implementation the passengers that get on the same car, must now block on the same object), it asks (signals and then waits for) one of the controllers’ permission for departure.
Note: If a passenger doesn’t get the chance to get in the car, it will wait for the next car available. Keep in mind that the last car might not be full. The controller checks the tickets (sleep of random time) and next allows the departure of the specific car.
Once the permission is given, the car rides around the park for a random amount of time. After the ride ends, the car unloads the passengers and becomes available again. After all passengers have the chance to take a ride, the threads can terminate.
Synchronize the m passenger threads, the n car threads and the c controller threads based on the given story
