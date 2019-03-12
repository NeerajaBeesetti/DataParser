import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

    public static ArrayList<ElectionResult> parse2016ElectionResults(String data) {
        ArrayList<ElectionResult> results = new ArrayList<>();

        String[] rows = data.split("\n");

        for (int i = 1; i < rows.length; i++) {
            rows[i] = fixRow(rows[i]);
            String[] line = rows[i].split(",");

            double votesDem = Double.parseDouble(line[1]);
            double votesGop = Double.parseDouble(line[2]);
            double totalVotes = Double.parseDouble(line[3]);
            double perDem = Double.parseDouble(line[4]);
            double perGop = Double.parseDouble(line[5]);
            int diff = Integer.parseInt(line[6]);
            double perPointDiff = Double.parseDouble(line[7]);
            String stateAbbr = line[8];
            String countyName = line[9];
            int combinedFips = Integer.parseInt(line[10]);

            ElectionResult result = new ElectionResult(votesDem, votesGop, totalVotes, perDem, perGop, diff, perPointDiff, stateAbbr, countyName, combinedFips);
            result.resultToString();
            results.add(result);

        }
        return results;
    }

    public static ArrayList<Education2016> parse2016Education(String data) {
        ArrayList<Education2016> results = new ArrayList<>();
        String[] rows = data.split("\n");

        for (int i = 5; i < rows.length; i++) {
            rows[i] = fixRow(rows[i]);
            String[] line = rows[i].split(",");

            double noHighSchool = Double.parseDouble(line[43]);
            double onlyHighSchool = Double.parseDouble(line[44]);
            double someCollege = Double.parseDouble(line[45]);
            double bachelorsOrMore = Double.parseDouble(line[46]);

            //TODO: fix error
            //indexOutOfBounds error
            //most likely due to empty indexes (looks like: ,,,,,,)

            Education2016 result = new Education2016(noHighSchool, onlyHighSchool, someCollege, bachelorsOrMore);
            results.add(result);
        }
        return results;
    }

    public static ArrayList<Employment2016> parse2016Unemployment(String data) {
        ArrayList<Employment2016> results = new ArrayList<>();
        String[] rows = data.split("\n");


        for (int i = 8; i < rows.length; i++) {
            rows[i] = fixRow(rows[i]);
            String[] line = rows[i].split(",");

            int totalLaborForce, employedLaborForce, unemployedLaborForce;
            double unemployedPercent;


            if (!isInteger(line[42])) line[42] = "0";
            if (!isInteger(line[43])) line[43] = "0";
            if (!isInteger(line[44])) line[44] = "0";
            if (!isInteger(line[45])) line[45] = "0";

                totalLaborForce = Integer.parseInt(line[42]);
                 employedLaborForce = Integer.parseInt(line[43]);
                 unemployedLaborForce = Integer.parseInt(line[44]);
                 unemployedPercent = Double.parseDouble(line[45]);




            //TODO: fix error
            //number format exception error
            //conversion of String to int?

            Employment2016 result = new Employment2016(totalLaborForce, employedLaborForce, unemployedLaborForce, unemployedPercent);
            results.add(result);
        }
        return results;

    }

    public static boolean isInteger(String str) {
        int n = 0;
        try {
            n = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    private static String fixRow(String row) {
        row = row.replace(" ", "");

        while (row.indexOf("\"") != -1) {
            int quoteStartIndex = row.indexOf("\"");
            int quoteEndIndex = row.indexOf("\"", quoteStartIndex + 1);

            String wordWQuotes = row.substring(quoteStartIndex, quoteEndIndex + 1);

            String unfixedWord = row.substring(quoteStartIndex + 1, quoteEndIndex);
            String fixedWord = unfixedWord.replace(",", "");

            row = row.replace(wordWQuotes, fixedWord);

        }

        while (row.indexOf("%") != -1) {
            int signIndex = row.indexOf("%");
            row = row.substring(0, signIndex) + row.substring(signIndex + 1, row.length());

        }

        while (row.indexOf("$") != -1) {
            int signIndex = row.indexOf("$");
            row = row.substring(0, signIndex) + row.substring(signIndex + 1, row.length());

        }

        return row;

    }

}