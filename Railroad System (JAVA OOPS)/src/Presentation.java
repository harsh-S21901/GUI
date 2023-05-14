import Cars.*;
import Enums.Stations;

public class Presentation {
    public static void main(String[] args) {
        //Make locomotives
        Locomotive locomotive = new Locomotive(Stations.CHELMNO,Stations.WARSZAWA_ZACHODNIA,Stations.MALBORK);
        Locomotive locomotive1 = new Locomotive(Stations.BIALYSTOK,Stations.AUGUSTOW,Stations.BYDGOSZCZ_GLOWNA);
        Locomotive locomotive2 = new Locomotive(Stations.CHELM_KALISKI,Stations.CIECHOCINEK,Stations.DRAWSKO_POMORSKIE);
        Locomotive locomotive3 = new Locomotive(Stations.GLIWICE,Stations.GDANSK_GLOWNY,Stations.GLOGOW);
        Locomotive locomotive4 = new Locomotive(Stations.DRAWSKO_POMORSKIE,Stations.JAROCIN,Stations.KALISZ);

        //Make different types of railroadCars
        PassengerRailroadCar passengerRailroadCar = new PassengerRailroadCar(1000,true,1000);
        RailroadBaggageAndMailCar railroadBaggageAndMailCar = new RailroadBaggageAndMailCar(true,1000,1000);
        RailroadPostOffice railroadPostOffice = new RailroadPostOffice(true,true,1000,1000);
        RefrigeratedRailroadCar refrigeratedRailroadCar = new RefrigeratedRailroadCar(-12,-5,1000);
        RestaurantCar restaurantCar = new RestaurantCar(1000,true,1000);
        ToxicRailroadCar toxicRailroadCar = new ToxicRailroadCar("Radioactive",true,1000);
        ExplosiveRailroadCar explosiveRailroadCar = new ExplosiveRailroadCar("Dynamite",true,1000);
        GaseousRailroadCar gaseousRailroadCar = new GaseousRailroadCar(1000,true,true,1000);
        LiquidRailroadCar liquidRailroadCar = new LiquidRailroadCar(true,true,1000);
        LiquidToxicRailroadCar liquidToxicRailroadCar = new LiquidToxicRailroadCar(true,true,1000,"Radio active",true,1000);

        //Attach railroads to locomotive
        locomotive.attachRailroadCar(passengerRailroadCar);
        locomotive.attachRailroadCar(railroadBaggageAndMailCar);
        locomotive1.attachRailroadCar(railroadPostOffice);
        locomotive1.attachRailroadCar(refrigeratedRailroadCar);
        locomotive2.attachRailroadCar(restaurantCar);
        locomotive2.attachRailroadCar(toxicRailroadCar);
        locomotive3.attachRailroadCar(explosiveRailroadCar);
        locomotive3.attachRailroadCar(gaseousRailroadCar);
        locomotive4.attachRailroadCar(liquidRailroadCar);
        locomotive4.attachRailroadCar(liquidToxicRailroadCar);

        //Make trainsets
        TrainSet trainSet = new TrainSet(locomotive,locomotive.getRailroadCars());
        TrainSet trainSet1 = new TrainSet(locomotive1,locomotive1.getRailroadCars());
        TrainSet trainSet2 = new TrainSet(locomotive2,locomotive2.getRailroadCars());
        TrainSet trainSet3 = new TrainSet(locomotive3,locomotive3.getRailroadCars());
        TrainSet trainSet4 = new TrainSet(locomotive4,locomotive4.getRailroadCars());

        //load different type of goods based on railroad car
        passengerRailroadCar.boardPassenger(100);
        railroadBaggageAndMailCar.loadBaggage(100);
        railroadPostOffice.loadMails(100);
        refrigeratedRailroadCar.loadColdGoods(100);
        restaurantCar.loadDiners(100);
        toxicRailroadCar.loadToxicMaterial(100);
        explosiveRailroadCar.loadExplosive(100);
        gaseousRailroadCar.loadGas(100);
        liquidRailroadCar.loadLiquid(100);
        liquidToxicRailroadCar.loadLiquidAndToxicMaterial(100);

        //unload good on railroad car
        passengerRailroadCar.unloadPassenger();
        railroadBaggageAndMailCar.unloadBaggage();
        railroadPostOffice.unloadMail();
        refrigeratedRailroadCar.unloadColdGoods();
        restaurantCar.unloadDiners();
        toxicRailroadCar.unloadToxicMaterial();
        explosiveRailroadCar.unloadExplosives();
        gaseousRailroadCar.unloadGas();
        liquidRailroadCar.unloadLiquid();
        liquidToxicRailroadCar.unloadLiquid();

        //detach railroad car from train-sets or locomotives
        trainSet.getLocomotive().removeRailroadCar(passengerRailroadCar);
        trainSet1.getLocomotive().removeRailroadCar(railroadPostOffice);
        trainSet2.getLocomotive().removeRailroadCar(restaurantCar);
        trainSet3.getLocomotive().removeRailroadCar(explosiveRailroadCar);
        trainSet4.getLocomotive().removeRailroadCar(liquidRailroadCar);

        //Generate Station Graph
        StationGraph stationGraph = new StationGraph(100);

        //make trains move along the route
        try {
            trainSet.moveAlongRoute(stationGraph);
        } catch (InterruptedException | RailroadHazard e) {
            throw new RuntimeException(e);
        }
    }
}
