import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Your implementations of various graph algorithms.
 *
 * @author Ashwini Iyer
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Perform breadth first search on the given graph, starting at the start
     * Vertex.  You will return a List of the vertices in the order that
     * you visited them.  Make sure to include the starting vertex at the
     * beginning of the list.
     *
     * When exploring a Vertex, make sure you explore in the order that the
     * adjacency list returns the neighbors to you.  Failure to do so may
     * cause you to lose points.
     *
     * The graph passed in may be directed or undirected, but never both.
     *
     * You may import/use {@code java.util.Queue}, {@code java.util.Set},
     * {@code java.util.Map}, {@code java.util.List}, and any classes
     * that implement the aforementioned interfaces.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex you are starting at
     * @param graph the Graph we are searching
     * @param <T> the data type representing the vertices in the graph.
     * @return a List of vertices in the order that you visited them
     */
    public static <T> List<Vertex<T>> breadthFirstSearch(Vertex<T> start,
            Graph<T> graph) {
        //check for errors
        if (start == null) {
            throw new IllegalArgumentException("start is null");
        }
        if (graph == null) {
            throw new IllegalArgumentException("graph is null");
        }
        Map<Vertex<T>,
                List<VertexDistancePair<T>>> adjList = graph.getAdjacencyList();
        if (!adjList.containsKey(start)) {
            throw new IllegalArgumentException("start is not in graph");
        }
        List<Vertex<T>> answer = new ArrayList<>();
        Set<Vertex<T>> visitedList = new HashSet<Vertex<T>>();
        Queue queue = new LinkedList();
        queue.add(start);
        visitedList.add(start);
        while (!queue.isEmpty()) {
            Vertex vertex = (Vertex) queue.remove();
            if (!visitedList.contains(vertex) || vertex == start) {
                visitedList.add(vertex);
                answer.add(vertex);
                for (int i = 0; i < adjList.get(vertex).size(); i++) {
                    Vertex temp = adjList.get(vertex).get(i).getVertex();
                    if (!visitedList.contains(temp)) {
                        queue.add(temp);
                    }
                }
            }
        }
        return answer;
    }

    /**
     * Perform depth first search on the given graph, starting at the start
     * Vertex.  You will return a List of the vertices in the order that
     * you visited them.  Make sure to include the starting vertex at the
     * beginning of the list.
     *
     * When exploring a Vertex, make sure you explore in the order that the
     * adjacency list returns the neighbors to you.  Failure to do so may
     * cause you to lose points.
     *
     * The graph passed in may be directed or undirected, but never both.
     *
     * You MUST implement this method recursively.
     * Do not use any data structure as a stack to avoid recursion.
     * Implementing it any other way WILL cause you to lose points!
     *
     * You may import/use {@code java.util.Set}, {@code java.util.Map},
     * {@code java.util.List}, and any classes that implement the
     * aforementioned interfaces.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex you are starting at
     * @param graph the Graph we are searching
     * @param <T> the data type representing the vertices in the graph.
     * @return a List of vertices in the order that you visited them
     */
    public static <T> List<Vertex<T>> depthFirstSearch(Vertex<T> start,
            Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("start is null");
        }
        if (graph == null) {
            throw new IllegalArgumentException("graph is null");
        }
        List<Vertex<T>> answer = new ArrayList<Vertex<T>>();
        Set<Vertex<T>> visitedList = new HashSet<Vertex<T>>();
        Map<Vertex<T>,
                List<VertexDistancePair<T>>> adjList = graph.getAdjacencyList();
        if (!adjList.containsKey(start)) {
            throw new IllegalArgumentException("start is not in graph");
        }
        visitedList.add(start);
        answer.add(start);
        return dfsRecHelper(start, adjList, visitedList, answer);
    }

    /**
     * Private recursive method that performs depth first search.
     * @param start The Vertex you are staring at.
     * @param adjList The adjacency list of the graph.
     * @param visitedList The visited list of the graph.
     * @param answer The list of vertices in the order we visited them
     * @param <T> the data type representing the vertices in the graph.
     * @return a List of vertices in the order that you visited them
     */
    private static <T> List<Vertex<T>> dfsRecHelper(Vertex<T> start,
                                      Map<Vertex<T>,
                                      List<VertexDistancePair<T>>> adjList,
                                      Set<Vertex<T>> visitedList,
                                      List<Vertex<T>> answer) {
        for (int i = 0; i < adjList.get(start).size(); i++) {
            Vertex next = adjList.get(start).get(i).getVertex();
            if (next == null) {
                return null;
            }
            if (next != null && !visitedList.contains(next)) {
                visitedList.add(next);
                answer.add(next);
                dfsRecHelper(next, adjList, visitedList, answer);
            }
        }
        return answer;
    }
    /**
     * Find the shortest distance between the start vertex and all other
     * vertices given a weighted graph where the edges only have positive
     * weights.
     *
     * Return a map of the shortest distances such that the key of each entry is
     * a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing infinity)
     * if no path exists. You may assume that going from a vertex to itself
     * has a distance of 0.
     *
     * There are guaranteed to be no negative edge weights in the graph.
     * The graph passed in may be directed or undirected, but never both.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Map}, and any class that implements the aforementioned
     * interface.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex you are starting at
     * @param graph the Graph we are searching
     * @param <T> the data type representing the vertices in the graph.
     * @return a map of the shortest distances from start to every other node
     *         in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
            Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("start is null");
        }
        if (graph == null) {
            throw new IllegalArgumentException("graph is null");
        }
        Map<Vertex<T>, Integer> finalMap = new HashMap<>();
        Set<Vertex<T>> visitedList = new HashSet<Vertex<T>>();
        PriorityQueue<VertexDistancePair<T>> pq = new PriorityQueue<>();
        Map<Vertex<T>,
                List<VertexDistancePair<T>>> adjList = graph.getAdjacencyList();
        if (!adjList.containsKey(start)) {
            throw new IllegalArgumentException("start is not in graph");
        }
        Set<Edge<T>> edges = graph.getEdgeList();
        for (Edge<T> e : edges) {
            if (e.getU() != null && !finalMap.containsKey(e.getU())) {
                finalMap.put(e.getU(), Integer.MAX_VALUE);
            }
            if (e.getV() != null && !finalMap.containsKey(e.getV())) {
                finalMap.put(e.getV(), Integer.MAX_VALUE);
            }
        }
        pq.add(new VertexDistancePair<T>(start, 0));
        while (!pq.isEmpty()) {
            VertexDistancePair<T> currentPair = pq.remove();
            if (!visitedList.contains(currentPair.getVertex())) {
                visitedList.add(currentPair.getVertex());
                int currentPairD = currentPair.getDistance();
                finalMap.put(currentPair.getVertex(), currentPairD);
                for (VertexDistancePair<T> vPair
                        : adjList.get(currentPair.getVertex())) {
                    Vertex<T> temp = vPair.getVertex();
                    int tempDist = vPair.getDistance()
                            + currentPair.getDistance();
                    if (tempDist < Integer.MAX_VALUE) {
                        VertexDistancePair<T> newPair
                                = new VertexDistancePair<T>(temp, tempDist);
                        pq.add(newPair);
                    }
                }
            }
        }
        return finalMap;
    }

    /**
     * Run Kruskal's algorithm on the given graph and return the minimum
     * spanning tree in the form of a set of Edges. If the graph is
     * disconnected, and therefore there is no valid MST, return null.
     *
     * You may assume that there will only be one valid MST that can be formed.
     * In addition, only an undirected graph will be passed in.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Set}, {@code java.util.Map} and any class from java.util
     * that implements the aforementioned interfaces.
     *
     * @throws IllegalArgumentException if graph is null
     * @param graph the Graph we are searching
     * @param <T> the data type representing the vertices in the graph.
     * @return the MST of the graph; null if no valid MST exists.
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("graph is null");
        }
        Set<Edge<T>> minSpanningTree = new HashSet<Edge<T>>();
        Set<Edge<T>> edges = graph.getEdgeList();
        PriorityQueue<Edge<T>> pq = new PriorityQueue<>();
        HashMap<Vertex<T>, DisjointSet> dSet = new HashMap<>();
        int numVertices = 0;
        for (Edge<T> e : edges) {
            pq.add(e);
            if (e.getU() != null && !dSet.containsKey(e.getU())) {
                dSet.put(e.getU(), new DisjointSet());
                numVertices++;
            }
            if (e.getV() != null && !dSet.containsKey(e.getV())) {
                dSet.put(e.getV(), new DisjointSet());
                numVertices++;
            }
        }
        while (minSpanningTree.size() < numVertices - 1) {
            if (pq.isEmpty()) {
                return null;
            }
            Edge<T> removedEdge = pq.remove();
            DisjointSet uSet = dSet.get(removedEdge.getU());
            DisjointSet vSet = dSet.get(removedEdge.getV());
            if (uSet != vSet) {
                minSpanningTree.add(removedEdge);
                if (vSet != null) {
                    uSet.union(vSet);
                }
            }
        }
        return minSpanningTree;
    }
}
