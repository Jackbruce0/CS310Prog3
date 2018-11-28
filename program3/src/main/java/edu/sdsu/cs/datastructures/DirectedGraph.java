/*
 * cssc0861
 * Project: program3
 * Created By: Jack Bruce & Jacob Romio
 * Date Created: 11/9/18
 * Date Last Edited: 11/27/18
 * Description: The concrete implementation of a non-weighted, directed graph meeting all the requirements defined in
 * this assignment
 */

package edu.sdsu.cs.datastructures;


import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class DirectedGraph<V> implements IGraph<V> {

    private class Vertex {
        V label;
        List<V> neighbors;
        int degree;

        public Vertex(V label) {
            this.label = label;
            this.neighbors = new LinkedList<>();
        }

        public void addEdge(V destination){
            if (this.neighbors.contains(destination))
                return;
            this.neighbors.add(destination);
        }

        public void removeEdge(V destination){
            if (!neighbors.contains(destination))
                return;
            neighbors.remove(destination);
        }
    }

    private List<Vertex> vertices;//add All vertices to this
    private int size;

    public DirectedGraph() {
        vertices = new LinkedList<>();
        size = 0;

    }

    private int findVertexNX(V label) {
        int vertexNX=0;
        while(!label.equals(vertices.get(vertexNX).label))
            vertexNX++;
        return vertexNX;
    }
    /**
     * Inserts a vertex with the specified name into the Graph if it
     * is not already present.
     *
     * @param vertexName The label to associate with the vertex
     */
    @Override
    public void add(V vertexName) {
        if(contains(vertexName))
            return;
        vertices.add(new Vertex(vertexName));
        size++;
    }

    /**
     * Adds a connection between the named vertices if one does not
     * yet exist.
     *
     * @param start       The first vertex for the edge
     * @param destination The second vertex
     * @throws NoSuchElementException if either vertex are not present in the graph
     */
    @Override
    public void connect(V start, V destination) {
        if(!(contains(start)&&contains(destination)))
            throw new NoSuchElementException();
        int startNX = findVertexNX(start);
        if(vertices.get(startNX).neighbors.contains(destination))
            return;
        vertices.get(startNX).addEdge(destination);

    }

    /**
     * Resets the graph to an empty state.
     */
    @Override
    public void clear() {
        vertices.clear();
        size=0;
    }

    /**
     * Reports if a vertex with the specified label is stored within
     * the graph.
     *
     * @param label The vertex name to find
     * @return true if within the graph, false if not.
     */
    @Override
    public boolean contains(V label) {
        for(int NX=0; NX<size; NX++){
            if(vertices.get(NX).label.equals(label))
                return true;
        }
        return false;
    }

    /**
     * Removes the specified edge, if it exists, from the Graph.
     *
     * @param start       The name of the origin vertex
     * @param destination The name of the terminal vertex
     * @throws NoSuchElementException if either vertex are not present in the graph
     */
    @Override
    public void disconnect(V start, V destination) {
        if(!(contains(start)&&contains(destination)))
            throw new NoSuchElementException();
        int startNX = findVertexNX(start);
        if(!vertices.get(startNX).neighbors.contains(destination))
            return;
        vertices.get(startNX).removeEdge(destination);
    }

    /**
     * Identifies if a path exists between the two vertices.
     * <p>
     * When the start and destination node names are the same, this
     * method shall only return true if there exists a self-edge on
     * the specified vertex.
     *
     * @param start       The initial Vertex
     * @param destination The terminal vertex
     * @return True if any path exists between them
     * @throws NoSuchElementException if either vertex are not present in the graph
     */
    @Override
    public boolean isConnected(V start, V destination) {//needs work
        if(!(contains(start)&&contains(destination)))
            throw new NoSuchElementException();
        List<V> connectionList = shortestPath(start, destination);
        return((connectionList.contains(destination))&&connectionList.contains(start));
    }

    /**
     * Provides a collection of vertex names directly connected
     * through a single outgoing edge to the target vertex.
     * <p>
     * Changes to the returned Iterable object (e.g., .remove())
     * shall NOT impact or change the graph.
     *
     * @param vertexName The target vertex
     * @return An iterable, possibly empty, containing all
     * neighboring vertices.
     * @throws NoSuchElementException if the vertex is not present in the graph
     */
    @Override
    public Iterable<V> neighbors(V vertexName) {
        if(!contains(vertexName))
            throw new NoSuchElementException();
        int vertexNX= findVertexNX(vertexName);
        return vertices.get(vertexNX).neighbors;
    }

    /**
     * Deletes all trace of the specified vertex from within the
     * graph.
     * <p>
     * This method deletes the vertex from the graph as well as every
     * edge using the specified vertex as a start (out) or
     * destination (in) vertex.
     *
     * @param vertexName The vertex name to remove from the graph
     * @throws NoSuchElementException if the origin vertex
     *                                is not present in this graph
     */
    @Override
    public void remove(V vertexName) {
        if(!contains(vertexName))
            throw new NoSuchElementException();
        int vertexNX = findVertexNX(vertexName);
        for(int verticesNX = 0; verticesNX<size; verticesNX++){
            for(int neighborNX = 0; neighborNX<vertices.get(verticesNX).neighbors.size(); neighborNX++){
                if(vertexName.equals(vertices.get(verticesNX).neighbors.get(neighborNX)))
                    vertices.get(verticesNX).neighbors.remove(neighborNX);
            }//loops through neighbors
        }//loops through vertices
        vertices.remove(vertexNX);
        size--;
    }

    /**
     * Returns one shortest path through the graph from the starting
     * vertex and ending in the destination vertex.
     *
     * @param start       The vertex from which to begin the search
     * @param destination The terminal vertex within the graph
     * @return A sequence of vertices to visit requiring the fewest
     * steps through the graph from its starting position
     * (at index 0 in the list) to its terminus at the list's end.
     * If no path exists between the nodes, this method
     * returns an empty list.
     * @throws NoSuchElementException if either vertex are not present in the graph
     */
    @Override
    public List<V> shortestPath(V start, V destination) {//THIS NEEDS TO BE REWORKED AS DIJKSTRA'S
        if(!(contains(start)&&contains(destination)))
            throw new NoSuchElementException();
        if(start.equals(destination)) {
            List<V> emptyList = new LinkedList<>();
            return emptyList;
        }
        List<List<V>> bigList=new LinkedList<>();//creates a list of lists
        bigList = shortestPathHelper(start, start, destination, bigList, null);
        PriorityQueue<Integer> p = new PriorityQueue<>();
        for(int listNX=0; listNX<bigList.size(); listNX++){
            p.add(bigList.get(listNX).size());
        }
        if(p.size()==0) {
            List<V> emptyList = new LinkedList<>();
            return emptyList;
        }
        int shortestSize = p.remove();
        int shortestNX=0;
        while(shortestSize!=bigList.get(shortestNX).size())
            shortestNX++;
        return bigList.get(shortestNX);
    }
    private List<List<V>> shortestPathHelper(V vertex, V start, V destination, List<List<V>> bigList, List<V> list){
        int vertexNX = findVertexNX(vertex);
        boolean visited = false;
        if(list==null) {
            list = new ArrayList<>();
            list.add(start);
        }
        for(int bigListNX=0;bigListNX<bigList.size();bigListNX++){
            if(bigList.get(bigListNX).contains(vertex))
                visited=true;
        }
        if(!list.contains(vertex)&&visited==false)//so you don't add start twice when a new list is made
            list.add(vertex);
        if(!list.contains(destination)&&visited==false) {
            for (int neighborNX = 0; neighborNX < vertices.get(vertexNX).neighbors.size(); neighborNX++) {
                if (vertices.get(vertexNX).neighbors.get(neighborNX).equals(destination)){
                    list.add(vertices.get(vertexNX).neighbors.get(neighborNX));
                    bigList.add(list);
                }
                else if(vertex.equals(start)){
                    bigList=shortestPathHelper(vertices.get(vertexNX).neighbors.get(neighborNX), start,
                            destination, bigList, null);
                }
                else{
                    bigList=shortestPathHelper(vertices.get(vertexNX).neighbors.get(neighborNX), start,
                            destination, bigList, list);
                }
            }
        }
        return bigList;
    }
    /**
     * Reports the number of vertices in the Graph.
     *
     * @return a non-negative number.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Provides a collection of vertex names currently in the graph.
     *
     * @return The names of the vertices within the graph.
     */
    @Override
    public Iterable<V> vertices() {
        List<V> labels = new LinkedList<>();

        for (Vertex vertex : vertices)
            labels.add(vertex.label);

        return labels;
    }

    /**
     * Produces a graph of only those vertices and edges reachable
     * from the origin vertex.
     *
     * @param origin The vertex to build the graph from
     * @return A new graph with only the Vertices and Edges
     * reachable from the parameter Vertex.
     * @throws NoSuchElementException if the origin vertex is not present in this graph
     */
    @Override
    public IGraph<V> connectedGraph(V origin) {
        if(!contains(origin))
            throw new NoSuchElementException();
        IGraph<V> connectedGraph = new DirectedGraph<>();
        int originNX = findVertexNX(origin);
        connectedGraph.add(vertices.get(originNX).label);
        for(int NX=0;NX<vertices.size();NX++){
            if(isConnected(vertices.get(originNX).label,vertices.get(NX).label)) {
                connectedGraph.add(vertices.get(NX).label);
                connectedGraph.connect(origin, vertices.get(NX).label);
            }
        }
        return connectedGraph;
    }

    @Override
    public String toString() {
        List<Connection> connections = getConnections();
        calulateDegrees(connections);
        String vertexStr = "Verticies: " + size + "\n" +
                "Connections: " + connections.size() + "\n\n";

        int vertexNum = 0;
        for (Vertex vertex: vertices) {
            vertexStr += ++vertexNum + ". " + vertex.label + ", degree: " + vertex.degree + "\n";
            for(V neighbor: vertex.neighbors) {
                vertexStr += "  >> " + neighbor + "\n";
            }
        }

        return vertexStr + "\n";
    }

    private class Connection {
        Vertex origin;
        V destination;

        public Connection(Vertex origin, V destination) {
            this.origin = origin;
            this.destination = destination;
        }

        @Override
        public String toString() {
            return origin.label + ", " + destination;
        }

    }

    private List<Connection> getConnections() {
        List<Connection> connections = new LinkedList<>();
        for (Vertex vertex: vertices) {
            for (V neighborLabel: vertex.neighbors) {
                connections.add(new Connection(vertex, neighborLabel));
            }
        }

        return connections;
    }

    private void calulateDegrees(List<Connection> connections) {
        for(Vertex vertex: vertices) {
            vertex.degree = vertex.neighbors.size(); //outgoing paths add degree
            for(Connection connection: connections) {
                if (connection.destination.equals(vertex.label)) { //incomming paths add degree
                    vertex.degree++;
                }
            }
        }
    }

}