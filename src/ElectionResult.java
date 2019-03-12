public class ElectionResult {

    private int diff, combinedFips;
    private double votesDem, votesGop, totalVotes, perDem, perGop, perPointDiff;
    private String stateAbbr, countyName;

    public ElectionResult(double votesDem, double votesGop, double totalVotes, double perDem, double perGop, int diff, double perPointDiff, String stateAbbr, String countyName, int combinedFips) {
        this.votesDem = votesDem;
        this.votesGop = votesGop;
        this.totalVotes = totalVotes;
        this.diff = diff;
        this.combinedFips = combinedFips;
        this.perDem = perDem;
        this.perGop = perGop;
        this.perPointDiff = perPointDiff;
        this.stateAbbr = stateAbbr;
        this.countyName = countyName;
    }

    public void resultToString() {
        System.out.println(votesDem + ", " + votesGop + ", " + totalVotes + ", " + perDem + ", " + perGop + ", " + diff + ", " + perPointDiff + ", " + stateAbbr + ", " + countyName + ", " + combinedFips);
    }

    public double getVotesDem() {
        return votesDem;
    }

    public void setVotesDem(double votesDem) {
        this.votesDem = votesDem;
    }

    public double getVotesGop() {
        return votesGop;
    }

    public void setVotesGop(double votesGop) {
        this.votesGop = votesGop;
    }

    public double getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(double totalVotes) {
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

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }
}
