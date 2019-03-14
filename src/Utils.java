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

//    public static ArrayList<ElectionResult> parse2016ElectionResults(String data) {
//        ArrayList<ElectionResult> results = new ArrayList<>();
//
//        String[] rows = data.split("\n");
//
//        for (int i = 1; i < rows.length; i++) {
//            rows[i] = fixRow(rows[i]);
//            String[] line = rows[i].split(",");
//
//            double votesDem = Double.parseDouble(line[1]);
//            double votesGop = Double.parseDouble(line[2]);
//            double totalVotes = Double.parseDouble(line[3]);
//            double perDem = Double.parseDouble(line[4]);
//            double perGop = Double.parseDouble(line[5]);
//            int diff = Integer.parseInt(line[6]);
//            double perPointDiff = Double.parseDouble(line[7]);
//            String stateAbbr = line[8];
//            String countryName = line[9];
//            int combinedFips = Integer.parseInt(line[10]);
//
//            ElectionResult result = new ElectionResult(votesDem, votesGop, totalVotes, perDem, perGop, diff, perPointDiff, stateAbbr, countryName, combinedFips);
//            result.resultToString();
//            results.add(result);
//
//        }
//        return results;
//    }

//    public static ArrayList<Education2016> parse2016Education(String data) {
//        ArrayList<Education2016> results = new ArrayList<>();
//        String[] rows = data.split("\n");
//       double noHighSchool = 0, onlyHighSchool = 0, someCollege = 0, bachelorsOrMore = 0;
//
//
//        rows[4]=  fixRow(rows[4]);
//
//        for (int i = 5; i < rows.length; i++) {
//            rows[i] = fixRow(rows[i]);
//            String[] line = rows[i].split(",");
//
//            if (isInteger(line, 43)) {
//                 noHighSchool = Double.parseDouble(line[43]);
//            }
//            if (isInteger(line, 44)) {
//                 onlyHighSchool = Double.parseDouble(line[44]);
//
//            }
//            if (isInteger(line, 45)) {
//                 someCollege = Double.parseDouble(line[45]);
//
//            }
//            if (isInteger(line, 44)) {
//                 bachelorsOrMore = Double.parseDouble(line[46]);
//
//            }
//
////            double noHighSchool = Double.parseDouble(line[rows[4].indexOf("Percent of adults with less than a high school diploma 2012-2016")]);
////            double onlyHighSchool = Double.parseDouble(line[rows[4].indexOf("Percent of adults with a high school diploma only 2012-2016")]);
////            double someCollege = Double.parseDouble(line[rows[4].indexOf("Percent of adults completing some college or associate's degree 2012-2016")]);
////            double bachelorsOrMore = Double.parseDouble(line[rows[4].indexOf("Percent of adults with a bachelor's degree or higher 2012-2016")]);
//
//            //TODO: fix error
//            //indexOutOfBounds error
//            //most likely due to empty indexes (looks like: ,,,,,,)
//
//            Education2016 result = new Education2016(noHighSchool, onlyHighSchool, someCollege, bachelorsOrMore);
//            results.add(result);
//        }
//        return results;
//    }

//    public static ArrayList<Employment2016> parse2016Unemployment(String data) {
//        ArrayList<Employment2016> results = new ArrayList<>();
//        String[] rows = data.split("\n");
//
//        for (int i = 8; i < rows.length; i++) {
//            rows[i] = fixRow(rows[i]);
//            String[] line = rows[i].split(",");
//
//
//            int totalLaborForce = Integer.parseInt(line[42]);
//            int employedLaborForce = Integer.parseInt(line[43]);
//            int unemployedLaborForce = Integer.parseInt(line[44]);
//            double unemployedPercent = Double.parseDouble(line[45]);
//
//            //TODO: fix error
//            //number format exception error
//            //most likely due to conversion of String to int?
//
//            Employment2016 result = new Employment2016(totalLaborForce, employedLaborForce, unemployedLaborForce, unemployedPercent);
//            results.add(result);
//        }
//        return results;
//
//    }



    public static void addStateObjs(String[] lines, List<State> states) {
        for (String line: lines) {

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
        for( String line : lines) {

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

        if (line.length() == 0) return "0";

        while (line.indexOf("\"") != -1) {
            int quoteStartIndex = line.indexOf("\"");
            int quoteEndIndex = line.indexOf("\"", quoteStartIndex + 1);

            String wordWQuotes = line.substring(quoteStartIndex, quoteEndIndex + 1);

            String unfixedWord = line.substring(quoteStartIndex + 1, quoteEndIndex);
            String fixedWord = unfixedWord.replace(",", "");

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
            out[i-linesToSkip] = fixLine(lines[i]);
        }
        return out;

    }
}