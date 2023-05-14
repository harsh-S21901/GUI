import Cars.*;
import Enums.Stations;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        StationGraph stationGraph = new StationGraph(100);
        Set<TrainSet> trainSets1 = Main.generateTrainsets();
        boolean start = true;

        System.out.println("1 - Info");
        System.out.println("2 - Load goods");
        System.out.println("3 - Unload goods");
        System.out.println("4 - Remove Railroad Car");
        System.out.println("5 - Attach Railroad Car");
        System.out.println("6 - Show Stations");
        System.out.println("7 - Start the movement of train sets");
        System.out.println("8 - View train report");
        System.out.println("9 - Save train info");
        System.out.println("10 - Exit Program");
        while (start) {
            Scanner sc = new Scanner(System.in);
            int a = sc.nextInt();
            switch (a) {
                case 1 -> System.out.println(trainSets1);
                case 2 -> {
                    System.out.println("Select ID of an trainset");
                    for (TrainSet trainset : trainSets1) {
                        System.out.println("Trainset ID: " + trainset.getId());
                    }
                    int trainId = sc.nextInt();
                    for (TrainSet trainset : trainSets1) {
                        if (trainset.getId() == trainId) {
                            System.out.println(trainset);
                            System.out.println("Select an Railroad Car ID to load goods");
                            int railCarId = sc.nextInt();
                            RailroadCar railroadCar = trainset.getLocomotive().getAttachedCars().stream().filter(railroadCar1 -> railroadCar1.getId() == railCarId).findAny().get();
                            System.out.println(railroadCar);
                            if (railroadCar.getType().equals("Passenger")) {
                                PassengerRailroadCar passengerRailroadCar = (PassengerRailroadCar) railroadCar;
                                System.out.println("Enter number of passengers you want to board");
                                int numOfPassengersToBoard = sc.nextInt();
                                passengerRailroadCar.boardPassenger(numOfPassengersToBoard);
                            } else if (railroadCar.getType().equals("Baggage and Mail")) {
                                RailroadBaggageAndMailCar railroadBaggageAndMailCar = (RailroadBaggageAndMailCar) railroadCar;
                                System.out.println("Enter number of baggage to load");
                                int numOfBaggageToLoad = sc.nextInt();
                                railroadBaggageAndMailCar.loadBaggage(numOfBaggageToLoad);
                            } else if (railroadCar.getType().equals("Railroad Post Office Car")) {
                                RailroadPostOffice railroadPostOffice = (RailroadPostOffice) railroadCar;
                                System.out.println("Enter number of mails you want to board");
                                int numOfMailsToLoad = sc.nextInt();
                                railroadPostOffice.loadMails(numOfMailsToLoad);
                            } else if (railroadCar.getType().equals("Restaurant")) {
                                RestaurantCar restaurantCar = (RestaurantCar) railroadCar;
                                System.out.println("Enter number of diners you want to board");
                                int numOfDinersToLoad = sc.nextInt();
                                restaurantCar.loadDiners(numOfDinersToLoad);
                            } else if (railroadCar.getType().equals("Toxic")) {
                                ToxicRailroadCar toxicRailroadCar = (ToxicRailroadCar) railroadCar;
                                System.out.println("Enter number of toxic material you want to load");
                                int numOfToxicsToLoad = sc.nextInt();
                                toxicRailroadCar.loadToxicMaterial(numOfToxicsToLoad);
                            } else if (railroadCar.getType().equals("Explosive")) {
                                ExplosiveRailroadCar explosiveRailroadCar = (ExplosiveRailroadCar) railroadCar;
                                System.out.println("Enter number of explosives you want to load");
                                int numOfExplosivesToLoad = sc.nextInt();
                                explosiveRailroadCar.loadExplosive(numOfExplosivesToLoad);
                            } else if (railroadCar.getType().equals("Gaseous")) {
                                GaseousRailroadCar gaseousRailroadCar = (GaseousRailroadCar) railroadCar;
                                System.out.println("Enter amount of gas you want to load");
                                int numOfGasToLoad = sc.nextInt();
                                gaseousRailroadCar.loadGas(numOfGasToLoad);
                            } else if (railroadCar.getType().equals("Liquid")) {
                                LiquidRailroadCar liquidRailroadCar = (LiquidRailroadCar) railroadCar;
                                System.out.println("Enter amount of liquid you want to load");
                                int numOfLiquidToLoad = sc.nextInt();
                                liquidRailroadCar.loadLiquid(numOfLiquidToLoad);
                            } else if (railroadCar.getType().equals("Liquid Toxic Car")) {
                                LiquidToxicRailroadCar liquidToxicRailroadCar = (LiquidToxicRailroadCar) railroadCar;
                                System.out.println("Enter amount of liquid and toxic material you want to load");
                                int numOfMaterialToLoad = sc.nextInt();
                                liquidToxicRailroadCar.loadLiquidAndToxicMaterial(numOfMaterialToLoad);
                            } else if (railroadCar.getType().equals("Refrigerator")) {
                                RefrigeratedRailroadCar refrigeratedRailroadCar = (RefrigeratedRailroadCar) railroadCar;
                                System.out.println("Enter amount of cold goods you want to load");
                                int numOfFrozenToLoad = sc.nextInt();
                                refrigeratedRailroadCar.loadColdGoods(numOfFrozenToLoad);
                            }
                        }
                    }
                }
                case 3 -> {
                    System.out.println("Enter ID of Railroad car you want to unload");
                    System.out.println("Select ID of an trainset");
                    for (TrainSet trainset : trainSets1) {
                        System.out.println("Trainset ID: " + trainset.getId());
                    }
                    int trainId1 = sc.nextInt();
                    for (TrainSet trainsets : trainSets1) {
                        if (trainsets.getId() == trainId1) {
                            System.out.println(trainsets);
                            System.out.println("Select an Railroad Car ID to unload goods");
                            int railCarId = sc.nextInt();
                            RailroadCar railroadCar = trainsets.getLocomotive().getAttachedCars().stream().filter(railroadCar1 -> railroadCar1.getId() == railCarId).findAny().get();
                            System.out.println(railroadCar);
                            if (railroadCar.getType().equals("Passenger")) {
                                PassengerRailroadCar passengerRailroadCar = (PassengerRailroadCar) railroadCar;
                                System.out.println("type yes to unload passengers");
                                sc.next("yes");
                                passengerRailroadCar.unloadPassenger();
                            } else if (railroadCar.getType().equals("Baggage and Mail")) {
                                RailroadBaggageAndMailCar railroadBaggageAndMailCar = (RailroadBaggageAndMailCar) railroadCar;
                                System.out.println("type yes to unload baggage");
                                sc.next("yes");
                                railroadBaggageAndMailCar.unloadBaggage();
                            } else if (railroadCar.getType().equals("Railroad Post Office Car")) {
                                RailroadPostOffice railroadPostOffice = (RailroadPostOffice) railroadCar;
                                System.out.println("type yes to unload mails");
                                sc.next("yes");
                                railroadPostOffice.unloadMail();
                            } else if (railroadCar.getType().equals("Restaurant")) {
                                RestaurantCar restaurantCar = (RestaurantCar) railroadCar;
                                System.out.println("type yes to unload diners");
                                sc.next("yes");
                                restaurantCar.unloadDiners();
                            } else if (railroadCar.getType().equals("Toxic")) {
                                ToxicRailroadCar toxicRailroadCar = (ToxicRailroadCar) railroadCar;
                                System.out.println("type yes to unload toxic material");
                                sc.next("yes");
                                toxicRailroadCar.unloadToxicMaterial();
                            } else if (railroadCar.getType().equals("Explosive")) {
                                ExplosiveRailroadCar explosiveRailroadCar = (ExplosiveRailroadCar) railroadCar;
                                System.out.println("type yes to unload explosive material");
                                sc.next("yes");
                                explosiveRailroadCar.unloadExplosives();
                            } else if (railroadCar.getType().equals("Gaseous")) {
                                GaseousRailroadCar gaseousRailroadCar = (GaseousRailroadCar) railroadCar;
                                System.out.println("type yes to unload gaseous material");
                                sc.next("yes");
                                gaseousRailroadCar.unloadGas();
                            } else if (railroadCar.getType().equals("Liquid")) {
                                LiquidRailroadCar liquidRailroadCar = (LiquidRailroadCar) railroadCar;
                                System.out.println("type yes to unload liquid");
                                sc.next("yes");
                                liquidRailroadCar.unloadLiquid();
                            } else if (railroadCar.getType().equals("Liquid Toxic Car")) {
                                LiquidToxicRailroadCar liquidToxicRailroadCar = (LiquidToxicRailroadCar) railroadCar;
                                System.out.println("type yes to unload passengers");
                                sc.next("yes");
                                liquidToxicRailroadCar.unloadLiquid();
                            } else if (railroadCar.getType().equals("Refrigerator")) {
                                RefrigeratedRailroadCar refrigeratedRailroadCar = (RefrigeratedRailroadCar) railroadCar;
                                System.out.println("type yes to unload passengers");
                                sc.next("yes");
                                refrigeratedRailroadCar.unloadColdGoods();
                            }
                        }
                    }
                }
                case 4 -> {
                    System.out.println("Enter ID of Trainset");
                    for (TrainSet trainset : trainSets1) {
                        System.out.println("Trainset ID: " + trainset.getId());
                    }
                    int trainId2 = sc.nextInt();
                    for (TrainSet trainsets : trainSets1) {
                        if (trainsets.getId() == trainId2) {
                            System.out.println(trainsets);
                            System.out.println("Select an Railroad Car ID to remove");
                            int railCarId = sc.nextInt();
                            RailroadCar railroadCar = trainsets.getLocomotive().getAttachedCars().stream().filter(railroadCar1 -> railroadCar1.getId() == railCarId).findFirst().get();
                            trainsets.detachRailroadCar(railroadCar);
                        } else System.out.println("Railroad car does not exist");
                    }
                }
                case 5 -> {
                    System.out.println("Enter ID of Trainset");
                    for (TrainSet trainset : trainSets1) {
                        System.out.println("Trainset ID: " + trainset.getId());
                    }
                    int trainId3 = sc.nextInt();
                    for (TrainSet trainset : trainSets1) {
                        if (trainset.getId() == trainId3) {
                            System.out.println(trainset);
                            System.out.println("Enter a type of Railroad car to attach\n 1 - Passenger \n 2 - Baggage and Mail \n 3 - Post office \n 4 - Refrigerator \n 5 - Restaurant \n 6 - Toxic \n 7 - Explosive\n 8 - Gaseous \n 9 - Liquid\n 10 - Liquid and Toxic");
                            int choice = sc.nextInt();
                            if (choice == 1) {
                                System.out.println("Enter number of seats:");
                                int numOfSeats = sc.nextInt();
                                System.out.println("Enter weight of the car");
                                int weight = sc.nextInt();
                                PassengerRailroadCar passengerRailroadCar = new PassengerRailroadCar(numOfSeats, true, weight);
                                trainset.getLocomotive().attachRailroadCar(passengerRailroadCar);
                            } else if (choice == 2) {
                                System.out.println("Enter shipment capacity");
                                int shipmentCap = sc.nextInt();
                                RailroadBaggageAndMailCar baggageAndMailCar = new RailroadBaggageAndMailCar(true, shipmentCap, shipmentCap);
                                trainset.getLocomotive().attachRailroadCar(baggageAndMailCar);
                            } else if (choice == 3) {
                                System.out.println("Enter number of mail boxes");
                                int mailBox = sc.nextInt();
                                RailroadPostOffice railroadPostOffice = new RailroadPostOffice(true, true, mailBox, mailBox);
                                trainset.getLocomotive().attachRailroadCar(railroadPostOffice);
                            } else if (choice == 4) {
                                System.out.println("Enter minimum temperature of the car");
                                double minTemp = sc.nextDouble();
                                System.out.println("Enter maximum temperature of the car");
                                double maxTemp = sc.nextDouble();
                                System.out.println("Enter weight of the car");
                                int postOfficeWeight = sc.nextInt();
                                RefrigeratedRailroadCar refrigeratedRailroadCar = new RefrigeratedRailroadCar(minTemp, maxTemp, postOfficeWeight);
                                trainset.getLocomotive().attachRailroadCar(refrigeratedRailroadCar);
                            } else if (choice == 5) {
                                System.out.println("Enter number of tables:");
                                int tables = sc.nextInt();
                                RestaurantCar restaurantCar = new RestaurantCar(tables, true, tables);
                                trainset.getLocomotive().attachRailroadCar(restaurantCar);
                            } else if (choice == 6) {
                                System.out.println("Enter type of material(for eg. Radio active):");
                                String material = sc.next();
                                System.out.println("Enter weight of the car");
                                int toxicWeight = sc.nextInt();
                                ToxicRailroadCar toxicRailroadCar = new ToxicRailroadCar(material, true, toxicWeight);
                                trainset.getLocomotive().attachRailroadCar(toxicRailroadCar);
                            } else if (choice == 7) {
                                System.out.println("Enter type of explosive(for eg. Dynamite):");
                                String explosive = sc.next();
                                System.out.println("Enter weight of the car");
                                int explosiveWeight = sc.nextInt();
                                ExplosiveRailroadCar explosiveRailroadCar = new ExplosiveRailroadCar(explosive, true, explosiveWeight);
                                trainset.getLocomotive().attachRailroadCar(explosiveRailroadCar);
                            } else if (choice == 8) {
                                System.out.println("Enter volume of Gas to load");
                                int gasVolume = sc.nextInt();
                                GaseousRailroadCar gaseousRailroadCar = new GaseousRailroadCar(gasVolume, true, true, gasVolume);
                                trainset.getLocomotive().attachRailroadCar(gaseousRailroadCar);
                            } else if (choice == 9) {
                                System.out.println("Enter weight of the car");
                                int liquidWeight = sc.nextInt();
                                LiquidRailroadCar liquidRailroadCar = new LiquidRailroadCar(true, true, liquidWeight);
                                trainset.getLocomotive().attachRailroadCar(liquidRailroadCar);
                            } else if (choice == 10) {
                                System.out.println("Enter type of toxic material(for eg. Radio active");
                                String toxic = sc.next();
                                System.out.println("Enter weight of the car");
                                int liquidToxicWeight = sc.nextInt();
                                LiquidToxicRailroadCar liquidToxicRailroadCar = new LiquidToxicRailroadCar(true, true, liquidToxicWeight, toxic, true, liquidToxicWeight);
                                trainset.getLocomotive().attachRailroadCar(liquidToxicRailroadCar);
                            }
                        } else System.out.println("Railroad car does not exist");
                    }
                }
                case 6 -> {
                    System.out.println("Stations:");
                    for (Stations stations : Stations.values()) {
                        System.out.println(stations);
                    }
                }
                case 7 -> {
                    Thread[] trainThreads = new Thread[trainSets1.size()];
                    int i = 0;
                    for (TrainSet trainSet : trainSets1) {
                        Thread trainThread = new Thread(() -> {
                            try {
                                trainSet.moveAlongRoute(stationGraph);
                            } catch (InterruptedException | RailroadHazard e) {
//                                e.printStackTrace();
                            }
                        });
                        trainThreads[i++] = trainThread;
                        trainThread.start();
                    }
                    while (true) {
                        String input = sc.nextLine();
                        if (input.equals("w")) {
                            for (Thread trainThread : trainThreads) {
                                trainThread.interrupt();
                            }
                            System.out.println("Trains stopped");
                            break;
                        }
                    }
                }
                case 8 -> {
                    System.out.println("Enter ID of an trainset");
                    int trainsetID = sc.nextInt();
                    for (TrainSet trainset : trainSets1) {
                        if (trainsetID == trainset.getId()) {
                            System.out.println(trainset);
                            System.out.println(trainset.getLocomotive().getRailroadCars());
                            System.out.println("Route= " + stationGraph.findRoute(trainset.getLocomotive().getSourceRailwayStation(), trainset.getLocomotive().getDestinationRailwayStation()).toString());
                            System.out.println("Distance= " + stationGraph.findDistance(trainset.getLocomotive().getSourceRailwayStation(), trainset.getLocomotive().getDestinationRailwayStation()));
                        }
                    }
                }
                case 9 -> {
                    StringBuilder sb = new StringBuilder();
                    List<TrainSet> sortedTrainSets = new ArrayList<>(trainSets1);
                    sortedTrainSets.sort((ts1, ts2) -> Integer.compare(stationGraph.findDistance(ts2.getLocomotive().getSourceRailwayStation(), ts2.getLocomotive().getDestinationRailwayStation()), stationGraph.findDistance(ts1.getLocomotive().getSourceRailwayStation(), ts1.getLocomotive().getDestinationRailwayStation())));
                    for (TrainSet trainset : sortedTrainSets) {
                        sb.append("Trainset ID: ").append(trainset.getId())
                                .append(", Route length: ").append(stationGraph.findDistance(trainset.getLocomotive().getSourceRailwayStation(), trainset.getLocomotive().getDestinationRailwayStation()))
                                .append("\n");
                        List<RailroadCar> railroadCars = new ArrayList<>(trainset.getLocomotive().getRailroadCars());
                        railroadCars.sort(Comparator.comparingInt(RailroadCar::getWeight));
                        for (RailroadCar railroadCar : railroadCars) {
                            sb.append("ID: ").append(railroadCar.getId())
                                    .append(", Weight: ").append(railroadCar.getWeight())
                                    .append(", Type: ").append(railroadCar.getType())
                                    .append("\n");
                        }
                    }
                    try {
                        PrintWriter cleaner = new PrintWriter("AppState.txt");
                        cleaner.print("");
                        cleaner.close();
                        BufferedWriter fileWriter = new BufferedWriter(new FileWriter("AppState.txt", true));
                        fileWriter.write(String.valueOf(sb));
                        fileWriter.close();
                    } catch (IOException e) {
                        System.out.println("An error occurred while saving the information to file: " + e.getMessage());
                    }
                    System.out.println("Train info saved :)");
                }
                case 10 -> start = false;
            }
        }
    }

    public static Set<RailroadCar> generateRailroadCars(int numCars) {
        Set<RailroadCar> railroadCars = new HashSet<>();
        for (int i = 1; i <= numCars; i++) {
            int carType = new Random().nextInt(10); // 10 types of railroad cars
            if (carType == 0) {
                railroadCars.add(new PassengerRailroadCar(500, true, 500));
            } else if (carType == 1) {
                railroadCars.add(new RailroadBaggageAndMailCar(true, 600, 600));
            } else if (carType == 2) {
                railroadCars.add(new RestaurantCar(700, true, 700));
            } else if (carType == 3) {
                railroadCars.add(new LiquidRailroadCar(true, true, 800));
            } else if (carType == 4) {
                railroadCars.add(new RailroadPostOffice(true, true, 825, 825));
            } else if (carType == 5) {
                railroadCars.add(new ExplosiveRailroadCar("dynamite", true, 850));
            } else if (carType == 6) {
                railroadCars.add(new LiquidToxicRailroadCar(true, true, 1000, "Radio active", true, 1000));
            } else if (carType == 7) {
                railroadCars.add(new RefrigeratedRailroadCar(-18, -5, 875));
            } else if (carType == 8) {
                railroadCars.add(new GaseousRailroadCar(1000, true, true, 875));
            } else {
                railroadCars.add(new ToxicRailroadCar("Radio active", true, 900));
            }
        }
        return railroadCars;
    }

    //To make sure source and destination are not the same
    public static Stations[] getRandomStation() {
        List<Stations> stations = Arrays.asList(Stations.values());
        Collections.shuffle(stations);
        Stations[] result = new Stations[3];
        int repeatIndex = new Random().nextInt(3);
        for (int i = 0; i < 3; i++) {
            if (i == repeatIndex)
                result[i] = result[0];
            result[i] = stations.get(i);
        }
        return result;
    }

    public static Set<TrainSet> generateTrainsets() {
        Set<TrainSet> trainsets = new HashSet<>();
        for (int i = 1; i <= 25; i++) {
            var station = getRandomStation();
            Locomotive locomotive = new Locomotive(station[0], station[1], station[2]);
            Set<RailroadCar> railroadCars = generateRailroadCars(50);
            Iterator<RailroadCar> railroadCarIterator = railroadCars.iterator();
            while (railroadCarIterator.hasNext()) {
                RailroadCar railroadCar = railroadCarIterator.next();
                locomotive.attachRailroadCar(railroadCar);
                railroadCarIterator.remove();
                if (locomotive.getAttachedCars().size() >= 10) {
                    break;
                }
            }
            TrainSet trainset = new TrainSet(locomotive, railroadCars);
            trainsets.add(trainset);
            System.out.println(trainsets.size());
        }
        return trainsets;
    }
}