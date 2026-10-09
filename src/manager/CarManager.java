package manager;

import entity.Car;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.util.List;
import java.util.Map;
import utils.Constants;

public class CarManager extends AbstractManager<Car> implements IReportable {

    private InsuranceManager insuranceManager;

    public CarManager(InsuranceManager insuranceManager) {
        this.insuranceManager = insuranceManager;
    }



    @Override
    public boolean add(Car car) {
        if (map.containsKey(car.getLicensePlate())) {
            return false;
        } else {
            map.put(car.getLicensePlate(), car);
        }
        return true;
    }

    public boolean update(String id, Car car) {
        if (map.containsKey(id) == false) {
            return false;
        }
        map.put(id, car);

        return true;
    }

    public boolean remove(String id) {
        if (!map.containsKey(id) ) {
            return false;
        }
        map.remove(id);

        return true;
    }

    @Override
    public List<Car> generateReport(Map<String, Object> params) {
        String sortField = (String) params.get(Constants.PARAM_SORT_FIELD);
        String sortType = (String) params.get(Constants.PARAM_SORT_TYPE);
        List<Car> result = new ArrayList<>();
        for (Car car : listAll()) {
            if (!insuranceManager.isInsured(car.getLicensePlate()) ) {
                result.add(car);
            }

        }
        Comparator<Car> comp = new Comparator<Car>() {
            @Override
            public int compare(Car c1, Car c2) {
                if (sortField.equalsIgnoreCase("License plate")) {
                    return c1.getLicensePlate().compareTo(c2.getLicensePlate());
                } else if (sortField.equalsIgnoreCase("Car owner")) {
                    return c1.getCarOwner().compareTo(c2.getCarOwner());
                } else if (sortField.equalsIgnoreCase("Registration Date")) {
                    return c1.getRegistrationDate().compareTo(c2.getRegistrationDate());
                } else if (sortField.equalsIgnoreCase("Vehicle type")) {
                    return Integer.compare(c1.getVehicleType(), c2.getVehicleType());
                } else {
                    return 0;
                }

            }
        };
        Collections.sort(result, comp);
        if (sortType.equalsIgnoreCase("DESC")) {
            Collections.reverse(result);
        }

        return result;
    }

}
