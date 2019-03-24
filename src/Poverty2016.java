public class Poverty2016 {

    private double numBelowPov;

    public Poverty2016() { }

    public double getnumBelowPov() {
        return numBelowPov;
    }

    public void setnumBelowPov(double numBelowPov) {
        this.numBelowPov = numBelowPov;
    }

    public void resultToString() {
        System.out.println("Number of People below Poverty Line: " + numBelowPov);
    }
}
