import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {

    public static String readFileAsString(String filepath) {
        StringBuilder output = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(filepath))) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                output.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output.toString();
    }

    public static ArrayList<ElectionResult> parse2016ElectionResults(String[] lines, DataManager dataManager) {
        ArrayList<ElectionResult> results = new ArrayList<>();

        for (String line : lines) {
            String[] items = line.split(",");

            double votesDem = Double.parseDouble(items[1]);
            double votesGop = Double.parseDouble(items[2]);
            double totalVotes = Double.parseDouble(items[3]);
            double perDem = Double.parseDouble(items[4]);
            double perGop = Double.parseDouble(items[5]);
            int diff = Integer.parseInt(items[6]);
            double perPointDiff = Double.parseDouble(items[7]);
            String stateAbbr = items[8];
            String countyName = items[9];
            int combinedFips = Integer.parseInt(items[10]);

            ElectionResult result = new ElectionResult(votesDem, votesGop, totalVotes, perDem, perGop, diff, perPointDiff, stateAbbr, countyName, combinedFips);
            //result.resultToString();
            results.add(result);

            State state = dataManager.getAlreadyExistingState(stateAbbr);

            if (state != null) {
                County c = state.getCounty(countyName, combinedFips);
                c.setVote2016(result);
            }

        }
        return results;
    }

    public static ArrayList<Education2016> parse2016Education(String[] lines, DataManager dataManager) {
        ArrayList<Education2016> results = new ArrayList<>();

        for (String line : lines) {
            String[] items = line.split(",");
            double noHighSchool = Double.parseDouble(items[43]);
            double onlyHighSchool = Double.parseDouble(items[44]);
            double someCollege = Double.parseDouble(items[45]);
            double bachelorsOrMore = Double.parseDouble(items[46]);

            Education2016 result = new Education2016();
            result.setNoHighSchool(noHighSchool);
            result.setOnlyHighSchool(onlyHighSchool);
            result.setSomeCollege(someCollege);
            result.setBachelorsOrMore(bachelorsOrMore);
            results.add(result);

            String stateAbbr = items[1];
            State state = dataManager.getAlreadyExistingState(stateAbbr);

            if (state != null) {
                County c = state.getCounty(items[2], Integer.parseInt(items[0]));
                c.setEduc2016(result);
            }
        }
        return results;
    }

    public static ArrayList<Employment2016> parse2016Unemployment(String[] lines, DataManager dataManager) {
        ArrayList<Employment2016> results = new ArrayList<>();

        for (String line : lines) {
            String[] items = line.split(",");

            int totalLaborForce = Integer.parseInt(items[42]);
            int employedLaborForce = Integer.parseInt(items[43]);
            int unemployedLaborForce = Integer.parseInt(items[44]);
            double unemployedPercent = Double.parseDouble(items[45]);

            //TODO: fix error
            //number format exception error
            //input string: "     702 "

            Employment2016 result = new Employment2016();
            result.setTotalLaborForce(totalLaborForce);
            result.setEmployedLaborForce(employedLaborForce);
            result.setUnemployedLaborForce(unemployedLaborForce);
            result.setUnemployedPercent(unemployedPercent);
            results.add(result);

            String stateAbbr = items[1];
            State state = dataManager.getAlreadyExistingState(stateAbbr);

            if (state != null) {
                County c = state.getCounty(items[2], Integer.parseInt(items[0]));
                c.setEmploy2016(result);
            }
        }
        return results;
    }

    public static void addStateObjs(String[] lines, List<State> states) {
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

    public static void addCountyObjs(String[] lines, List<State> states) {
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

    public static State getState(String name, List<State> states) {
        for (State temp : states) {
            if (temp.getName().equals(name)) return temp;
        }

        return null;
    }


    public static boolean isInteger(String[] line, int index) {
        int n = 0;
        try {
            n = Integer.parseInt(line[index]);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private static String fixLine(String line) {
        line = line.trim();

        while (line.indexOf(",,") != -1) {
            line = line.replace(",,", ",0,");
        }

        while (line.indexOf("\"") != -1) {
            int quoteStartIndex = line.indexOf("\"");
            int quoteEndIndex = line.indexOf("\"", quoteStartIndex + 1);

            String wordWQuotes = line.substring(quoteStartIndex, quoteEndIndex + 1);
            String unfixedWord = line.substring(quoteStartIndex + 1, quoteEndIndex);

            String fixedWord = unfixedWord.replace(",", "");
            fixedWord = fixedWord.trim();
            line = line.replace(wordWQuotes, fixedWord);

        }

        while (line.indexOf("%") != -1) {
            int signIndex = line.indexOf("%");
            line = line.substring(0, signIndex) + line.substring(signIndex + 1, line.length());

        }

        while (line.indexOf("$") != -1) {
            int signIndex = line.indexOf("$");
            line = line.substring(0, signIndex) + line.substring(signIndex + 1, line.length());

        }
        return line;
    }

    public static String[] readFileAsCleanedLines(String filepath, int linesToSkip) {
        String[] lines = (readFileAsString(filepath)).split("\n");
        String[] out = new String[lines.length - linesToSkip];
        for (int i = linesToSkip; i < lines.length; i++) {
            out[i - linesToSkip] = fixLine(lines[i]);
        }
        return out;

    }
}