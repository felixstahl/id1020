package src.q5;

/**
 *              README
 *       Author: Felix Ståhl
 *       Extra lab - Question 1
 *       Based on "Algorithms, 4th Edition" by Robert Sedgewick & Kevin Wayne
 * Implementation of an Edge class that is used in the Directed Graph
 *
 */
public class Edge {
    // in a digraph tail would be 'from this node' and head would be 'to this node' (tail -edge-> head)
    final int tail;
    final int head;
    final double weight;    // weight of the edge as a double

    // constructor of an edge
    public Edge(int tail, int head, double weight) {
        this.tail = tail;
        this.head = head;
        this.weight = weight;
    }
}