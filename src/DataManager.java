import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private List<State> states;
    private List<County> counties;

    // private FileWriter fileWriter = new FileWriter("data/newData.csv");
    // private BufferedWriter bufferedWriter = new BufferedWriter();

    public DataManager() {
        states = new ArrayList<>();
        counties = new ArrayList<>();
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

    public List<County> getCounties() {
        return counties;
    }

    public void setCounties(List<County> counties) {
        this.counties = counties;
    }

    public County getAlreadyExistingCounty(String countyToCheck) {
        for (County temp : counties) {
            if (temp.getName().equals(countyToCheck)) {
                return temp;
            }
        }
        return null;
    }

    public State getAlreadyExistingState(String stateToCheck) {
        for (State temp : states) {
            if (temp.getName().equals(stateToCheck)) {
                return temp;
            }
        }
        return null;
    }

    public State getAlreadyExistingState(int fipsNumFirstNum) {
        for (int i = 0; i < states.size(); i++) {
            if (states.get(i).getFirstNumInFIPS() == fipsNumFirstNum) {
                return states.get(i);
            }
        }
        return null;
    }

    public void add(State s) {
        states.add(s);
    }

    public void loadAllData(String electionFile, String educationFile, String unemploymentFile, String povertyFile, String populationFile, DataManager dataManager) {
        String[] electionRawCleanedLines = Utils.readFileAsCleanedLines(electionFile, 1, 0);
        String[] educationRawCleanedLines = Utils.readFileAsCleanedLines(educationFile, 6, 10);
        String[] unemploymentRawCleanedLines = Utils.readFileAsCleanedLines(unemploymentFile, 8, 0);
        String[] povertyRawCleanedLines = Utils.readFileAsCleanedLines(povertyFile, 1, 0);
        String[] populationRawCleanedLines = Utils.readFileAsCleanedLines(populationFile, 2, 0);


        // List<State> states = dataManager.getStates();
        addStateObjs(electionRawCleanedLines, states);
        addCountyObjs(electionRawCleanedLines, states);

        Utils.parse2016ElectionResults(electionRawCleanedLines, dataManager);
        Utils.parse2016Education(educationRawCleanedLines, dataManager);
        Utils.parse2016Unemployment(unemploymentRawCleanedLines, dataManager);
        Utils.parse2016Population(populationRawCleanedLines, dataManager);
        Utils.parse2016Poverty(povertyRawCleanedLines, dataManager);

        //System.out.println(employmentResults);


        dataManager.getStates().get(4).getCounties().get(0).getPop2016().resultToString();
        dataManager.getStates().get(4).getCounties().get(0).getPov2016().resultToString();
        //System.out.println(dataManager.getStates().get(4).getCounties().get(0).getName());


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
            County toAdd = new County(countyName, fips);
            state.addCounty(toAdd);
            state.setFirstNumInFIPS(Integer.parseInt((items[10].substring(0, 1))));
            counties.add(toAdd);
        }
    }

    public State getState(String name, List<State> states) {
        for (State temp : states) {
            if (temp.getName().equals(name)) return temp;
        }

        return null;
    }

    public void exportData() {
        String newData = "State,County Name,FIPS,Bachleors or more,No High School" + "\n";

        for (int i = 0; i < states.size(); i++) {

            State currentState = states.get(i);

            for (int j = 0; j < states.get(i).getCounties().size(); j++) {
                County currentCounty = currentState.getCounties().get(j);

                // newData += currentState.getName() + "," + currentCounty.getName() + "," + currentCounty.getFips() + "\n";
                if (currentCounty.getEduc2016() != null) {
                    newData += currentState.getName() + "," + currentCounty.getName() + "," +
                            currentCounty.getFips() + "," + currentCounty.getEduc2016().getBachelorsOrMore() + "," +currentCounty.getEduc2016().getNoHighSchool() + "\n";
                }
            }

        }
        writeDataToFile(newData);
    }

    private void writeDataToFile(String s) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/newData.csv"))) {
            writer.write(s);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
