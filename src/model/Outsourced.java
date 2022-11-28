package model;
/** This class creates the data for parts that are outsourced. */
public class Outsourced extends Part {

    String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** @return the companyName */
    public String getCompanyName() {
        return companyName;
    }

    /** @param companyName the companyName to be set. */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
