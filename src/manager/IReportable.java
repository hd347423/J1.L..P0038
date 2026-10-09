package manager;

import java.util.List;
import java.util.Map;

public interface IReportable {

    List<?> generateReport(Map<String, Object> params);

}
