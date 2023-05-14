import Enums.Stations;

import java.util.*;

public class StationGraph {
    private final int numStations;
    private final Map<Stations, List<Stations>> connections;

    public StationGraph(int numStations) {
        this.numStations = numStations;
        this.connections = new HashMap<>();
        // Initialize the connections between stations
        for (Stations station : Stations.values()) {
            connections.put(station, new ArrayList<>());
        }
        // Add the connections between stations
        for (Stations station : Stations.values()) {
            List<Stations> adjacentStations = connections.get(station);
            int stationIndex = station.ordinal(); //for incrementing in enum

            // Add connections to adjacent stations
            if (stationIndex > 0) {
                adjacentStations.add(Stations.values()[stationIndex - 1]); // connect to previous station
            }
            if (stationIndex < numStations - 1) {
                adjacentStations.add(Stations.values()[stationIndex + 1]); // connect to next station
            }
        }
    }

    public List<Stations> findRoute(Stations source, Stations destination) {
        // Implement Dijkstra's algorithm to find the shortest path
        Map<Stations, Integer> distances = new HashMap<>();
        Map<Stations, Stations> previous = new HashMap<>();
        PriorityQueue<Stations> queue = new PriorityQueue<>(Comparator.comparingInt(key -> distances.get(key)));
        Set<Stations> visited = new HashSet<>();
        // Initialize distances and previous stations
        for (Stations station : Stations.values()) {
            distances.put(station, Integer.MAX_VALUE);
            previous.put(station, null);
        }
        distances.put(source, 0);
        // Add source station to the queue
        queue.offer(source);
        while (!queue.isEmpty()) {
            Stations current = queue.poll();
            // Stop if the destination has been reached
            if (current == destination) {
                break;
            }
            // Skip if the current station has already been visited
            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);
            // Update distances and previous stations for adjacent stations
            for (Stations adjacent : connections.get(current)) {
                int distance = distances.get(current) + 1; // assume equal distance for all connections
                if (distance < distances.get(adjacent)) {
                    distances.put(adjacent, distance);
                    previous.put(adjacent, current);
                    queue.offer(adjacent);
                }
            }
        }
        // Construct the route from source to destination
        LinkedList<Stations> route = new LinkedList<>();
        Stations current = destination;
        while (current != null) {
            route.addFirst(current);
            current = previous.get(current);
        }
        if (!route.isEmpty() && route.getFirst() == source) {
            return route;
        } else {
            throw new RuntimeException("No route found from " + source + " to " + destination);
        }
    }

    public int findDistance(Stations source, Stations destination) {
        List<Stations> route = findRoute(source, destination);
        return route.size() - 1; // the distance is the number of stations on the route minus 1
    }
}
