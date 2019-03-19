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

    public static void parse2016ElectionResults(String[] lines, DataManager dataManager) {
        ArrayList<ElectionResult> results = new ArrayList<>();

        for (String line : lines) {
            String[] items = line.split(",");

            double votesDem = Double.parseDouble(items[1].trim());
            double votesGop = Double.parseDouble(items[2].trim());
            double totalVotes = Double.parseDouble(items[3].trim());
            double perDem = Double.parseDouble(items[4].trim());
            double perGop = Double.parseDouble(items[5].trim());
            int diff = Integer.parseInt(items[6].trim());
            double perPointDiff = Double.parseDouble(items[7].trim());
            String stateAbbr = items[8];
            String countyName = items[9];
            int combinedFips = Integer.parseInt(items[10].trim());

            ElectionResult result = new ElectionResult(votesDem, votesGop, totalVotes, perDem, perGop, diff, perPointDiff, stateAbbr, countyName, combinedFips);
            //result.resultToString();
            results.add(result);

            State state = dataManager.getAlreadyExistingState(stateAbbr);

            if (state != null) {
                County c = state.getCounty(countyName, combinedFips);
                c.setVote2016(result);
            }

        }

    }

    public static void parse2016Education(String[] lines, DataManager dataManager) {
        ArrayList<Education2016> results = new ArrayList<>();

        for (int i = 6; i < lines.length - 10; i++) {
            String line = lines[i];
            String[] items = line.split(",");
            double noHighSchool = Double.parseDouble(items[43].trim());
            double onlyHighSchool = Double.parseDouble(items[44].trim());
            double someCollege = Double.parseDouble(items[45].trim());
            double bachelorsOrMore = Double.parseDouble(items[46].trim());

            Education2016 result = new Education2016();
            result.setNoHighSchool(noHighSchool);
            result.setOnlyHighSchool(onlyHighSchool);
            result.setSomeCollege(someCollege);
            result.setBachelorsOrMore(bachelorsOrMore);
            results.add(result);

            int fipsNum = Integer.parseInt(items[0]);
            if (fipsNum % 1000 != 0) {
                String stateAbbr = items[1];
                State state = dataManager.getAlreadyExistingState(stateAbbr);

                if (state != null) {
                    County c = state.getCounty(items[2], Integer.parseInt(items[0]));
                    if (c != null) {
                        c.setEduc2016(result);
                    }
                }
            }

        }


    }

    public static void parse2016Unemployment(String[] lines, DataManager dataManager) {
        ArrayList<Employment2016> results = new ArrayList<>();

        for (String line : lines) {
            String[] items = line.split(",");

            int totalLaborForce = Integer.parseInt(items[42].trim());
            int employedLaborForce = Integer.parseInt(items[43].trim());
            int unemployedLaborForce = Integer.parseInt(items[44].trim());
            double unemployedPercent = Double.parseDouble(items[45].trim());

            Employment2016 result = new Employment2016();
            result.setTotalLaborForce(totalLaborForce);
            result.setEmployedLaborForce(employedLaborForce);
            result.setUnemployedLaborForce(unemployedLaborForce);
            result.setUnemployedPercent(unemployedPercent);
            results.add(result);

            int fipsNum = Integer.parseInt(items[0]);
            if (fipsNum % 1000 != 0) {
                String stateAbbr = items[1];
                State state = dataManager.getAlreadyExistingState(stateAbbr);

                if (state != null) {
                    County c = state.getCounty(items[2], Integer.parseInt(items[0]));
                    if (c != null) {
                        c.setEmploy2016(result);
                    }
                }
            }

        }

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

    private static String fixLine(String line) {
        //line = line.trim();

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


//    public static String[] readFileAsClean(String file, int startLine) {
//        String[] results = file.split("\n");
//
//        for (int i = startLine; i < results.length; i++) {
//            results[i] = cleanLine(results[i]);
//
//
//        }
//
//        return results;
//    }
//
//    private static String cleanLine(String row) {
//        int firstQuote = row.indexOf("\"");
//        int secondQuote = row.indexOf("\"", firstQuote + 1);
//
//        while (firstQuote != -1 && secondQuote != -1) {
//            row = cleanSubstring(row, firstQuote, secondQuote);
//            firstQuote = row.indexOf("\"");
//            secondQuote = row.indexOf("\"", firstQuote + 1);
//        }
//        row = row.replaceAll("%", "");
//        return row;
//    }
//
//    private static String cleanSubstring(String row, int firstQuote, int secondQuote) {
//        String before = row.substring(0, firstQuote);
//        String after = row.substring(secondQuote + 1);
//
//        String toClean = row.substring(firstQuote + 1, secondQuote);
//
//        return before + clean(toClean) + after;
//    }
//
//    private static String clean(String toClean) {
//        toClean = toClean.replaceAll(",", "").trim();
//        toClean = toClean.replaceAll("%", "");
//        return toClean;
//    }


}