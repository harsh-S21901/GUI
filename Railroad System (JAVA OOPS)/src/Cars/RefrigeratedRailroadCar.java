package Cars;

public class RefrigeratedRailroadCar extends BasicRailroadFreightCar{
    private  double minTemperature; //degrees
    private double maxTemperature;
    private int coldGoods;

    public RefrigeratedRailroadCar(double minTemperature, double maxTemperature, int weight) {
        super(weight);
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.coldGoods =0;
    }

    public void loadColdGoods(int coldGoods){
        if(this.coldGoods + coldGoods > this.getWeight()){
            System.out.println("Error: Not enough capacity to load " + coldGoods + " goods.");
        } else {
            this.coldGoods += coldGoods;
            System.out.println(coldGoods + " cold goods loaded.");
        }
    }

    public void unloadColdGoods(){
        if(this.coldGoods == 0){
            System.err.println("No cold goods found to unload.");
        }else {
            this.coldGoods = 0;
            System.out.println("cold goods unloaded.");
        }
    }

    public int getColdGoods() {
        return coldGoods;
    }

    public void setColdGoods(int coldGoods) {
        this.coldGoods = coldGoods;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    @Override
    public String getType(){
        return "Refrigerator";
    }

    @Override
    public boolean requiresElectricity() {
        return true;
    }

    @Override
    public String toString() {
        return "RefrigeratedRailroadCar{" +
                "id=" + getId() +
                " minTemperature=" + minTemperature +
                ", maxTemperature=" + maxTemperature +
                ", coldGoods=" + coldGoods +
                '}';
    }
}
