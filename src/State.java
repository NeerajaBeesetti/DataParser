import java.util.ArrayList;
import java.util.List;

public class State {

    private String name;
    private List<County> counties;
    private int firstNumInFIPS;



    public State() {
        counties = new ArrayList<>();
    }

    public void addCounty(County county) {
        counties.add(county);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<County> getCounties() {
        return counties;
    }

    public void printCounties() {
        for (int i = 0; i < counties.size(); i++) {
            System.out.println(counties.get(i).getName());

        }
    }

    public void setCounties(List<County> counties) {
        this.counties = counties;
    }


    public County getCounty(String countyName, int combinedFips) {
        for (County c : counties) {
            if (c.getName().equals(countyName) || c.getFips() == combinedFips) return c;
        }
        return null;
    }

    public int getFirstNumInFIPS() {
        return firstNumInFIPS;
    }

    public void setFirstNumInFIPS(int firstNumInFIPS) {
        this.firstNumInFIPS = firstNumInFIPS;
    }

}

