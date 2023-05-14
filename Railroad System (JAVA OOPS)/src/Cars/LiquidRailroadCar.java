package Cars;

public class LiquidRailroadCar extends BasicRailroadFreightCar {
    private final double Volume=1000; //litres
    private boolean hasLiquidMaterial;
    private int fillLiquid;
    private boolean hasContainer;

    public LiquidRailroadCar(boolean hasLiquidMaterial, boolean hasContainer, int weight) {
        super(weight);
        this.hasLiquidMaterial = hasLiquidMaterial;
        this.hasContainer = hasContainer;
        this.fillLiquid =0;
    }

    public void loadLiquid(int fillLiquid) {
        if (this.fillLiquid + fillLiquid > Volume) {
            System.out.println("Error: Not enough volume to load " + fillLiquid + " liquid.");
        } else {
            this.fillLiquid += fillLiquid;
            System.out.println(fillLiquid + " litre liquid loaded.");
        }
    }
    public void unloadLiquid(){
        if(this.fillLiquid == 0){
            System.err.println("No liquid found to unload.");
        }else {
            this.fillLiquid = 0;
            System.out.println("Gas unloaded.");
        }
    }

    public boolean isHasLiquidMaterial() {
        return hasLiquidMaterial;
    }

    public void setHasLiquidMaterial(boolean hasLiquidMaterial) {
        this.hasLiquidMaterial = hasLiquidMaterial;
    }

    public int getFillLiquid() {
        return fillLiquid;
    }

    public void setFillLiquid(int fillLiquid) {
        this.fillLiquid = fillLiquid;
    }

    public boolean isHasContainer() {
        return hasContainer;
    }

    public void setHasContainer(boolean hasContainer) {
        this.hasContainer = hasContainer;
    }

    public double getVolume() {
        return Volume;
    }

    @Override
    public String getType() {
        return "Liquid";
    }

    @Override
    public String toString() {
        return "LiquidRailroadCar{" +
                "id=" + getId() +
                " Volume=" + Volume +
                ", hasLiquidMaterial=" + hasLiquidMaterial +
                ", fillLiquid=" + fillLiquid +
                ", hasContainer=" + hasContainer +
                '}';
    }
}
