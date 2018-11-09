/*
 * cssc0861
 * Project: program3
 * Created By: Jack Bruce & Jacob Romio
 * Date Created: 11/9/18
 * Date Last Edited: 11/9/18
 * Description: The concrete implementation of a non-weighted, directed graph meeting all the requirements defined in
 * this assignment
 */

package edu.sdsu.cs.datastructures;

import java.util.List;
import java.util.NoSuchElementException;

public class DirectedGraph<V> implements IGraph<V> {

    public DirectedGraph() {

    }

    /**
     * Inserts a vertex with the specified name into the Graph if it
     * is not already present.
     *
     * @param vertexName The label to associate with the vertex
     */
    @Override
    public void add(Object vertexName) {

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
    public void connect(Object start, Object destination) {

    }

    /**
     * Resets the graph to an empty state.
     */
    @Override
    public void clear() {

    }

    /**
     * Reports if a vertex with the specified label is stored within
     * the graph.
     *
     * @param label The vertex name to find
     * @return true if within the graph, false if not.
     */
    @Override
    public boolean contains(Object label) {
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
    public void disconnect(Object start, Object destination) {

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
    public boolean isConnected(Object start, Object destination) {
        return false;
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
    public Iterable neighbors(Object vertexName) {
        return null;
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
    public void remove(Object vertexName) {

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
    public List shortestPath(Object start, Object destination) {
        return null;
    }

    /**
     * Reports the number of vertices in the Graph.
     *
     * @return a non-negative number.
     */
    @Override
    public int size() {
        return 0;
    }

    /**
     * Provides a collection of vertex names currently in the graph.
     *
     * @return The names of the vertices within the graph.
     */
    @Override
    public Iterable vertices() {
        return null;
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
    public IGraph connectedGraph(Object origin) {
        return null;
    }
}
