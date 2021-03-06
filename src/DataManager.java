import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataManager {
    private List<State> states;
    private List<County> counties;


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

    public County searchByCountyName(String countyToCheck) {
        //System.out.println(countyToCheck);
        countyToCheck.trim();
        for (State sTemp : states) {
            for (County cTemp : sTemp.getCounties())
                if (cTemp.getName().equals(countyToCheck)) {
                    return cTemp;
                }
        }
        return null;

    }

    public State getAlreadyExistingState(String stateToCheck) {
        //System.out.println(stateToCheck);
        stateToCheck.trim();
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


        addStateObjs(electionRawCleanedLines, states);
        addCountyObjs(electionRawCleanedLines, states);

        Utils.parse2016ElectionResults(electionRawCleanedLines, dataManager);
        Utils.parse2016Education(educationRawCleanedLines, dataManager);
        Utils.parse2016Unemployment(unemploymentRawCleanedLines, dataManager);
        Utils.parse2016Population(populationRawCleanedLines, dataManager);
        Utils.parse2016Poverty(povertyRawCleanedLines, dataManager);




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
        String newData = "State,County Name,FIPS,Number of Dem Votes,Number of GOP Votes,Percent With No High School,Percent With Only High School,Percent With Some College, Percent With Bachelor's or more,Percent of County Population Below Poverty" + "\n";

        for (int i = 0; i < states.size(); i++) {

            State currentState = states.get(i);

            for (int j = 0; j < states.get(i).getCounties().size(); j++) {
                County currentCounty = currentState.getCounties().get(j);

                Education2016 education2016 = currentCounty.getEduc2016();
                ElectionResult electionResult = currentCounty.getVote2016();
                Poverty2016 pov2016 = currentCounty.getPov2016();
                Population2016 population2016 = currentCounty.getPop2016();

                if (population2016 != null && pov2016 != null) {
                    double population = (pov2016.getnumBelowPov() / (double) population2016.getPopNum());
                    population = (population * 100);




                    newData += currentState.getName() + "," + currentCounty.getName() + "," + currentCounty.getFips() + "," +
                            electionResult.getVotesDem() + "," + electionResult.getVotesGop() + "," + education2016.getNoHighSchool() + "," +
                            education2016.getOnlyHighSchool() + "," + education2016.getSomeCollege() + "," + education2016.getBachelorsOrMore() + "," + population + "\n";
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
