package Cars;

import Cars.RailroadCar;

public class RestaurantCar extends RailroadCar {
    private int numOfTables;
    private boolean hasBar;
    private int numberOfDiners;

    public RestaurantCar(int numOfTables, boolean hasBar, int weight) {
        super(weight);
        this.numOfTables = numOfTables;
        this.hasBar = hasBar;
        this.numberOfDiners =0;
    }

    public void loadDiners(int numberOfDiners){
        if(this.numberOfDiners + numberOfDiners > numOfTables){
            System.out.println("Error: Not enough Diners to load " + numberOfDiners + " diners.");
        } else
        {
            this.numberOfDiners += numberOfDiners;
            System.out.println(numberOfDiners + " Diners loaded.");
        }
    }

    public void unloadDiners(){
        if(this.numberOfDiners == 0){
            System.err.println("No diners found to unload.");
        }else {
            this.numberOfDiners = 0;
            System.out.println("Diners unloaded.");
        }
    }

    public int getNumberOfDiners() {
        return numberOfDiners;
    }

    public void setNumberOfDiners(int numberOfDiners) {
        this.numberOfDiners = numberOfDiners;
    }

    public int getNumOfTables() {
        return numOfTables;
    }

    public void setNumOfTables(int numOfTables) {
        this.numOfTables = numOfTables;
    }

    public boolean isHasBar() {
        return hasBar;
    }

    public void setHasBar(boolean hasBar) {
        this.hasBar = hasBar;
    }

    @Override
    public boolean requiresElectricity() {
        return false;
    }

    @Override
    public String getType() {
        return "Restaurant";
    }

    @Override
    public String toString() {
        return "RestaurantCar{" +
                "id=" + getId() +
                " numOfTables=" + numOfTables +
                ", hasBar=" + hasBar +
                ", numberOfDiners=" + numberOfDiners +
                '}';
    }
}
