package Cars;

public class HeavyRailroadFreightCar extends RailroadCar {

    public HeavyRailroadFreightCar(int weight) {
        super(weight);
    }

    @Override
    public boolean requiresElectricity() {
        return false;
    }

    @Override
    public String getType() {
        return "Heavy Freight Car";
    }
}
