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
            removeCommasInQuotations(rows, i);

            String[] line = rows[i].split(",");

            double votesDem = Double.parseDouble(line[1]);
            double votesGop = Double.parseDouble(line[2]);
            double totalVotes = Double.parseDouble(line[3]);
            double perDem = Double.parseDouble(line[4]);
            double perGop = Double.parseDouble(line[5]);
            int diff = Integer.parseInt(line[6]);
            double perPointDiff = Double.parseDouble(line[7].substring(0, line[7].length() - 1));
            String stateAbbr = line[8];
            String countryName = line[9];
            int combinedFips = Integer.parseInt(line[10]);

            ElectionResult result = new ElectionResult(votesDem, votesGop, totalVotes, perDem, perGop, diff, perPointDiff, stateAbbr, countryName, combinedFips);
            result.resultToString();
            results.add(result);

        }
        return results;
    }

    private static void removeCommasInQuotations(String[] rows, int i) {
        String stringDiff;
        String diffWComma;
        String diffWOComma;
        int stringStartIndex = rows[i].indexOf("\"");

        if (stringStartIndex != -1) {
            int stringEndIndex = rows[i].indexOf("\"", stringStartIndex + 1);

            rows[i] = rows[i].replace("\"", "");
            stringDiff = rows[i].substring(stringStartIndex, stringEndIndex - 1);
            diffWComma = stringDiff;
            stringDiff = stringDiff.replace(",", "");
            diffWOComma = stringDiff;

            rows[i] = rows[i].replace(diffWComma, diffWOComma);

        }
    }
}
