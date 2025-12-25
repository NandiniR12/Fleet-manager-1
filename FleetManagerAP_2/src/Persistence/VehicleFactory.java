package Persistence;

import CustomExceptions.InvalidOperationException;
import Vehicles.*;

public class VehicleFactory {

    public static Vehicle createVehicle(String[] data) throws InvalidOperationException {
        String type = data[0];

        try {
            return switch (type) {
                case "Car" -> new Car(
                        data[1],                         // id
                        data[2],                         // model
                        parseDoubleSafe(data, 3),        // maxSpeed
                        parseDoubleSafe(data, 4),        // currentMileage
                        parseIntSafe(data, 5),           // numWheels
                        parseDoubleSafe(data, 6),        // fuelLevel
                        parseIntSafe(data, 7),           // passengerCapacity
                        parseIntSafe(data, 8)            // currentPassengers
                );
                case "Truck" -> new Truck(
                        data[1],                         // id
                        data[2],                         // model
                        parseDoubleSafe(data, 3),        // maxSpeed
                        parseDoubleSafe(data, 4),        // currentMileage
                        parseIntSafe(data, 5),           // numWheels
                        parseDoubleSafe(data, 6),        // fuelLevel
                        parseDoubleSafe(data, 7),        // cargoCapacity
                        parseDoubleSafe(data, 8)         // currentCargo
                );
                case "Bus" -> new Bus(
                        data[1],                         // id
                        data[2],                         // model
                        parseDoubleSafe(data, 3),        // maxSpeed
                        parseDoubleSafe(data, 4),        // currentMileage
                        parseIntSafe(data, 5),           // numWheels
                        parseDoubleSafe(data, 6),        // fuelLevel
                        parseIntSafe(data, 7),           // passengerCapacity
                        parseIntSafe(data, 8),           // currentPassengers
                        parseDoubleSafe(data, 9),        // cargoCapacity
                        parseDoubleSafe(data, 10)        // currentCargo
                );
                case "Airplane" -> new Airplane(
                        data[1],                         // id
                        data[2],                         // model
                        parseDoubleSafe(data, 3),        // maxSpeed
                        parseDoubleSafe(data, 4),        // currentMileage
                        parseDoubleSafe(data, 5),        // maxAltitude
                        parseDoubleSafe(data, 6),        // fuelLevel
                        parseIntSafe(data, 7),           // passengerCapacity
                        parseIntSafe(data, 8),           // currentPassengers
                        parseDoubleSafe(data, 9),        // cargoCapacity
                        parseDoubleSafe(data, 10)        // currentCargo
                );
                case "CargoShip" -> new CargoShip(
                        data[1],                         // id
                        data[2],                         // model
                        parseDoubleSafe(data, 3),        // maxSpeed
                        parseDoubleSafe(data, 4),        // currentMileage
                        Boolean.parseBoolean(getSafe(data, 5, "false")), // hasSail
                        parseDoubleSafe(data, 6),        // cargoCapacity
                        parseDoubleSafe(data, 7),        // currentCargo
                        parseDoubleSafe(data, 8)         // fuelLevel
                );
                default -> throw new InvalidOperationException("Unknown vehicle type: " + type);
            };
        } catch (Exception e) {
             throw new InvalidOperationException("Error parsing vehicle data for type " + type + ": " + e.getMessage());
        }
    }

    private static String getSafe(String[] data, int index, String defaultValue) {
        if (index < data.length && data[index] != null && !data[index].isEmpty()) {
            return data[index];
        }
        return defaultValue;
    }

    private static double parseDoubleSafe(String[] data, int index) {
        String val = getSafe(data, index, "0.0");
        try {
            return Double.parseDouble(val);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private static int parseIntSafe(String[] data, int index) {
        String val = getSafe(data, index, "0");
        try {
            // Handle cases like "15.0" being parsed as int 15
            double d = Double.parseDouble(val);
            return (int) d;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
