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

        loadAllData("data/2016_Presidential_Results.csv", "data/Education.csv", "data/Unemployment.csv", dataManager);


    }

    private static void loadAllData(String electionFile, String educationFile, String unemploymentFile, DataManager dataManager) {
        String[] electionRawCleanedLines = Utils.readFileAsCleanedLines(electionFile, 1);
//        String[] educationRawCleanedLines = Utils.readFileAsCleanedLines(educationFile, 5);
        String[] unemploymentRawCleanedLines = Utils.readFileAsCleanedLines(unemploymentFile, 8);

        List<State> states = dataManager.getStates();
        Utils.addStateObjs(electionRawCleanedLines, states);
        Utils.addCountyObjs(electionRawCleanedLines, states);

//        ArrayList<ElectionResult> electionResults = Utils.parse2016ElectionResults(electionRawCleanedLines, dataManager);
//        ArrayList<Education2016> educationResults = Utils.parse2016Education(educationRawCleanedLines, dataManager);
        ArrayList<Employment2016> employmentResults = Utils.parse2016Unemployment(unemploymentRawCleanedLines, dataManager);

        System.out.println(employmentResults);


        //adding education and employment data done inside parsing methods

    }


}
