package Vehicles;

import CustomExceptions.InsufficientFuelException;
import CustomExceptions.InvalidOperationException;
import CustomExceptions.OverloadException;
import Interfaces.CargoCarrier;
import Interfaces.FuelConsumable;
import Interfaces.Maintainable;

public class CargoShip extends WaterVehicle implements CargoCarrier, Maintainable, FuelConsumable {

    //if hasSail() = false then it implements fuelconsumable interface
    private double cargoCapacity = 50000; // kg
    private double currentCargo = 0;
    private boolean maintenanceNeeded = false;
    private double fuelLevel = 0; // only if fuel-powered

    //csv constructor
    public CargoShip(String id, String model, double maxSpeed, double currentMileage, boolean hasSail, double cargoCapacity, double currentCargo, double fuelLevel)
            throws InvalidOperationException {

        super(id, model, maxSpeed, currentMileage, hasSail);

        this.cargoCapacity = cargoCapacity;
        this.currentCargo = currentCargo;
        this.fuelLevel = fuelLevel;
    }

    public CargoShip(String id, String model, double maxSpeed, double currentMileage, boolean hasSail) throws InvalidOperationException{
        super(id, model, maxSpeed, currentMileage, hasSail);

        this.cargoCapacity = 50000;
        this.currentCargo = 0;
        this.fuelLevel = 0;
    }



    @Override
    public void move(double distance) throws InvalidOperationException {
        if(distance < 0)
            throw new InvalidOperationException("Distance cannot be negative");

        // Fuel calculation
        double fuelNeeded = distance / calculateFuelEfficiency();
        if(!hasSail() && fuelLevel < fuelNeeded) {
            System.out.println("Not enough fuel to sail for " + distance + " km.");
            return;

        }

        if(!hasSail())
            fuelLevel -= fuelNeeded;

        // Update mileage using public setter
        setCurrentMileage(getCurrentMileage() + distance);

        System.out.println("Sailing with cargo for " + distance + " km.");
    }

/*
    @Override
    public double calculateFuelEfficiency() {
        return hasSail() ? 0 : 4.0; // km/l
    }
    ternary opertors- condition ? ifValueTrue : ifValueFalse
 */

    @Override
    public double calculateFuelEfficiency(){
        if (hasSail()==true){ //fueled ship means there is no sail thus, if hasSail= true then fuel efficiency=0 cus no fuel needed
            return 0;
        }
        return 4.0;
    }

    // CargoCarrier
    @Override
    public void loadCargo(double weight) throws OverloadException {
        if(currentCargo + weight > cargoCapacity) throw new OverloadException("Cargo exceeds capacity");
        currentCargo += weight;
    }

    @Override
    public void unloadCargo(double weight) throws InvalidOperationException {
        if(weight > currentCargo) throw new InvalidOperationException("Cannot unload more than current cargo");
        currentCargo -= weight;
    }

    @Override
    public double getCargoCapacity() { return cargoCapacity; }

    @Override
    public double getCurrentCargo() { return currentCargo; }

    // Maintainable
    @Override
    public void scheduleMaintenance() { maintenanceNeeded = true; }

    @Override
    public boolean needsMaintenance() { return getCurrentMileage() > 10000 || maintenanceNeeded; }

    @Override
    public void performMaintenance() {
        maintenanceNeeded = false;
        System.out.println("Cargo ship maintenance completed.");
    }

    // FuelConsumable (only if not sail-powered)
    @Override
    public void refuel(double amount) throws InvalidOperationException {
        if(hasSail()) return; // sails don't use fuel
        if(amount <= 0) throw new InvalidOperationException("Refuel amount must be positive");
        fuelLevel += amount;
    }

    @Override
    public double getFuelLevel() { return fuelLevel; }

    @Override
    public double consumeFuel(double distance) throws InsufficientFuelException {
        if(hasSail()) return 0; // sails don't use fuel
        double required = distance / calculateFuelEfficiency();
        if(required > fuelLevel) throw new InsufficientFuelException("Not enough fuel");
        fuelLevel -= required;
        return required;
    }


    @Override
    public String toCSV() {
        return "CargoShip," + getId() + "," + getModel() + "," + getMaxSpeed() + "," +
                getCurrentMileage() + "," + hasSail() + ","+ getCargoCapacity() + "," +
                getCurrentCargo() + ","  + getFuelLevel();
    }


}
