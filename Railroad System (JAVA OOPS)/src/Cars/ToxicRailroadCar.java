package Cars;

public class ToxicRailroadCar extends HeavyRailroadFreightCar{
    private String typeOfMaterial;
    private boolean hasSafetyMeasures;
    private int toxicMaterial;

    public ToxicRailroadCar(String typeOfMaterial, boolean hasSafetyMeasures, int weight) {
        super(weight);
        this.typeOfMaterial = typeOfMaterial;
        this.hasSafetyMeasures = hasSafetyMeasures;
        this.toxicMaterial =0;
    }

    public void loadToxicMaterial(int toxicMaterial){
        if(this.toxicMaterial + toxicMaterial > this.getWeight()){
            System.out.println("Error: Not enough capacity to load " + toxicMaterial + " toxic materials.");
        } else {
            this.toxicMaterial += toxicMaterial;
            System.out.println(toxicMaterial + " kg of toxic material loaded.");
        }
    }

    public void unloadToxicMaterial(){
        if(this.toxicMaterial == 0){
            System.err.println("No toxic material found to unload.");
        }else {
            this.toxicMaterial = 0;
            System.out.println("Toxic material unloaded.");
        }
    }

    public int getToxicMaterial() {
        return toxicMaterial;
    }

    public void setToxicMaterial(int toxicMaterial) {
        this.toxicMaterial = toxicMaterial;
    }

    public String getTypeOfMaterial() {
        return typeOfMaterial;
    }

    public void setTypeOfMaterial(String typeOfMaterial) {
        this.typeOfMaterial = typeOfMaterial;
    }

    public boolean isHasSafetyMeasures() {
        return hasSafetyMeasures;
    }

    @Override
    public boolean requiresElectricity() {
        return false;
    }

    public void setHasSafetyMeasures(boolean hasSafetyMeasures) {
        this.hasSafetyMeasures = hasSafetyMeasures;
    }

    @Override
    public String getType(){
        return "Toxic";
    }

    @Override
    public String toString() {
        return "ToxicRailroadCar{" +"id=" + getId() +
                " typeOfMaterial='" + typeOfMaterial + '\'' +
                ", hasSafetyMeasures=" + hasSafetyMeasures +
                ", toxicMaterial=" + toxicMaterial +
                '}';
    }
}
