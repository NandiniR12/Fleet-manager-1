package Vehicles;

import CustomExceptions.InvalidOperationException;

public abstract class LandVehicle extends Vehicle {
    private int numWheels;

    // Constructor- call super initialise numWheels- constructor has both vehicle and landvehicle attributes
    public LandVehicle(String id, String model, double maxSpeed, double currentMileage, int numWheels)
            throws InvalidOperationException {
        super(id, model, maxSpeed, currentMileage);

        if (numWheels <= 0) {
            throw new InvalidOperationException("Number of wheels must be positive.");
        }

        this.numWheels = numWheels;
    }

    @Override
    public double estimateJourneyTime(double distance) {
        double baseTime = distance / getMaxSpeed();
        return baseTime * 1.1; // +10% for traffic
    }

    //since we need to keep move and calfueleff as abstract- no more overriding- abstract methods inherited directly form vehicle class

    public int getNumWheels() {
        return numWheels;
    }

    public String baseLandCSV() {
        return baseCSV() + "," + numWheels;
    }

    @Override
    public String toCSV() {
        return baseLandCSV(); // subclasses can extend this
    }

    @Override
    public String toString() {
        return super.toString() + ", Wheels: " + numWheels;
    }
}
