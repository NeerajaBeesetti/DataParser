import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private List<State> states;

    // private FileWriter fileWriter = new FileWriter("data/newData.csv");
    // private BufferedWriter bufferedWriter = new BufferedWriter();

    public DataManager() {
        states = new ArrayList<>();
    }

    public void addStateObjs(String[] lines, List<State> states) {
        for (String line : lines) {

            String[] items = line.split(",");
            String stateAbbr = items[8];

            State state = getState(stateAbbr, states);
            if (state == null) {
                State toAdd = new State();
                toAdd.setName(stateAbbr);
                states.add(toAdd);
            }
        }
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public State getAlreadyExistingState(String stateToCheck) {
        for (int i = 0; i < states.size(); i++) {
            if (states.get(i).getName().equals(stateToCheck)) {
                return states.get(i);
            }
        }
        return null;
    }

    public void add(State s) {
        states.add(s);
    }

    public void loadAllData(String electionFile, String educationFile, String unemploymentFile, String povertyFile, DataManager dataManager) {
        String[] electionRawCleanedLines = Utils.readFileAsCleanedLines(electionFile, 1);
        String[] educationRawCleanedLines = Utils.readFileAsCleanedLines(educationFile, 6);
        String[] unemploymentRawCleanedLines = Utils.readFileAsCleanedLines(unemploymentFile, 8);
        String[] povertyRawCleanedLines = Utils.readFileAsCleanedLines(povertyFile, 1);


        List<State> states = dataManager.getStates();
        addStateObjs(electionRawCleanedLines, states);
        addCountyObjs(electionRawCleanedLines, states);

        Utils.parse2016ElectionResults(electionRawCleanedLines, dataManager);
        Utils.parse2016Education(educationRawCleanedLines, dataManager);
        Utils.parse2016Unemployment(unemploymentRawCleanedLines, dataManager);


        //System.out.println(employmentResults);


        // dataManager.getStates().get(4).getCounties().get(0).getEmploy2016().resultToString();

        //adding education and employment data done inside parsing methods

    }

    public void addCountyObjs(String[] lines, List<State> states) {
        for (String line : lines) {

            String[] items = line.split(",");
            String stateAbbr = items[8];

            State state = getState(stateAbbr, states);
            if (state == null) {
                System.out.println("ERROR: non-existent state: " + stateAbbr);
                continue;
            }

            String countyName = items[9];
            int fips = Integer.parseInt(items[10]);
            state.addCounty(new County(countyName, fips));

        }
    }

    public State getState(String name, List<State> states) {
        for (State temp : states) {
            if (temp.getName().equals(name)) return temp;
        }

        return null;
    }

    public void exportData() {
        String newData = "";

        for (int i = 0; i < states.size(); i++) {


            for (int j = 0; j < states.get(i).getCounties().size(); j++) {

                newData += states.get(i).getName() + "," + states.get(i).getCounties().get(j).getName() + "\n";


            }

        }
        writeDataToFile(newData);
    }

    private void writeDataToFile(String s) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/newData.csv"))) {
            writer.write(s);
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
