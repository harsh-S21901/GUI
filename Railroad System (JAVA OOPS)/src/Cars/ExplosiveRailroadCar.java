package Cars;

public class ExplosiveRailroadCar extends HeavyRailroadFreightCar{
    private String typeOfExplosive;
    private boolean isDangerous;
    private final int maxCapacityOfExplosive = 1000; // in tons
    private int explosiveWeight;

    public ExplosiveRailroadCar(String typeOfExplosive, boolean isDangerous, int weight) {
        super(weight);
        this.typeOfExplosive = typeOfExplosive;
        this.isDangerous = isDangerous;
        this.explosiveWeight =0;
    }

    public void loadExplosive(int explosiveWeight){
        if(this.explosiveWeight + explosiveWeight > maxCapacityOfExplosive){
            System.out.println("Cant load" + explosiveWeight + " tons of explosive, only "+ maxCapacityOfExplosive + "allowed" );
        } else
        {
            this.explosiveWeight += explosiveWeight;
            System.out.println(explosiveWeight + " tons explosives loaded.");
        }
    }

    public void unloadExplosives(){
        if(this.explosiveWeight == 0){
            System.err.println("No explosives found.");
        }else{
            this.explosiveWeight =0;
            System.out.println("Explosives unloaded.");
        }
    }

    @Override
    public String getType(){
        return "Explosive";
    }

    public int getMaxCapacityOfExplosive() {
        return maxCapacityOfExplosive;
    }


    public int getExplosiveWeight() {
        return explosiveWeight;
    }

    public void setExplosiveWeight(int explosiveWeight) {
        this.explosiveWeight = explosiveWeight;
    }

    public String getTypeOfExplosive() {
        return typeOfExplosive;
    }

    public void setTypeOfExplosive(String typeOfExplosive) {
        this.typeOfExplosive = typeOfExplosive;
    }

    public boolean isDangerous() {
        return isDangerous;
    }

    public void setDangerous(boolean dangerous) {
        isDangerous = dangerous;
    }

    @Override
    public String toString() {
        return "ExplosiveRailroadCar{" +
                "id=" + getId()+
                ", typeOfExplosive='" + typeOfExplosive + '\'' +
                ", isDangerous=" + isDangerous +
                ", maxCapacityOfExplosive=" + maxCapacityOfExplosive +
                ", explosiveWeight=" + explosiveWeight +
                '}';
    }
}
