package Cars;


public abstract class RailroadCar {
    private final int weight;
    private final int id;
    private static int count = 1;

    public RailroadCar(int weight) {
        this.id = count++;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public int getId() {
        return id;
    }

    public abstract boolean requiresElectricity();

    public abstract String getType();

}
