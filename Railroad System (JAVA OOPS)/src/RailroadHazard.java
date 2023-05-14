public class RailroadHazard extends Exception{
    private TrainSet trainSet;

    public RailroadHazard(String s) {

    }

    public TrainSet getTrainSet() {
        return trainSet;
    }

    @Override
    public String getMessage() {
        return "Warning: Trainset " + trainSet.getId()  + " has exceeded the maximum speed limit of 200km/h";
    }
}
