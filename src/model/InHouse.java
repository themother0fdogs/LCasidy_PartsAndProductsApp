package model;
/** This class creates the data for parts that are InHouse. */
public class InHouse extends Part {

    int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** @return the machineId */
    public int getMachineId() {
        return machineId;
    }

    /** @param machineId the machineId to be set. */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
