package Cars;

public class RailroadBaggageAndMailCar extends RailroadCar {
    private boolean hasLuggage;
    private int shipmentCapacity;
    private int numberOfBaggage;

    public RailroadBaggageAndMailCar(boolean hasLuggage, int shipmentCapacity, int weight) {
        super(weight);
        this.hasLuggage = hasLuggage;
        this.shipmentCapacity = shipmentCapacity;
        this.numberOfBaggage =0;
    }


    public void loadBaggage(int numberOfBaggage){
        if(this.numberOfBaggage + numberOfBaggage > shipmentCapacity) {
            System.out.println("Error: Not enough capacity to load " + numberOfBaggage + " baggage.");
        } else
        {
            this.numberOfBaggage += numberOfBaggage;
            System.out.println(numberOfBaggage + " baggage loaded.");
        }
    }

    public void unloadBaggage(){
        if(this.numberOfBaggage == 0){
            System.err.println("No Baggage found to unload.");
        }else {
            this.numberOfBaggage = 0;
            System.out.println("Baggage unloaded.");
        }
    }

    public boolean isHasLuggage() {
        return hasLuggage;
    }

    public void setHasLuggage(boolean hasLuggage) {
        this.hasLuggage = hasLuggage;
    }

    public int getShipmentCapacity() {
        return shipmentCapacity;
    }

    public void setShipmentCapacity(int shipmentCapacity) {
        this.shipmentCapacity = shipmentCapacity;
    }

    public int getNumberOfBaggage() {
        return numberOfBaggage;
    }

    public void setNumberOfBaggage(int numberOfBaggage) {
        this.numberOfBaggage = numberOfBaggage;
    }

    @Override
    public boolean requiresElectricity() {
        return false;
    }

    @Override
    public String getType() {
        return "Baggage and Mail";
    }

    @Override
    public String toString() {
        return "RailroadBaggageAndMailCar{" +
                "id=" + getId() +
                " hasLuggage=" + hasLuggage +
                ", shipmentCapacity=" + shipmentCapacity +
                ", numberOfBaggage=" + numberOfBaggage +
                '}';
    }
}
