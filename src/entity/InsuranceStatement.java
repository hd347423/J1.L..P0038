package entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import utils.Formatter;

public class InsuranceStatement extends Entity{
        private static final long serialVersionUID = 1L;

    private String insuranceId;
    private LocalDate establishedDate;
    private String licensePlate;
    private String customerName;
    private int insurancePeriod;
    private BigDecimal insuranceFees;

    public InsuranceStatement(String insuranceId, LocalDate establishedDate, String licensePlate, String customerName, int insurancePeriod, BigDecimal insuranceFees) {
        this.insuranceId = insuranceId;
        this.establishedDate = establishedDate;
        this.licensePlate = licensePlate;
        this.customerName = customerName;
        this.insurancePeriod = insurancePeriod;
        this.insuranceFees = insuranceFees;
    }

    public String getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(String insuranceId) {
        this.insuranceId = insuranceId;
    }

    public LocalDate getEstablishedDate() {
        return establishedDate;
    }

    public void setEstablishedDate(LocalDate establishedDate) {
        this.establishedDate = establishedDate;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getInsurancePeriod() {
        return insurancePeriod;
    }

    public void setInsurancePeriod(int insurancePeriod) {
        this.insurancePeriod = insurancePeriod;
    }

    public BigDecimal getInsuranceFees() {
        return insuranceFees;
    }

    public void setInsuranceFees(BigDecimal insuranceFees) {
        this.insuranceFees = insuranceFees;
    }

    @Override
    public String toString() {
        String dateStr = establishedDate.format(Formatter.DTF);
        String fees = Formatter.formatMoney(insuranceFees);
        System.out.println(String.format("%-12s | %-16s | %-15s | %-20s | %-16s | %s ",
                "Insurance Id",
                "Established Date",
                "License Plate",
                "Customer Name",
                "Insurance Period",
                "Fees"));
        return String.format("%-12s | %-16s | %-15s | %-20s | %-16d | %s ", 
                insuranceId,
                dateStr,
                licensePlate,
                customerName,
                insurancePeriod,
                fees);
    }
    

}
