package Vehicles;

import CustomExceptions.InvalidOperationException;

public abstract class WaterVehicle extends Vehicle {
    private boolean hasSail; //will affect fuel efficiency

    public WaterVehicle(String id, String model, double maxSpeed, double currentMileage, boolean hasSail)
            throws InvalidOperationException { //since vehicle class which is the parent throws invalidoperationexception for validating id,
        //we need to pass the exception along even in its children classes
        //this rule is only there for checked exceptions??
        super(id, model, maxSpeed, currentMileage);
        this.hasSail = hasSail;
    }

    @Override
    public double estimateJourneyTime(double distance) {
        double baseTime = distance / getMaxSpeed();
        return baseTime * 1.15;
    }

    /*
    public abstract void move(double distance) throws InvalidOperationException;

    public abstract double calculateFuelEfficiency();

     */

    public boolean hasSail() {
        return hasSail;
    }



}

