/***
 * Main class for data parsers
 */

public class Main {

    public static void main(String[] args) {
        //test of utils

        String data = Utils.readFileAsString("data/2016_Presidential_Results.csv");

        ArrayList<ElectionResult> results = Utils.parse2016ElectionResults(data);
    }
}
