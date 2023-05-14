package Cars;

public class BasicRailroadFreightCar extends RailroadCar {

    public BasicRailroadFreightCar(int weight) {
        super(weight);
    }

    @Override
    public boolean requiresElectricity() {
        return false;
    }

    @Override
    public String getType() {
        return "Basic Freight Car";
    }
}
