package Interfaces;

import CustomExceptions.InsufficientFuelException;
import CustomExceptions.InvalidOperationException;

public interface FuelConsumable {

    //Adds fuel; throws InvalidOperationException- if amount ≤ 0.
    void refuel (double amount) throws InvalidOperationException;

    //Returns current fuel level
    double getFuelLevel();

    //Reduces fuel based on efficiency; re-turns consumed amount; throws InsufficientFuelException if not enough fuel
    double consumeFuel(double distance) throws InsufficientFuelException;
}
