import Cars.RailroadCar;
import Enums.Stations;

import java.util.*;

public class Locomotive {
    private Stations homeRailwayStation;
    private Stations sourceRailwayStation;
    private Stations destinationRailwayStation;

    private static int count =0;
    private final int id;
    private double speed;
    private int loadedWeight;
    private final int MAX_WEIGHT= 15000;
    private final int MAX_ELECTRIC_GRID_RAILROAD_CAR = 5;
    private final int MAX_NON_ELECTRIC_GRID_RAILROAD_CAR = 5;
    private final int MAX_RAILROAD_CAR = MAX_ELECTRIC_GRID_RAILROAD_CAR + MAX_NON_ELECTRIC_GRID_RAILROAD_CAR;

    private final Set<RailroadCar> railroadCars = new HashSet<>();
    private final Set<Locomotive> locomotives = new HashSet<>();

    public Locomotive(Stations homeRailwayStation, Stations sourceRailwayStation, Stations destinationRailwayStation) {
        this.homeRailwayStation = homeRailwayStation;
        this.sourceRailwayStation = sourceRailwayStation;
        this.destinationRailwayStation = destinationRailwayStation;
        count+=1;
        this.id =count;
        this.speed=100.0;
        this.loadedWeight = 0;
    }

    public Set<RailroadCar> getAttachedCars() {
        return railroadCars;
    }

    public boolean isConnected(RailroadCar car){
        if(railroadCars.contains(car)){
            System.out.println(car.getType());
            return true;
        } else
            return false;
    }

    public void removeRailroadCar(RailroadCar car) {
        if(!railroadCars.contains(car)){
            System.err.println("Given car is not connected to this locomotive");
        }
        if (railroadCars.size() == 0) {
            System.err.println("No RailroadCar found");
        }
        railroadCars.remove(car);
        System.out.println("RailroadCar "+ car.getType() + " is removed.");
    }

    public void attachRailroadCar(RailroadCar car) {
        //Check if car is already attached
        if(locomotives.stream().anyMatch(x -> x.railroadCars.contains(car))){
            System.err.println("Railroad car id:" +car.getId()+" already attached");
        }
        //Add car logic
        if (railroadCars.size() < MAX_RAILROAD_CAR && this.loadedWeight < this.MAX_WEIGHT){
            //If Electric car
            if (car.requiresElectricity()) {
                if (railroadCars.stream().filter(RailroadCar::requiresElectricity).count() < getMAX_ELECTRIC_GRID_RAILROAD_CAR()) {
                    railroadCars.add(car);
                    this.loadedWeight += car.getWeight();
                    System.out.println("Attached " + car.getType());
                } else {
                    System.err.println("Max number of Electric car Reached");
                }
            } else {
                //Non-electric car
                if(railroadCars.stream().filter(x -> !x.requiresElectricity()).count() < MAX_NON_ELECTRIC_GRID_RAILROAD_CAR) {
                    railroadCars.add(car);
                    this.loadedWeight += car.getWeight();
                    System.out.println("Attached " + car.getType());
                }
                else {
                    System.err.println("Max number of Non-Electric car Reached");
                }
            }
        }
        else {
            if (this.loadedWeight >= this.MAX_WEIGHT) {
                System.err.println("Locomotive reached max weight");
            } else {
                System.err.println("Maximum number of Cars attached to Locomotive");
            }
        }
    }
    public void updateSpeed() {
        double variation = 0.03 * this.speed; // 3% variation
        double delta = (new Random().nextDouble() * 2 - 1) * variation;
        this.speed += delta;
    }

    public Stations getHomeRailwayStation() {
        return homeRailwayStation;
    }

    public void setHomeRailwayStation(Stations homeRailwayStation) {
        this.homeRailwayStation = homeRailwayStation;
    }

    public Stations getSourceRailwayStation() {
        return sourceRailwayStation;
    }

    public void setSourceRailwayStation(Stations sourceRailwayStation) {
        this.sourceRailwayStation = sourceRailwayStation;
    }

    public Stations getDestinationRailwayStation() {
        return destinationRailwayStation;
    }

    public void setDestinationRailwayStation(Stations destinationRailwayStation) {
        this.destinationRailwayStation = destinationRailwayStation;
    }

    public int getId() {
        return id;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getMAX_RAILROAD_CAR() {
        return MAX_RAILROAD_CAR;
    }

    public int getMAX_WEIGHT() {
        return MAX_WEIGHT;
    }

    public int getMAX_ELECTRIC_GRID_RAILROAD_CAR() {
        return MAX_ELECTRIC_GRID_RAILROAD_CAR;
    }

    public Set<RailroadCar> getRailroadCars() {
        return railroadCars;
    }

    @Override
    public String toString() {
        return "Locomotive{" +
                "homeRailwayStation=" + homeRailwayStation +
                ", sourceRailwayStation=" + sourceRailwayStation +
                ", destinationRailwayStation=" + destinationRailwayStation +
                ", id=" + getId() ;
    }
}