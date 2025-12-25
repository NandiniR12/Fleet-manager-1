
import Interfaces.FuelConsumable;
import Vehicles.*;
import fleetManager.FleetManager;
import CustomExceptions.InvalidOperationException;
import Persistence.Persistence;

import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FleetManager fleet = new FleetManager();
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Fleet Manager ---");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Remove Vehicle");
            System.out.println("3. Start Journey");
            System.out.println("4. Refuel All");
            System.out.println("5. Perform Maintenance");
            System.out.println("6. View Fleet");
            System.out.println("7. Save Fleet to File");
            System.out.println("8. Load Fleet from File");
            System.out.println("9. Search by Type");
            System.out.println("10. Fastest / Slowest Vehicle");
            System.out.println("11. View Sorted Fleet");
            System.out.println("12. Generate Report");
            System.out.println("13. Vehicles Needing Maintenance");
            System.out.println("14. Exit");

            System.out.print("Choose an option: ");

            String choice = sc.nextLine().trim();
            if (choice.isEmpty())
                continue;

            try {
                switch (choice) {
                    // add vehicle- user creates vehicle thus it will be using the constructor meant
                    // for user object creation
                    case "1" -> { // Add Vehicle
                        System.out.print("Vehicle type (Car, Truck, Bus, Airplane, CargoShip): ");
                        String type = sc.nextLine().trim();
                        System.out.print("ID: ");
                        String id = sc.nextLine().trim();
                        System.out.print("Model: ");
                        String model = sc.nextLine().trim();
                        System.out.print("Max speed: ");
                        double maxSpeed = Double.parseDouble(sc.nextLine());
                        System.out.print("Current mileage: ");
                        double mileage = Double.parseDouble(sc.nextLine());

                        Vehicle v = null;

                        if (type.equalsIgnoreCase("Car")) {

                            System.out.print("number of wheels:  ");
                            int numWheels = Integer.parseInt(sc.nextLine());
                            v = new Car(id, model, maxSpeed, mileage, numWheels);

                        } else if (type.equalsIgnoreCase("Truck")) {
                            System.out.print("number of wheels:  ");
                            int numWheels = Integer.parseInt(sc.nextLine());
                            v = new Truck(id, model, maxSpeed, mileage, numWheels);

                        } else if (type.equalsIgnoreCase("Bus")) {

                            System.out.print("number of wheels:  ");
                            int numWheels = Integer.parseInt(sc.nextLine());
                            v = new Bus(id, model, maxSpeed, mileage, numWheels);

                        } else if (type.equalsIgnoreCase("Airplane")) {
                            System.out.print("Max altitude: ");
                            double maxAlt = Double.parseDouble(sc.nextLine());

                            v = new Airplane(id, model, maxSpeed, mileage, maxAlt);

                        } else if (type.equalsIgnoreCase("CargoShip")) {
                            System.out.print("Has sail (true/false): ");
                            boolean hasSail = Boolean.parseBoolean(sc.nextLine());

                            v = new CargoShip(id, model, maxSpeed, mileage, hasSail);

                        } else {
                            System.out.println("Unknown vehicle type.");
                            continue;
                        }

                        fleet.addVehicle(v);
                        System.out.println(type + " added successfully.");
                    }

                    case "2" -> {
                        System.out.print("Enter ID to remove: ");
                        String removeId = sc.nextLine().trim();
                        fleet.removeVehicle(removeId);
                        System.out.println("Vehicle removed successfully.");
                    }

                    case "3" -> {
                        System.out.print("Enter distance to move all vehicles: ");
                        double distance = Double.parseDouble(sc.nextLine());
                        fleet.startAllJourneys(distance);

                        double totalFuelUsed = fleet.getTotalFuelConsumption(distance);
                        System.out.println("Total fuel consumed for " + distance + " km: " + totalFuelUsed);
                    }

                    case "4" -> {
                        System.out.print("Enter fuel amount to refuel all: ");
                        double fuel = Double.parseDouble(sc.nextLine());
                        for (Vehicle veh : fleet.getFleet()) {
                            if (veh instanceof FuelConsumable fc)
                                fc.refuel(fuel);
                        }
                        System.out.println("All refuel able vehicles refueled.");
                    }

                    case "5" -> {
                        fleet.maintainAll();
                        System.out.println("Maintenance completed where needed.");
                    }

                    case "6" -> fleet.printFleet();

                    case "7" -> {
                        System.out.print("Enter filename to save: ");
                        String saveFile = sc.nextLine().trim();
                        Persistence.saveToFile(fleet.getFleet(), saveFile);

                    }

                    case "8" -> {
                        System.out.print("Enter filename to load: ");
                        String loadFile = sc.nextLine().trim();
                        Persistence.loadFromFile(fleet.getFleet(), loadFile);

                    }

                    case "9" -> {
                        System.out.print("Enter type to search (Car/Truck/Bus/Airplane/CargoShip): ");
                        String searchType = sc.nextLine().trim();
                        List<Vehicle> found = fleet.searchByType(searchType);
                        if (found.isEmpty())
                            System.out.println("No vehicles of type " + searchType + " found.");
                        else {
                            System.out.println("Found " + found.size() + " vehicle(s):");
                            for (Vehicle v : found)
                                v.displayInfo();
                        }
                    }

                    case "10" -> {
                        System.out.println("Fastest vehicle: " + fleet.getFastestVehicle().getId());
                        System.out.println("Slowest vehicle: " + fleet.getSlowestVehicle().getId());
                    }

                    case "11" -> {
                        System.out.println("Sort by:");
                        System.out.println("1. Max Speed");
                        System.out.println("2. Fuel Efficiency");
                        System.out.println("3. Model Name");
                        System.out.print("Choose option: ");
                        String sortChoice = sc.nextLine().trim();

                        switch (sortChoice) {
                            case "1" -> fleet.printFleetBySpeed();
                            case "2" -> fleet.printFleetByEfficiency();
                            case "3" -> fleet.printFleetByModelName();
                            default -> System.out.println("Invalid sort choice.");
                        }
                    }

                    case "12" -> {
                        System.out.println("\n===== Fleet Report =====");
                        System.out.println(fleet.generateReport());
                    }

                    case "13" -> {
                        List<Vehicle> needing = fleet.getVehiclesNeedingMaintenance();
                        if (needing.isEmpty()) {
                            System.out.println("No vehicles need maintenance.");
                        } else {
                            System.out.println("\n=== Vehicles Needing Maintenance ===");
                            for (Vehicle v : needing) {
                                v.displayInfo();
                                System.out.println("-------------------");
                            }
                        }
                    }

                    case "14" -> {
                        exit = true;
                        System.out.println("Exiting...");
                    }

                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (InvalidOperationException e) {
                System.out.println("Operation failed: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Please enter valid input.");
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }

        sc.close();
    }
}
