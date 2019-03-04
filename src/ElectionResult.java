public class ElectionResult {

    private int votesDem, votesGop, totalVotes, diff, combinedFips;
    private double perDem, perGop, perPointDiff;
    private String stateAbbr, countryName;

    public ElectionResult(int votesDem, int votesGop, int totalVotes, double perDem, double perGop, int diff, double perPointDiff,  String stateAbbr, String countryName, int combinedFips) {
        this.votesDem = votesDem;
        this.votesGop = votesGop;
        this.totalVotes = totalVotes;
        this.diff = diff;
        this.combinedFips = combinedFips;
        this.perDem = perDem;
        this.perGop = perGop;
        this.perPointDiff = perPointDiff;
        this.stateAbbr = stateAbbr;
        this.countryName = countryName;
    }

    public void resultToString() {
        System.out.println(votesDem + ", " + votesGop + ", " + totalVotes+ ", " +perDem+ ", " +perGop+ ", " +diff+ ", " +perPointDiff+ ", " +stateAbbr+ ", " +countryName+ ", " +combinedFips);
    }

    public int getVotesDem() {
        return votesDem;
    }

    public void setVotesDem(int votesDem) {
        this.votesDem = votesDem;
    }

    public int getVotesGop() {
        return votesGop;
    }

    public void setVotesGop(int votesGop) {
        this.votesGop = votesGop;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }

    public int getDiff() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }

    public int getCombinedFips() {
        return combinedFips;
    }

    public void setCombinedFips(int combinedFips) {
        this.combinedFips = combinedFips;
    }

    public double getPerDem() {
        return perDem;
    }

    public void setPerDem(double perDem) {
        this.perDem = perDem;
    }

    public double getPerGop() {
        return perGop;
    }

    public void setPerGop(double perGop) {
        this.perGop = perGop;
    }

    public double getPerPointDiff() {
        return perPointDiff;
    }

    public void setPerPointDiff(double perPointDiff) {
        this.perPointDiff = perPointDiff;
    }

    public String getStateAbbr() {
        return stateAbbr;
    }

    public void setStateAbbr(String stateAbbr) {
        this.stateAbbr = stateAbbr;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
