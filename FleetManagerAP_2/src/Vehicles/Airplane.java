package Vehicles;

import CustomExceptions.InsufficientFuelException;
import CustomExceptions.InvalidOperationException;
import CustomExceptions.OverloadException;
import Interfaces.CargoCarrier;
import Interfaces.FuelConsumable;
import Interfaces.Maintainable;
import Interfaces.PassengerCarrier;

public class Airplane extends AirVehicle implements FuelConsumable, PassengerCarrier, CargoCarrier, Maintainable {
    private double fuelLevel = 0;
    private int passengerCapacity = 200;
    private int currentPassengers = 0;
    private double cargoCapacity = 10000; // kg
    private double currentCargo = 0;
    private boolean maintenanceNeeded = false;

    public Airplane(String id, String model, double maxSpeed, double currentMileage, double maxAltitude) throws InvalidOperationException {
        super(id, model, maxSpeed, currentMileage, maxAltitude);

        this.fuelLevel = 0;
        this.passengerCapacity = 200;
        this.currentPassengers = 0;
        this.cargoCapacity = 10000;
        this.currentCargo = 0;


    }

    //csv constructor

    public Airplane(String id, String model, double maxSpeed, double currentMileage, double maxAltitude,
                    double fuelLevel, int passengerCapacity, int currentPassengers,
                    double cargoCapacity, double currentCargo) throws InvalidOperationException {
        super(id, model, maxSpeed, currentMileage, maxAltitude);
        this.fuelLevel = fuelLevel;
        this.currentPassengers = currentPassengers;
        this.passengerCapacity = passengerCapacity;
        this.cargoCapacity = cargoCapacity;
        this.currentCargo = currentCargo;
    }

    @Override
    public void move(double distance) throws InvalidOperationException {
        if(distance < 0)
            throw new InvalidOperationException("Distance cannot be negative");

        double fuelNeeded = distance / calculateFuelEfficiency();
        if(fuelLevel < fuelNeeded) {
            System.out.println("Not enough fuel to fly " + distance + " km.");
            return;
        }

        fuelLevel -= fuelNeeded;

        // Update mileage using public setter
        setCurrentMileage(getCurrentMileage() + distance);

        System.out.println("Flying at " + getMaxAltitude() + " meters for " + distance + " km.");
    }

    @Override
    public double calculateFuelEfficiency() {
        return 5.0; // km/l
    }

    // FuelConsumable
    @Override
    public void refuel(double amount) throws InvalidOperationException {
        if(amount <= 0)
            throw new InvalidOperationException("Refuel amount must be positive");
        fuelLevel += amount;
    }

    @Override
    public double getFuelLevel() { return fuelLevel; }

    @Override
    public double consumeFuel(double distance) throws InsufficientFuelException {
        double required = distance / calculateFuelEfficiency();
        if(required > fuelLevel)
            throw new InsufficientFuelException("Not enough fuel");
        fuelLevel -= required;
        return required;
    }

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
        System.out.println("Airplane maintenance completed.");
    }

    @Override
    public String toCSV() {
        return "Airplane," + getId() + "," + getModel() + "," + getMaxSpeed() + "," + getCurrentMileage() + "," + getMaxAltitude()+ "," + getFuelLevel() + "," + getPassengerCapacity() + "," + getCurrentPassengers() + "," + getCargoCapacity() + "," + getCurrentCargo();
    }

}
