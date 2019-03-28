import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/***
 * Main class for data parsers
 */

public class Main {
    public static void main(String[] args) {

        DataManager dataManager = new DataManager();



        dataManager.loadAllData("data/2016_Presidential_Results.csv", "data/Education.csv", "data/Unemployment.csv", "data/Poverty.csv", "data/Population - PEP_2016_PEPANNRES.csv", dataManager);

        dataManager.exportData();

    }


}
