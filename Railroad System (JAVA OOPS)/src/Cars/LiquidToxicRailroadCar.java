package Cars;

public class LiquidToxicRailroadCar extends LiquidRailroadCar{
    private String toxicMaterial;
    private boolean hasChemical;
    private int liquidAndToxicMaterial;

    public LiquidToxicRailroadCar(boolean hasLiquidMaterial, boolean hasContainer, int weight, String toxicMaterial, boolean hasChemical, int liquidAndToxicMaterial) {
        super(hasLiquidMaterial, hasContainer, weight);
        this.toxicMaterial = toxicMaterial;
        this.hasChemical = hasChemical;
        this.liquidAndToxicMaterial = liquidAndToxicMaterial;
    }

    public void loadLiquidAndToxicMaterial(int liquidAndToxicMaterial){
        if(this.liquidAndToxicMaterial + liquidAndToxicMaterial > this.getWeight()){
            System.err.println("Error: Not enough capacity to load " + liquidAndToxicMaterial + " liquid and toxic material.");
        } else {
            this.liquidAndToxicMaterial += liquidAndToxicMaterial;
            System.out.println(liquidAndToxicMaterial + " litre materials loaded.");
        }
    }

    public void unloadLiquidAndToxicMaterial(){
        if(this.liquidAndToxicMaterial == 0){
            System.err.println("No liquid and toxic material found to unload.");
        }else {
            this.liquidAndToxicMaterial = 0;
            System.out.println("Liquid and toxic material unloaded.");
        }
    }

    @Override
    public void loadLiquid(int fillLiquid) {
        super.loadLiquid(fillLiquid);
    }

    public int getLiquidAndToxicMaterial() {
        return liquidAndToxicMaterial;
    }

    public void setLiquidAndToxicMaterial(int liquidAndToxicMaterial) {
        this.liquidAndToxicMaterial = liquidAndToxicMaterial;
    }

    public String getToxicMaterial() {
        return toxicMaterial;
    }

    public void setToxicMaterial(String toxicMaterial) {
        this.toxicMaterial = toxicMaterial;
    }

    public boolean isHasChemical() {
        return hasChemical;
    }

    public void setHasChemical(boolean hasChemical) {
        this.hasChemical = hasChemical;
    }

    @Override
    public String getType() {
        return "Liquid Toxic Car";
    }

    @Override
    public String toString() {
        return "LiquidToxicRailroadCar{" +
                "id=" + getId() +
                " toxicMaterial='" + toxicMaterial + '\'' +
                ", hasChemical=" + hasChemical +
                ", liquidAndToxicMaterial=" + liquidAndToxicMaterial +
                '}';
    }
}
