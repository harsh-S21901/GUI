package Cars;

public class PassengerRailroadCar extends RailroadCar {
    private int numOfSeats;
    private boolean hasWifi;
    private int numOfPassenger;

    public PassengerRailroadCar(int numOfSeats, boolean hasWifi, int weight) {
        super(weight);
        this.numOfSeats = numOfSeats;
        this.hasWifi = hasWifi;
        this.numOfPassenger = 0;
    }

    public void boardPassenger(int numberOfPassenger) {
        if (this.numOfPassenger + numberOfPassenger > numOfSeats) {
            this.numOfPassenger = numOfSeats;
            System.out.println("Error: Not enough seats to load " + numberOfPassenger + " passengers.");
        } else
        {
            this.numOfPassenger += numberOfPassenger;
            System.out.println(numberOfPassenger + " passengers loaded.");
        }
    }

    public void unloadPassenger(){
        if(this.numOfPassenger == 0){
            System.err.println("No passengers found");
        }else {
            this.numOfPassenger = 0;
            System.out.println("Passengers unloaded");
        }
    }

    @Override
    public boolean requiresElectricity() {
        return true;
    }

    @Override
    public String getType() {
        return "Passenger";
    }

    public int getNumOfSeats() {
        return numOfSeats;
    }

    public void setNumOfSeats(int numOfSeats) {
        this.numOfSeats = numOfSeats;
    }

    public boolean isHasWifi() {
        return hasWifi;
    }

    public void setHasWifi(boolean hasWifi) {
        this.hasWifi = hasWifi;
    }

    public int getNumOfPassenger() {
        return numOfPassenger;
    }

    public void setNumOfPassenger(int numOfPassenger) {
        this.numOfPassenger = numOfPassenger;
    }

    @Override
    public String toString() {
        return "PassengerRailroadCar{" +
                "id=" + getId()+
                " , numOfPassengers=" + numOfPassenger +
                ", numOfSeats=" + numOfSeats +
                ", hasWifi=" + hasWifi +
                '}';
    }

}
