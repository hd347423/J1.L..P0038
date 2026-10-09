package manager;

import entity.InsuranceStatement;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import utils.Constants;

public class InsuranceManager extends AbstractManager<InsuranceStatement> implements IReportable {

    @Override
    public boolean add(InsuranceStatement is) {
        if (map.containsKey(is.getInsuranceId())) {
            return false;
        }
        map.put(is.getInsuranceId(), is);

        return true;
    }

    public boolean isInsured(String licensePlate) {
        for (InsuranceStatement is : map.values()) {
            if (is.getLicensePlate().equals(licensePlate)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<InsuranceStatement> generateReport(Map<String, Object> params) {
        int year = (int) params.get(Constants.PARAM_YEAR);
        String sortField = (String) params.get(Constants.PARAM_SORT_FIELD);
        String sortType = (String) params.get(Constants.PARAM_SORT_TYPE);
        List<InsuranceStatement> result = new ArrayList<>();
        for (InsuranceStatement insurance : listAll()) {
            if (insurance.getEstablishedDate().getYear() == year) {
                result.add(insurance);
            }

        }
        Comparator<InsuranceStatement> comp = new Comparator<InsuranceStatement>() {
            @Override
            public int compare(InsuranceStatement i1, InsuranceStatement i2) {
                if (sortField.equalsIgnoreCase("Insurance Id")) {
                    return i1.getInsuranceId().compareTo(i2.getInsuranceId());
                } else if (sortField.equalsIgnoreCase("Established Date")) {
                    return i1.getEstablishedDate().compareTo(i2.getEstablishedDate());
                } else if (sortField.equalsIgnoreCase("License plate")) {
                    return i1.getLicensePlate().compareTo(i2.getLicensePlate());
                } else if (sortField.equalsIgnoreCase("Insurance period")) {
                    return Integer.compare(i1.getInsurancePeriod(), i2.getInsurancePeriod());
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
