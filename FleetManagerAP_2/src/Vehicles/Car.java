package Vehicles;

import CustomExceptions.InsufficientFuelException;
import CustomExceptions.InvalidOperationException;
import CustomExceptions.OverloadException;
import Interfaces.FuelConsumable;
import Interfaces.Maintainable;
import Interfaces.PassengerCarrier;

public class Car extends LandVehicle implements FuelConsumable, PassengerCarrier, Maintainable {
    //properties- initialised already
    private double fuelLevel = 0;
    private int passengerCapacity = 5;
    private int currentPassengers = 0;
    private boolean maintenanceNeeded = false;


    public Car(String id, String model, double maxSpeed, double currentMileage, int numWheels) throws InvalidOperationException {
        super(id, model, maxSpeed, currentMileage, numWheels);

        this.fuelLevel = 0;
        this.passengerCapacity= 5;
        this.currentPassengers = 0;
    }


    //csv constructor
    public Car(String id, String model, double maxSpeed, double currentMileage, int numWheels,
               double fuelLevel, int passengerCapacity, int currentPassengers) throws InvalidOperationException {
        super(id, model, maxSpeed, currentMileage, numWheels);
        this.fuelLevel = fuelLevel;
        this.passengerCapacity = passengerCapacity;
        this.currentPassengers = currentPassengers;




    }


    @Override

    // this was the specification for the abstract move method to be made in vehicle class-
    // abstract void move(double distance): Updates mileage, prints type-specific movement; throws InvalidOperationException if distance < 0.

    public void move(double distance) throws InvalidOperationException {
        if(distance < 0)
            throw new InvalidOperationException("Distance cannot be negative, enter valid distance: ");

        double fuelNeeded = distance / calculateFuelEfficiency(); //since fuel effciency needs to be in km/l form thus- distace/fueleff
        if(fuelLevel < fuelNeeded) {
            System.out.println("Not enough fuel to drive " + distance + " km.");
            return;
        }

        fuelLevel -= fuelNeeded;

        // Update mileage using setter
        setCurrentMileage(getCurrentMileage() + distance);

        System.out.println("Driving on road for " + distance + " km.");
    }

    @Override
    public double calculateFuelEfficiency() {
        return 15.0;
    }

    //fuelconsumable

    @Override
    public void refuel(double amount) throws InvalidOperationException {
        if(amount <= 0)
            throw new InvalidOperationException("Refuel amount must be positive");
        fuelLevel += amount;
    }

    @Override
    public double getFuelLevel() {
        return fuelLevel;
    }

    @Override
    public double consumeFuel(double distance)
            throws InsufficientFuelException {
        double required = distance / calculateFuelEfficiency();
        if(required > fuelLevel) throw new InsufficientFuelException("Not enough fuel");
        fuelLevel -= required;
        return required;
    }

    //SINCE CAR IS A CONCRETE CLASS IT MUST IMPLEMENT THE METHODS OF ALL INTERFACES AS IT SAYS IMPLEMENTS SO AND SO INTERFACES- MUST IMPLEMENT ALL THE MTHODS OF THESE MENTIONED INTERFACES
     //MISSING EVEN ONE= complie error
    // PassengerCarrier
    @Override
    public void boardPassengers(int count) throws OverloadException {
        if(currentPassengers + count > passengerCapacity)
            throw new OverloadException("Too many passengers");
        currentPassengers += count;
    }

    @Override
    public void disembarkPassengers(int count) throws InvalidOperationException {
        if(count > currentPassengers)
            throw new InvalidOperationException("Cannot disembark more than present");
        currentPassengers -= count;
    }

    @Override
    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    @Override
    public int getCurrentPassengers() {
        return currentPassengers;
    }

    // Maintainable
    @Override
    public void scheduleMaintenance() {
        maintenanceNeeded = true;
    }

    @Override
    public boolean needsMaintenance() {
        return getCurrentMileage() > 10000 || maintenanceNeeded;
    }

    @Override
    public void performMaintenance() {
        maintenanceNeeded = false;
        System.out.println("Car maintenance completed.");
    }


    //puts vehicle into a csv format line
    @Override
    public String toCSV() {
        return "Car," + getId() + "," + getModel() + "," + getMaxSpeed() + "," + getCurrentMileage() + "," + getNumWheels() + "," + getFuelLevel() + "," + getPassengerCapacity() + "," + getCurrentPassengers();
    }


}
