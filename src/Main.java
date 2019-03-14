import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/***
 * Main class for data parsers
 */

public class Main {
    public static void main(String[] args) {

        String electionData = Utils.readFileAsString("data/2016_Presidential_Results.csv");

//        ArrayList<ElectionResult> electionResults = Utils.parse2016ElectionResults(electionData);
//        System.out.println(electionResults);

        String educationData = Utils.readFileAsString("data/Education.csv");

//        ArrayList<Education2016> educationResults = Utils.parse2016Education(educationData);
//        System.out.println(educationResults);

        String unemploymentData = Utils.readFileAsString("data/Unemployment.csv");

//        ArrayList<Employment2016> employmentResults = Utils.parse2016Unemployment(unemploymentData);
//        System.out.println(employmentResults);



        loadAllData("data/2016_Presidential_Results.csv", "data/Education.csv", "data/Unemployment.csv");


    }

    private static void loadAllData(String electionFile, String educationFile, String unemploymentFile) {
        String[] electionRawCleanedLines = Utils.readFileAsCleanedLines(electionFile, 1);
        String[] educationRawCleanedLines = Utils.readFileAsCleanedLines(educationFile, 5);
        //System.out.println(Arrays.toString(educationRawCleanedLines));

        String[] unemploymentRawCleanedLines = Utils.readFileAsCleanedLines(unemploymentFile, 8);

        DataManager dataManager = new DataManager();
        List<State> states = dataManager.getStates();

        Utils.addStateObjs(electionRawCleanedLines, states);
        //System.out.println(states);
        Utils.addCountyObjs(electionRawCleanedLines, states);
//        loadEducationDataIntoObjs(educationRawCleanedLines);


    }



}
