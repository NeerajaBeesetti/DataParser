import java.util.ArrayList;

/***
 * Main class for data parsers
 */

public class Main {

    public static void main(String[] args) {


//        String electionData = Utils.readFileAsString("data/2016_Presidential_Results.csv");
//
//        ArrayList<ElectionResult> electionResults = Utils.parse2016ElectionResults(electionData);
//        System.out.println(electionResults);

//        DataManager dataManager;
//        dataManager.add();
//
//        for (int i = 1; i < electionResults.size(); i++) {
//            if (dataManager.containsAlreadyExistingState(electionResults.get(i).getStateAbbr())) {
//            }
//        }




//        String educationData = Utils.readFileAsString("data/Education.csv");
//
//        ArrayList<Education2016> educationResults = Utils.parse2016Education(educationData);
//        System.out.println(educationResults);

        String unemploymentData = Utils.readFileAsString("data/Unemployment.csv");

        ArrayList<Employment2016> employmentResults = Utils.parse2016Unemployment(unemploymentData);
        System.out.println(employmentResults);


    }
}
