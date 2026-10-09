package entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import utils.Formatter;

public class Car extends Entity {

    private static final long serialVersionUID = 1L;

    private String licensePlate;
    private String carOwner;
    private String carBrand;
    private BigDecimal value;
    private LocalDate registrationDate;
    private String registrationPlace;
    private int vehicleType;

    public Car(String licensePlate, String carOwner, String carBrand, BigDecimal value, LocalDate registrationDate, String registrationPlace, int vehicleType) {
        this.licensePlate = licensePlate;
        this.carOwner = carOwner;
        this.carBrand = carBrand;
        this.value = value;
        this.registrationDate = registrationDate;
        this.registrationPlace = registrationPlace;
        this.vehicleType = vehicleType;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getCarOwner() {
        return carOwner;
    }

    public void setCarOwner(String carOwner) {
        this.carOwner = carOwner;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getRegistrationPlace() {
        return registrationPlace;
    }

    public void setRegistrationPlace(String registrationPlace) {
        this.registrationPlace = registrationPlace;
    }

    public int getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(int vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Override
    public String toString() {
        String dateStr = registrationDate.format(Formatter.DTF);
        String money = Formatter.formatMoney(value);
        System.out.println(  String.format("%-15s | %-16s | %-20s | %-12s | %-12s | %s",
                "License Plate",
                "RegistrationDate",
                "Owner",
                "Brand",
                "Vehicle Type",
                "Value"));
       return String.format("%-15s | %-16s | %-20s | %-12s | %-12d | %s", 
            licensePlate,
            dateStr,
            carOwner,
            carBrand,
            vehicleType,
            money);
        
    }


}
