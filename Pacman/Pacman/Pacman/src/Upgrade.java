public class Upgrade {
    private String name;
    private double effect;

    public Upgrade(String name, double effect) {
        this.name = name;
        this.effect = effect;
    }

    public String getName() {
        return name;
    }

    public double getEffect() {
        return effect;
    }
}
