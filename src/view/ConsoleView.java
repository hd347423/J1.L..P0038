package view;

import entity.Car;
import entity.InsuranceStatement;
import java.util.List;

public class ConsoleView {

    private static final String[] MENU_ITEMS = {
        "Add car information",
        "Find a car",
        "Update a car",
        "Delete a car",
        "Add an insurance statement",
        "List of insurance statements",
        "Report uninsured cars",
        "Save data",
        "Load data",
        "Quit"
    };

    public ConsoleView() {
    }

    public void showInsurance(InsuranceStatement is) {
        System.out.println(String.format("%-4s | %-12s | %-12s | %-15s | %-20s | %-12s | %s",
        "No.", "Insurance Id", "Est Date", "License Plate", "Customer", "Period", "Fees"));
        System.out.println(String.format("%-4d | %s", 1, is.toString()));
    }
    public void showCar(Car car) {
    System.out.println(String.format("%-4s | %-15s | %-12s | %-20s | %-12s | %-12s | %-12s | %-15s",
        "No.", "License Plate", "Reg Date", "Car Owner", "Brand", "Type", "Value", "Place"));
    System.out.println(String.format("%-4d | %s", 1, car.toString()));
}
    
    
    public void showMenu() {
        System.out.println("========== CAR INSURANCE MANAGEMENT ==========");
        for (int i = 0; i < MENU_ITEMS.length; i++) {
            System.out.println((i + 1) + ". " + MENU_ITEMS[i]);
        }
        System.out.println("===============================================");
    }

    public void showMessage(String msg) {
        System.out.println(msg);
    }

    public void showCarReport(List<Car> list, String sortField, String sortType) {
        if (list.isEmpty()) {
            showMessage("No uninsured cars found");
            return;
        }
        System.out.println("Report: UNINSURED CARS\n");

        System.out.println("Sorted by: " + sortField);
        System.out.println("Sort type: " + sortType + "\n");

        System.out.println(String.format("%-4s | %-15s | %-17s | %-20s | %-12s | %-12s | %s",
                "No.", "License plate", "Registration Date", "Vehicle Owner", "Brand", "Vehicle type", "Value"));

        for (int i = 0; i < list.size(); i++) {

            System.out.println(String.format("%-4d | %s ", i + 1, list.get(i).toString()));
        }
    }

    public void showInsuranceReport(List<InsuranceStatement> list, int year, String sortField, String sortType) {
        if (list.isEmpty()) {
            System.out.println("No insurance statements found for year " + year + ".");
            return;
        }
        System.out.println("Report : INSURANCE STATEMENTS");
        System.out.println("From: 01/01/" + year + "  To: 12/31/" + year + "\n");
        System.out.println("Sorted by: " + sortField);
        System.out.println("Sort type: " + sortType + "\n");

        System.out.println(String.format("%-4s | %-12s | %-16s | %-15s | %-20s | %-16s | %s",
                "No.", "Insurance Id", "Established Date", "License plate", "Customer", "Insurance period", "Insurance fees"));

        for (int i = 0; i < list.size(); i++) {

            System.out.println(String.format("%-4d | %s ", i + 1, list.get(i).toString()));
        }
    }
}
