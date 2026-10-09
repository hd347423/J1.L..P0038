package controller;

import entity.Car;
import entity.InsuranceStatement;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import manager.CarManager;
import manager.InsuranceManager;
import utils.Constants;
import utils.Inputter;
import io.FileReader;
import io.FileWriter;
import java.math.RoundingMode;
import utils.Formatter;

import view.ConsoleView;

public class ProgramController {

    private final CarManager carManager;
    private final InsuranceManager insuranceManager;
    private final Inputter inputter;
    private final ConsoleView consoleView;

    private boolean dataChanged;

    public ProgramController() {
        insuranceManager = new InsuranceManager();
        carManager = new CarManager(insuranceManager);
        inputter = new Inputter();
        consoleView = new ConsoleView();
        dataChanged = false;
    }

    public void run() {
        loadData();
        while (true) {
            consoleView.showMenu();
            int choice = inputter.readChoice("Choose an option: ", 1, 10);
            if (choice == 10) {
                if (quit()) {
                    return;
                }
            } else {
                dispatch(choice);
            }
        }

    }

    private void dispatch(int choice) {
        switch (choice) {
            case 1:
                addCar();
                break;
            case 2:
                findCar();
                break;
            case 3:
                updateCar();
                break;
            case 4:
                deleteCar();
                break;
            case 5:
                addInsurance();
                break;
            case 6:
                listInsurance();
                break;
            case 7:
                reportUninsured();
                break;
            case 8:
                saveData();
                break;
            case 9:
                loadData();
                break;

            default:
                consoleView.showMessage("Invalid choice");

        }
    }

    private void addCar() {
        do {
            String licensePlate = inputter.readLicensePlate("Enter license plate: ");
            if (carManager.findById(licensePlate) != null) {
                consoleView.showMessage("License plate already exists");
                continue;
            }
            String carOwner = inputter.readCarOwner("Enter car owner: ", false);

            String carBrand = inputter.readCarBrand("Enter car brand: ", false);

            BigDecimal value = inputter.readVehicleValue("Enter value: ", false);
            LocalDate date = inputter.readDate("Enter date (MM/dd/yyyy): ", false);
            String registrationPlace = inputter.readRegistrationPlace("Enter registration place: ", false);
            int vehicleType = inputter.readVehicleType("Enter type: ", false);

            Car car = new Car(licensePlate, carOwner, carBrand, value, date, registrationPlace, vehicleType);
            if (carManager.add(car)) {
                dataChanged = true;
                consoleView.showMessage("Car added successfully");
                consoleView.showCar(car);
            } else {
                consoleView.showMessage("Failed to add car");
            }
        } while (inputter.readYesNo("Do you want to add another car (Y/N): "));
    }

    private void findCar() {
        do {
            String licensePlate = inputter.readLicensePlateLoose("Enter license plate: ");

            Car car = carManager.findById(licensePlate);
            if (car == null) {
                consoleView.showMessage("Unregistered vehicle");

            } else {
                consoleView.showCar(car);

            }
        } while (inputter.readYesNo("Do you want to find another car(Y/N): "));
    }

    private void updateCar() {
        String licensePlate = inputter.readLicensePlate("Enter license plate: ");
        Car oldCar = carManager.findById(licensePlate);
        if (oldCar == null) {
            consoleView.showMessage("Unregistered vehicle");
            return;
        }
        String newOwner = inputter.readCarOwner("Enter new car owner (old: " + oldCar.getCarOwner() + "):", true);
        if (newOwner == null) {
            newOwner = oldCar.getCarOwner();
        }
        String newBrand = inputter.readCarBrand("Enter new car brand(old: " + oldCar.getCarBrand() + "):", true);
        if (newBrand == null) {
            newBrand = oldCar.getCarBrand();
        }
        BigDecimal newValue = inputter.readVehicleValue("Enter new car value (old: " + Formatter.formatMoney(oldCar.getValue()) + "):", true);
        if (newValue == null) {
            newValue = oldCar.getValue();
        }
        LocalDate newDate = inputter.readDate("Enter new registration date (old: " + oldCar.getRegistrationDate().format(Formatter.DTF) + ", MM/dd/yyyy): ", true);

        if (newDate == null) {
            newDate = oldCar.getRegistrationDate();
        }
        String newPlace = inputter.readRegistrationPlace("Enter new registration place (old: " + oldCar.getRegistrationPlace() + "): ", true);
        if (newPlace == null) {
            newPlace = oldCar.getRegistrationPlace();
        }
        int newType = inputter.readVehicleType("Enter vehicle type (old: " + oldCar.getVehicleType() + "):", true);
        if (newType == -1) {
            newType = oldCar.getVehicleType();
        }
        boolean changed = !newOwner.equals(oldCar.getCarOwner())
                || !newBrand.equals(oldCar.getCarBrand())
                || newValue.compareTo(oldCar.getValue()) != 0
                || !newDate.equals(oldCar.getRegistrationDate())
                || !newPlace.equals(oldCar.getRegistrationPlace())
                || newType != oldCar.getVehicleType();
     
        Car newCar = new Car(licensePlate, newOwner, newBrand, newValue, newDate, newPlace, newType);
        if (carManager.update(licensePlate, newCar)) {
            if (changed) {
                dataChanged = true;
            }
            consoleView.showMessage("Car updated successfully");
            consoleView.showCar(newCar);

        } else {
            consoleView.showMessage("Failed to update car");
        }

    }

    private void deleteCar() {
        String licensePlate = inputter.readLicensePlateLoose("Enter license plate: ");
        Car car = carManager.findById(licensePlate);
        if (car == null) {
            consoleView.showMessage("Unregistered vehicle");
            return;
        }
        if (insuranceManager.isInsured(licensePlate)) {
            consoleView.showMessage("Cannot delete: car is already insured");
            return;
        }
        if (!inputter.readYesNo("Confirm delete? (Y/N): ")) {
            consoleView.showMessage("Delete cancelled");
            return;
        }
        if (carManager.remove(licensePlate)) {
            dataChanged = true;
            consoleView.showMessage("Car deleted successfully");
        } else {
            consoleView.showMessage("Failed to delete car");
        }
    }

    private void addInsurance() {
        if (carManager.listAll().isEmpty()) {
            consoleView.showMessage("No cars available in system. Please add a car first!");
            return;
        }
        do {
            String insuranceId = inputter.readInsuranceId("Enter insurance id: ");
            InsuranceStatement existing = insuranceManager.findById(insuranceId);
            if (existing != null) {
                consoleView.showMessage("Insurance id already exists");
                continue;
            }
            LocalDate establishedDate = inputter.readDate("Enter established date (MM/dd/yyyy): ", false);
            Car car;
            String licensePlate;
            while (true) {
                licensePlate = inputter.readLicensePlateLoose("Enter license plate: ");
                if (licensePlate.isEmpty()) {
                    consoleView.showMessage("Cancelled");
                    return;
                }
                car = carManager.findById(licensePlate);
                if (car != null) {
                    break;
                }
                consoleView.showMessage("Unregistered vehicle. Try again or press Enter to cancel.");
            }
            String customerName = inputter.readCustomerName("Enter customer name: ", false);
            int insurancePeriod = inputter.readInsurancePeriod("Enter insurance period (12, 24, 36): ", false);
            BigDecimal value = car.getValue();
            BigDecimal fees;
            if (insurancePeriod == 12) {
                fees = value.multiply(new BigDecimal("0.25"));
            } else if (insurancePeriod == 24) {
                fees = value.multiply(new BigDecimal("0.20").multiply(new BigDecimal("2")));
            } else {
                fees = value.multiply(new BigDecimal("0.15").multiply(new BigDecimal("3")));
            }
            fees = fees.setScale(0, RoundingMode.HALF_UP);
            InsuranceStatement ins = new InsuranceStatement(insuranceId, establishedDate, licensePlate, customerName, insurancePeriod, fees);
            if (insuranceManager.add(ins)) {
                dataChanged = true;
                consoleView.showInsurance(ins);
            } else {
                consoleView.showMessage("Fail to add insurance");
            }
        } while (inputter.readYesNo("Do you want to add another insurance (Y/N): "));
    }

    private void listInsurance() {
        int year = inputter.readChoice("Enter year: ", 1900, 2100);
        String[] fields = {"Insurance Id", "Established Date", "License plate", "Insurance period"};
        String sortField = chooseSortField(fields);
        String sortType = chooseSortType();
        Map<String, Object> params = new HashMap<>();
        params.put(Constants.PARAM_YEAR, year);
        params.put(Constants.PARAM_SORT_FIELD, sortField);
        params.put(Constants.PARAM_SORT_TYPE, sortType);
        List<InsuranceStatement> list = insuranceManager.generateReport(params);
        consoleView.showInsuranceReport(list, year, sortField, sortType);

    }

    private void reportUninsured() {
        String[] fields = {"License plate", "Car owner", "Registration Date", "Vehicle type"};
        String sortField = chooseSortField(fields);
        String sortType = chooseSortType();
        Map<String, Object> params = new HashMap<>();
        params.put(Constants.PARAM_SORT_FIELD, sortField);
        params.put(Constants.PARAM_SORT_TYPE, sortType);
        List<Car> list = carManager.generateReport(params);
        consoleView.showCarReport(list, sortField, sortType);

    }

    private void saveData() {
        boolean carSaved = FileWriter.save(carManager.getMap(), Constants.CAR_FILE);
        boolean insuranceSaved = FileWriter.save(insuranceManager.getMap(), Constants.INSURANCE_FILE);
        if (carSaved && insuranceSaved) {
            dataChanged = false;
            int carCount = carManager.listAll().size();
            int insCount = insuranceManager.listAll().size();
            consoleView.showMessage("Data saved successfully (" + carCount + " cars, " + insCount + " insurance statements).");
        } else {
            consoleView.showMessage("Failed to save data");

        }
    }

private void loadData() {
    Object carObj = FileReader.read(Constants.CAR_FILE);
    if (carObj != null) {
        carManager.loadFromObject(carObj);
        consoleView.showMessage("Car data loaded");
    } else {
        carManager.clear();
        consoleView.showMessage("No car data file found");
    }
    
    Object insObj = FileReader.read(Constants.INSURANCE_FILE);
    if (insObj != null) {
        insuranceManager.loadFromObject(insObj);
        consoleView.showMessage("Insurance data loaded");
    } else {
        insuranceManager.clear();
        consoleView.showMessage("No insurance data file found");
    }
    
    dataChanged = false;
}

    private boolean quit() {
        if (!inputter.readYesNo("Are you sure want to quit (Y/N)")) {
            return false;
        }

        if (dataChanged) {
            saveData();
        }
        consoleView.showMessage("Goodbye");
        return true;
    }

    private String chooseSortField(String[] fields) {
        consoleView.showMessage("Choose sort field: ");
        for (int i = 0; i < fields.length; i++) {
            consoleView.showMessage((i + 1) + ". " + fields[i]);
        }
        int choice = inputter.readChoice("Choose: ", 1, fields.length);
        return fields[choice - 1];
    }

    private String chooseSortType() {
        consoleView.showMessage("Choose sort type: ");
        consoleView.showMessage("1. ASC");
        consoleView.showMessage("2. DESC");
        int choice = inputter.readChoice("Choose: ", 1, 2);
        if (choice == 1) {
            return "ASC";
        } else {
            return "DESC";
        }
    }

}
