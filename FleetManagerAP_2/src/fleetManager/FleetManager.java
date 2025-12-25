package fleetManager;

import CustomExceptions.InsufficientFuelException;
import CustomExceptions.InvalidOperationException;
import Interfaces.FuelConsumable;
import Interfaces.Maintainable;
import Vehicles.*;
import Persistence.VehicleFactory;
import java.io.*;
import java.util.*;


public class FleetManager {
    private List<Vehicle> fleet = new ArrayList<>();

    private Set<String> distinctModels = new HashSet<>();


    public void addVehicle(Vehicle v) throws InvalidOperationException {
        for (Vehicle vehicle : fleet) {
            if (vehicle.getId().equals(v.getId())) {
                throw new InvalidOperationException("Vehicle ID already exists: " + v.getId());
            }
        }
        fleet.add(v);
        distinctModels.add(v.getModel());

    }

    public void removeVehicle(String id) throws InvalidOperationException {
        Vehicle toRemove = null;
        for (Vehicle vehicle : fleet) {
            if (vehicle.getId().equals(id)) {
                toRemove = vehicle;
                break;
            }
        }
        if (toRemove == null)
            throw new InvalidOperationException("Vehicle with ID " + id + " not found");
        fleet.remove(toRemove);
        distinctModels.remove(toRemove.getModel());

    }


    public Set<String> getDistinctModels() {
        return distinctModels;
    }


    public void startAllJourneys(double distance) {
        for (Vehicle vehicle : fleet) {
            try {
                vehicle.move(distance);
            } catch (InvalidOperationException e) {
                System.out.println("Error moving vehicle (" + vehicle.getId() + "): " + e.getMessage());
            }
        }
    }

    public double getTotalFuelConsumption(double distance) {
        double total = 0;
        for (Vehicle vehicle : fleet) {
            if (vehicle instanceof FuelConsumable) {
                FuelConsumable f = (FuelConsumable) vehicle;
                try {
                    total += f.consumeFuel(distance);
                } catch (InsufficientFuelException e) {
                    System.out.println("Fuel error for vehicle " + vehicle.getId() + ": " + e.getMessage());
                }
            }
        }
        return total;
    }

    public void maintainAll() {
        for (Vehicle vehicle : fleet) {
            if (vehicle instanceof Maintainable) {
                Maintainable m = (Maintainable) vehicle;
                if (m.needsMaintenance()) {
                    m.performMaintenance();
                }
            }
        }
    }

    public List<Vehicle> searchByType(String typeName) {
        List<Vehicle> result = new ArrayList<>();
        String t = typeName.toLowerCase();

        for (Vehicle v : fleet) {
            if (t.equals("car") && v instanceof Car) result.add(v);
            else if (t.equals("truck") && v instanceof Truck) result.add(v);
            else if (t.equals("bus") && v instanceof Bus) result.add(v);
            else if (t.equals("airplane") && v instanceof Airplane) result.add(v);
            else if (t.equals("cargoship") && v instanceof CargoShip) result.add(v);
        }
        return result;
    }


    public void sortFleetByEfficiency() {
        Collections.sort(fleet);
        System.out.println("Fleet sorted by fuel efficiency successfully!");
    }

    public void printFleet() {
        if (fleet.isEmpty()) {
            System.out.println("No vehicles in the fleet.");
            return;
        }
        for (Vehicle v : fleet) {
            v.displayInfo();
            System.out.println("Fuel Efficiency: " + v.calculateFuelEfficiency() + " km/l");
            System.out.println("----------------------------");
        }
    }

    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("Total vehicles: ").append(fleet.size()).append("\n");

        int maintenanceCount = 0;
        double totalEfficiency = 0;

        int cars = 0, trucks = 0, buses = 0, airplanes = 0, cargoShips = 0;

        for (Vehicle v : fleet) {
            totalEfficiency += v.calculateFuelEfficiency();

            if (v instanceof Maintainable) {
                Maintainable m = (Maintainable) v;
                if (m.needsMaintenance()) {
                    maintenanceCount++;
                }
            }

            if (v instanceof Car) cars++;
            else if (v instanceof Truck) trucks++;
            else if (v instanceof Bus) buses++;
            else if (v instanceof Airplane) airplanes++;
            else if (v instanceof CargoShip) cargoShips++;
        }

        double avgEfficiency = fleet.isEmpty() ? 0 : totalEfficiency / fleet.size();

        sb.append("Average fuel efficiency: ").append(avgEfficiency).append(" km/l\n");
        sb.append("Vehicles needing maintenance: ").append(maintenanceCount).append("\n");
        sb.append("By type: Cars=").append(cars)
                .append(", Trucks=").append(trucks)
                .append(", Buses=").append(buses)
                .append(", Airplanes=").append(airplanes)
                .append(", CargoShips=").append(cargoShips).append("\n");

        return sb.toString();
    }

    public List<Vehicle> getVehiclesNeedingMaintenance() {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : fleet) {
            if (v instanceof Maintainable) {
                Maintainable m = (Maintainable) v;
                if (m.needsMaintenance()) {
                    result.add(v);
                }
            }
        }
        return result;
    }

    public Vehicle getFastestVehicle() {
        return Collections.max(fleet, Comparator.comparingDouble(Vehicle::getMaxSpeed));
    }

    public Vehicle getSlowestVehicle() {
        return Collections.min(fleet, Comparator.comparingDouble(Vehicle::getMaxSpeed));
    }

    public void printFleetBySpeed() {
        List<Vehicle> sorted = new ArrayList<>(fleet);
        sorted.sort(Comparator.comparingDouble(Vehicle::getMaxSpeed).reversed());

        System.out.println("Fleet sorted by max speed:");
        for (Vehicle v : sorted) {
            System.out.println(v.getId() + " | " + v.getModel() + " | " + v.getMaxSpeed());
        }
    }

    public void printFleetByEfficiency() {
        List<Vehicle> sorted = new ArrayList<>(fleet);
        sorted.sort(Comparator.comparingDouble(Vehicle::calculateFuelEfficiency).reversed());

        System.out.println("Fleet sorted by efficiency:");
        for (Vehicle v : sorted) {
            System.out.println(v.getId() + " | " + v.getModel() + " | " + v.calculateFuelEfficiency());
        }
    }

    public void printFleetByModelName() {
        List<Vehicle> sorted = new ArrayList<>(fleet);
        sorted.sort(Comparator.comparing(Vehicle::getModel, String.CASE_INSENSITIVE_ORDER));

        System.out.println("Fleet sorted by model name:");
        for (Vehicle v : sorted) {
            System.out.println(v.getId() + " | " + v.getModel());
        }
    }


    public List<Vehicle> getFleet() {
        return fleet;
    }


}