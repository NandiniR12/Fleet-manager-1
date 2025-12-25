package Interfaces;

import CustomExceptions.InvalidOperationException;
import CustomExceptions.OverloadException;

public interface CargoCarrier {
    void loadCargo(double weight) throws OverloadException;

    void unloadCargo(double weight) throws InvalidOperationException;

    double getCargoCapacity();

    double getCurrentCargo();
}
