package Cars;

public class GaseousRailroadCar extends BasicRailroadFreightCar{
    private boolean Gaseous;
    private boolean isDangerous;
    private double volumeOfGas;
    private final double maxVolume =1000;

    public GaseousRailroadCar(double maxVolume,boolean gaseous, boolean isDangerous, int weight) {
        super(weight);
        Gaseous = gaseous;
        this.isDangerous = isDangerous;
        this.volumeOfGas = 0;
    }

    public void loadGas(int volumeOfGas) {
        if (this.volumeOfGas + volumeOfGas > maxVolume) {
            System.out.println("Error: Not enough volume to load " + volumeOfGas + " gas.");
        } else {
            this.volumeOfGas += volumeOfGas;
            System.out.println(volumeOfGas + " litre gas loaded.");
        }
    }

    public void unloadGas(){
        if(this.volumeOfGas ==0){
            System.err.println("No gas found to unload");
        }else {
            this.volumeOfGas=0;
            System.out.println("Gas unloaded.");
        }
    }



    public double getMaxVolume() {
        return maxVolume;
    }

    public double getVolumeOfGas() {
        return volumeOfGas;
    }

    public void setVolumeOfGas(double volumeOfGas) {
        this.volumeOfGas = volumeOfGas;
    }

    public boolean isGaseous() {
        return Gaseous;
    }

    public void setGaseous(boolean gaseous) {
        Gaseous = gaseous;
    }

    public boolean isDangerous() {
        return isDangerous;
    }

    public void setDangerous(boolean dangerous) {
        isDangerous = dangerous;
    }

    @Override
    public boolean requiresElectricity() {
        return false;
    }

    @Override
    public String getType() {
        return "Gaseous";
    }

    @Override
    public String toString() {
        return "GaseousRailroadCar{" +
                "id=" + getId() +
                " , maxVolume=" + maxVolume+
                ", Gaseous=" + Gaseous +
                ", isDangerous=" + isDangerous +
                ", volumeOfGas=" + volumeOfGas +
                '}';
    }
}
