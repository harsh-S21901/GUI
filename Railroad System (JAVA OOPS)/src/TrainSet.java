import Cars.RailroadCar;
import Enums.Stations;

import java.util.*;

public class TrainSet {
    private static int count = 0;
    private final int id;
    private Locomotive locomotive;

    private Set<RailroadCar> railroadCars;
    private List<Stations> route;

    private StationGraph stationGraph;
    private double distanceCovered;


    public TrainSet(Locomotive locomotive, Set<RailroadCar> railroadCars) {
        count += 1;
        this.id = count;
        this.locomotive = locomotive;
        this.route = getRoute();
        this.railroadCars = new HashSet<>();
        this.distanceCovered=0;
    }

    public void moveAlongRoute(StationGraph stationGraph) throws InterruptedException, RailroadHazard {
        List<Stations> route = stationGraph.findRoute(locomotive.getSourceRailwayStation(), locomotive.getDestinationRailwayStation());
        int index = 0;

        while (true) {
            Stations currentStation = route.get(index);
            locomotive.updateSpeed();
            System.out.println("Trainset " + getId() + " is at station " + currentStation + "/speed: " + locomotive.getSpeed() + " km/hr");
            // Wait 2 seconds at each station
            Thread.sleep(2000);
            index++;
            if (index >= route.size()) {
                // Wait 30 seconds at destination station
                System.out.println("Trainset " + getId() + " has reached its destination at " + currentStation + " and is waiting for 30 seconds");
                Thread.sleep(30000);
                // Generate new route and start return journey
                route = stationGraph.findRoute(locomotive.getSourceRailwayStation(), locomotive.getDestinationRailwayStation());
                Collections.reverse(route);
                index = 0;
            }
            // Check speed limit
            double speed = locomotive.getSpeed();
            if (speed > 200) {
                throw new RailroadHazard("Trainset " + getId() + " is exceeding the speed limit. Current speed: " + speed);
            }
        }
    }



    public void attachRailroadCar(RailroadCar car) {
        this.railroadCars.add(car);
        this.locomotive.attachRailroadCar(car);
    }

    public void detachRailroadCar(RailroadCar car) {
        this.railroadCars.remove(car.getId());
        this.locomotive.removeRailroadCar(car);
    }

    public int getId() {
        return id;
    }

    public Locomotive getLocomotive() {
        return locomotive;
    }

    public void setLocomotive(Locomotive locomotive) {
        this.locomotive = locomotive;
    }

    public List<Stations> getRoute() {
        return route;
    }

    public void setRoute(List<Stations> route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "TrainSet:" +
                "id:" + id +
                ", locomotive:" + locomotive + "\n" +
                ", Railroad Cars:" + locomotive.getAttachedCars() + "\n";
    }
}
