package Vehicles;

import CustomExceptions.InsufficientFuelException;
import CustomExceptions.InvalidOperationException;
import CustomExceptions.OverloadException;
import Interfaces.CargoCarrier;
import Interfaces.FuelConsumable;
import Interfaces.Maintainable;


public class Truck extends LandVehicle implements FuelConsumable, CargoCarrier, Maintainable {
    private double fuelLevel = 0;
    private double cargoCapacity = 5000; // kg
    private double currentCargo = 0;
    private boolean maintenanceNeeded = false;

    public Truck(String id, String model, double maxSpeed, double currentMileage, int numWheels) throws InvalidOperationException {
        super(id, model, maxSpeed, currentMileage, numWheels);

        this.fuelLevel = 0;
        this.cargoCapacity = 5000;
        this.currentCargo = 0;
    }


    //csv constructor
    public Truck(String id, String model, double maxSpeed, double currentMileage, int numWheels,
                 double fuelLevel, double cargoCapacity, double currentCargo) throws InvalidOperationException {
        super(id, model, maxSpeed, currentMileage, numWheels);
        this.fuelLevel = fuelLevel;
        this.cargoCapacity = cargoCapacity;
        this.currentCargo = currentCargo;
    }


    @Override
    public void move(double distance) throws InvalidOperationException {
        if(distance < 0)
            throw new InvalidOperationException("Distance cannot be negative");

        double efficiency = calculateFuelEfficiency();
        double fuelNeeded = distance / efficiency;
        if(fuelLevel < fuelNeeded) {
            System.out.println("Not enough fuel to haul cargo for " + distance + " km.");
            return;
        }

        fuelLevel -= fuelNeeded;

        // Update mileage using public setter
        setCurrentMileage(getCurrentMileage() + distance);

        System.out.println("Hauling cargo for " + distance + " km.");
    }

    @Override
    public double calculateFuelEfficiency() {
        // 10% reduction if >50% loaded
        if(currentCargo > (cargoCapacity / 2)) //if curr cargo is >50% then fuel efficiency is 8km/l-0.1*8 else return set fuel cap= 8km/l
            return 8.0 * 0.9;
        return 8.0;
    }

    // FuelConsumable
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
    public double consumeFuel(double distance) throws InsufficientFuelException {
        double required = distance / calculateFuelEfficiency();
        if(required > fuelLevel)
            throw new InsufficientFuelException("Not enough fuel");
        fuelLevel -= required;
        return required;
    }

    // CargoCarrier
    @Override
    public void loadCargo(double weight) throws OverloadException {
        if(currentCargo + weight > cargoCapacity)
            throw new OverloadException("Cargo exceeds capacity");
        currentCargo += weight;
    }

    @Override
    public void unloadCargo(double weight) throws InvalidOperationException {
        if(weight > currentCargo)
            throw new InvalidOperationException("Cannot unload more than current cargo");
        currentCargo -= weight;
    }

    @Override
    public double getCargoCapacity() {
        return cargoCapacity;
    }

    @Override
    public double getCurrentCargo() {
        return currentCargo;
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
        System.out.println("Truck maintenance completed.");
    }

    @Override
    public String toCSV() {
        return "Truck," + getId() + "," + getModel() + "," + getMaxSpeed() + "," + getCurrentMileage() + "," + getNumWheels() + "," + getFuelLevel() + "," + getCargoCapacity() + "," + getCurrentCargo();
    }

}
