public class County {
    private String name;
    private int fips;
    private ElectionResult vote2016;
    private Education2016 educ2016;
    private Employment2016 employ2016;
    private Population2016 pop2016;
    private Poverty2016 pov2016;

    public County(String name, int fips) {
        this.name = name;
        this.fips = fips;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFips() {
        return fips;
    }

    public void setFips(int fips) {
        this.fips = fips;
    }

    public ElectionResult getVote2016() {
        return vote2016;
    }

    public void setVote2016(ElectionResult vote2016) {
        this.vote2016 = vote2016;
    }

    public Education2016 getEduc2016() {
        return educ2016;
    }

    public void setEduc2016(Education2016 educ2016) {
        this.educ2016 = educ2016;
    }

    public Employment2016 getEmploy2016() {
        return employ2016;
    }

    public void setEmploy2016(Employment2016 employ2016) {
        this.employ2016 = employ2016;
    }

    public Population2016 getPop2016() { return pop2016; }

    public void setPop2016(Population2016 pop2016) {
        this.pop2016 = pop2016;
    }

    public Poverty2016 getPov2016() { return pov2016; }

    public void setPov2016(Poverty2016 pov2016) { this.pov2016 = pov2016; }
}