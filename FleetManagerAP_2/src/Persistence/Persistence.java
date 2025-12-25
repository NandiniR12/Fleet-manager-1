package Persistence;

import CustomExceptions.InvalidOperationException;
import Vehicles.*;

import java.io.*;
import java.util.List;

public class Persistence {

    // Save the entire fleet to a CSV file
    public static void saveToFile(List<Vehicle> fleet, String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Vehicle v : fleet) {
                pw.println(v.toCSV());
            }
            System.out.println("Fleet saved successfully to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving fleet: " + e.getMessage());
        }
    }

    // Load fleet data back from a CSV file (RELATIVE PATH ONLY)
    public static void loadFromFile(List<Vehicle> fleet, String filename) {
        File file = new File(filename); // only relative path

        if (!file.exists()) {
            System.out.println("Error: File not found: " + filename);
            System.out.println("Current working directory: " + System.getProperty("user.dir"));
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            fleet.clear();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                try {
                    Vehicle v = VehicleFactory.createVehicle(parts);
                    fleet.add(v);
                } catch (Exception e) {
                    System.out.println("Skipping invalid vehicle data: " + line + ". Reason: " + e.getMessage());
                }
            }
            System.out.println("Fleet loaded successfully from " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Error loading fleet: " + e.getMessage());
        }
    }
}
