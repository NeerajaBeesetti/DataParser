import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/***
 * Main class for data parsers
 */

public class Main {
    public static void main(String[] args) {

        DataManager dataManager = new DataManager();

//        String electionData = Utils.readFileAsString("data/2016_Presidential_Results.csv");
//
//        ArrayList<ElectionResult> electionResults = Utils.parse2016ElectionResults(electionData, dataManager);
//        System.out.println(electionResults);
//
//        String educationData = Utils.readFileAsString("data/Education.csv");
//
//        ArrayList<Education2016> educationResults = Utils.parse2016Education(educationData);
//        System.out.println(educationResults);
//
//        String unemploymentData = Utils.readFileAsString("data/Unemployment.csv");
//
//        ArrayList<Employment2016> employmentResults = Utils.parse2016Unemployment(unemploymentData);
//        System.out.println(employmentResults);
//

        dataManager.loadAllData("data/2016_Presidential_Results.csv", "data/Education.csv", "data/Unemployment.csv", "data/Poverty.csv", "data/Population - PEP_2016_PEPANNRES.csv", dataManager);

        //dataManager.getStates().get(0).getCounties().get(0).getEduc2016().resultToString();
        dataManager.exportData();

    }


}
