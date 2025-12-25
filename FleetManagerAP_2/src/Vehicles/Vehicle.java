package Vehicles;

import CustomExceptions.InvalidOperationException;

public abstract class Vehicle implements Comparable<Vehicle> {
    private String id;
    private String model;
    private double maxSpeed;
    private double currentMileage;

    //constructor initialises all fields and checks for valid id- shouldnt be null or empty
    public Vehicle(String id, String model, double maxSpeed, double currentMileage)
            throws InvalidOperationException {
        if (id == null || id.isEmpty()) {
            throw new InvalidOperationException("ID can't be empty. Try Again.");
        }
        this.id = id;
        this.model = model;
        this.maxSpeed = maxSpeed;
        this.currentMileage = currentMileage;
    }

    @Override
    public int compareTo(Vehicle other) {
        return Double.compare(this.calculateFuelEfficiency(), other.calculateFuelEfficiency());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vehicle other = (Vehicle) obj;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public String baseCSV() {
        return id + "," + model + "," + maxSpeed + "," + currentMileage;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Model: " + model +
                ", Max Speed: " + maxSpeed +
                ", Current Mileage: " + currentMileage;
    }


    //A1 concrete method
    public void displayInfo() {
        System.out.println(toString());
    }


    // A1 concrete method- getter
    public double getCurrentMileage() {
        return currentMileage;
    }

    //setter
    public void setCurrentMileage(double mileage) {
        this.currentMileage = mileage;
    }

    //A1 concrete method
    public String getId() {
        return id;
    }
    public double getMaxSpeed() {
        return maxSpeed;
    }
    public String getModel() {
        return model;
    }

    
    public void setModel(String model) {

        this.model = model;
    }

    public void setMaxSpeed(double maxSpeed) {

        this.maxSpeed = maxSpeed;
    }


    //abstract methods from assignment 1- abstract methods dont return anything like concrete methods
    public abstract void move(double distance) throws InvalidOperationException;

    public abstract double calculateFuelEfficiency();

    public abstract double estimateJourneyTime(double distance);

    public abstract String toCSV();

    public void setFuelEfficiency(double v) {

    }
}
