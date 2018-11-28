/*
 * Project: program3
 * Created By: Jack Bruce - cssc0861,  Jacob Romio - cssc0862
 * Date Created: 11/9/18
 * Date Last Edited: 11/28/18
 * Description: The concrete implementation of a non-weighted, directed graph meeting all the requirements defined in
 * this assignment
 */

package edu.sdsu.cs.datastructures;

import java.util.*;

public class DirectedGraph<V> implements IGraph<V> {

    private class Vertex {
        V label;
        List<V> neighbors;
        int degree;

        public Vertex(V label) {
            this.label = label;
            this.neighbors = new LinkedList<>();
        }

        public void addEdge(V destination) {
            if (this.neighbors.contains(destination))
                return;
            this.neighbors.add(destination);
        }

        public void removeEdge(V destination) {
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
        int vertexNX = 0;
        while (!label.equals(vertices.get(vertexNX).label))
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
        if (contains(vertexName))
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
        if (!(contains(start) && contains(destination)))
            throw new NoSuchElementException();
        int startNX = findVertexNX(start);
        if (vertices.get(startNX).neighbors.contains(destination))
            return;
        vertices.get(startNX).addEdge(destination);
    }

    /**
     * Resets the graph to an empty state.
     */
    @Override
    public void clear() {
        vertices.clear();
        size = 0;
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
        for (int NX = 0; NX < size; NX++) {
            if (vertices.get(NX).label.equals(label))
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
        if (!(contains(start) && contains(destination)))
            throw new NoSuchElementException();
        int startNX = findVertexNX(start);
        if (!vertices.get(startNX).neighbors.contains(destination))
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
        if (!(contains(start) && contains(destination)))
            throw new NoSuchElementException();
        List<V> connectionList = shortestPath(start, destination);
        return ((connectionList.contains(destination)) && connectionList.contains(start));
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
        if (!contains(vertexName))
            throw new NoSuchElementException();
        int vertexNX = findVertexNX(vertexName);
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
        if (!contains(vertexName))
            throw new NoSuchElementException();
        int vertexNX = findVertexNX(vertexName);
        for (int verticesNX = 0; verticesNX < size; verticesNX++) {
            for (int neighborNX = 0; neighborNX < vertices.get(verticesNX).neighbors.size(); neighborNX++) {
                if (vertexName.equals(vertices.get(verticesNX).neighbors.get(neighborNX)))
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
    public List<V> shortestPath(V start, V destination) {
        if (!(contains(start) && contains(destination)))
            throw new NoSuchElementException();

        Map<V, Vertex> unvisited = new TreeMap<>(); //<label, vertex>
        Map<V, Integer> distances = new TreeMap<>(); //<destination, cost>
        Map<V, V> breadcrumbs = new TreeMap<>(); //<to, from>
        ArrayList<Vertex> q = new ArrayList<>();
        Vertex startVertex = new Vertex(start); //default value should never be used

        // initialization
        for (Vertex vertex : vertices) {
            unvisited.put(vertex.label, vertex);
            if (vertex.label.equals(start)) {
                distances.put(vertex.label, 0);
                startVertex = vertex;
            } else
                distances.put(vertex.label, Integer.MAX_VALUE);
        }

        q.add(startVertex);
        while (!q.isEmpty()) {
            Vertex current = q.remove(q.size() - 1);

            if (!unvisited.containsKey(current.label)) {
                continue;
            }

            unvisited.remove(current.label);

            for (V neighbor : current.neighbors) {
                if (distances.get(neighbor) > 1) {
                    distances.put(neighbor, 1);
                    breadcrumbs.put(neighbor, current.label);
                }
                if (unvisited.containsKey(neighbor))
                    q.add(unvisited.get(neighbor));
            }
        }

        Stack<V> shortestPath = new Stack<>();
        V curr = destination;
        shortestPath.push(curr);
        while (!shortestPath.contains(start)) {
            if (!breadcrumbs.containsKey(curr)) {
                shortestPath.clear();
                break;
            }
            if (curr.equals(start)) break;
            shortestPath.push(breadcrumbs.get(curr));
            curr = breadcrumbs.get(curr);
        }

        List<V> sPathList = new LinkedList<>();
        while (!shortestPath.empty())
            sPathList.add(shortestPath.pop());
        return sPathList;
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
        if (!contains(origin))
            throw new NoSuchElementException();
        IGraph<V> connectedGraph = new DirectedGraph<>();
        int originNX = findVertexNX(origin);
        connectedGraph.add(vertices.get(originNX).label);
        for (int NX = 0; NX < vertices.size(); NX++) {
            if (isConnected(vertices.get(originNX).label, vertices.get(NX).label)) {
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
        for (Vertex vertex : vertices) {
            vertexStr += ++vertexNum + ". " + vertex.label + ", degree: " + vertex.degree + "\n";
            for (V neighbor : vertex.neighbors) {
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
        for (Vertex vertex : vertices) {
            for (V neighborLabel : vertex.neighbors) {
                connections.add(new Connection(vertex, neighborLabel));
            }
        }
        return connections;
    }

    private void calulateDegrees(List<Connection> connections) {
        for (Vertex vertex : vertices) {
            vertex.degree = vertex.neighbors.size(); //outgoing paths add degree
            for (Connection connection : connections) {
                if (connection.destination.equals(vertex.label)) { //incomming paths add degree
                    vertex.degree++;
                }
            }
        }
    }

}