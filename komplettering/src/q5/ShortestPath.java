package q5;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *              README
 *       Author: Felix Ståhl
 *       Extra lab - Question 1
 *       Based on "Algorithms, 4th Edition" by Robert Sedgewick & Kevin Wayne
 * Implementation of a dijkstras algorithm computing a shortest path tree from a single source vertex
 *
 * It's a sparse graph, therefore i implemented an adjacency list (and not a matrix) using a Bag.
 * The program can either find a shortest path from A -> B, or A -> B -> C.
 *
 * Time complexity of dijkstra from A -> B :                        O(E * log(V))
 * Time complexity of dijkstra from A -> B -> C: 2 * (E * log(V)) = O(E * log(V))
 *
 * It will first build a SPT from Source vertex (A) and then create a SPT from the middle vertex (B).
 * This is because it will go from source to detour node, and from there find the shortest node to the destination.
 */
public class ShortestPath {

    private static final String FILE_NAME = "C:\\Users\\mr_fe\\Desktop\\Algo\\Algo2\\nya\\komplettering\\src\\q5\\NYC.txt";
    private static final String TEST_FILE_NAME = "C:\\Users\\mr_fe\\Desktop\\Algo\\Algo2\\nya\\komplettering\\src\\q5\\TestNYC.txt";
    private static final int NUM_VERTICES = 264347;
    private static final int NUM_EDGES = 733846;

    private double[] distTo;        // contains distance to each node from the source
    private Edge[] edgeTo;          // contains from which previous node you got to current node
    private Queue<Double> queue;    // (min binary heap) priority queue the nodes in dijkstras algorithm

    /**
     *  intelliJ, edit configurations -> skriv följande i program argument...
     *  startnod mellannod destinationsnod (1 2 13 is good)
     *  write 'test' instead for test scenario to run.
     */
    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("test")) {
            runTest();
        } else if (args.length == 3) {
            int source = Integer.parseInt(args[0]);
            int detour = Integer.parseInt(args[1]);
            int destination = Integer.parseInt(args[2]);
            File file = new File(FILE_NAME);
            Digraph graph = createGraphFromTxt(file, NUM_VERTICES);  // create the graph

            //System.out.println("starting shortest path tree from source");
            ShortestPath mainRoute = new ShortestPath(graph, source);        // build shortest path tree from source

            //System.out.println("starting shortest path tree from detour");
            ShortestPath detourRoute = new ShortestPath(graph, detour); // build shortest path tree from detour

            StringBuilder builder = new StringBuilder();
            if (mainRoute.hasPathTo(detour) && detourRoute.hasPathTo(destination)) {
                builder.append("Path from " + source + " to " + detour + ": ");
                for (Edge edge : mainRoute.pathTo(detour)) {
                    builder.append(edge.tail + "->" + edge.head + " ");
                }
                builder.append("\nPath from " + detour + " to " + destination + ": ");
                for (Edge edge : detourRoute.pathTo(destination)) {
                    builder.append(edge.tail + "->" + edge.head + " ");
                }

            } else {
                builder.append("No route found");
            }
            System.out.println(builder.toString());
        } else if (args.length == 2) {                  // skip the detour
            int source = Integer.parseInt(args[0]);
            int destination = Integer.parseInt(args[1]);
            File file = new File(FILE_NAME);
            Digraph graph = createGraphFromTxt(file, NUM_VERTICES);

            ShortestPath main = new ShortestPath(graph, source);
            StringBuilder builder = new StringBuilder();
            if (main.hasPathTo(destination)) {
                builder.append("Path from " + source + " to " + destination + ": ");
                for (Edge edge : main.pathTo(destination)) {
                    builder.append(edge.tail + "->" + edge.head + " ");
                }
            } else {
                builder.append("No route found");
            }
            System.out.println(builder.toString());
        }
    }

    private static Digraph createGraphFromTxt(File file, int numVertices) {
        //System.out.println("start creating graph");
        Digraph graph = new Digraph(numVertices);
        try {
            Util.readLinesFromFile(file, (line) -> {
                String[] words = line.split(" ");
                if (words.length == 3) {
                    //System.out.println(words[0]);
                    graph.addEdge(new Edge(Integer.parseInt(words[0]), Integer.parseInt(words[1]), Double.parseDouble(words[2])));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println("done creating graph");
        return graph;
    }
                                                        // dijkstra, computes a shortest path tree from source node
    public ShortestPath(Digraph graph, int s) {         // initially set distances to all nodes to infinity
        //System.out.println("starting shortest path");   // relax edges each iteration, and always go to the node with
        distTo = new double[graph.vertices()];          // the shortest distance from the source node
        edgeTo = new Edge[graph.vertices()];

        for (int i = 0; i < graph.vertices(); i++) {
            distTo[i] = Double.POSITIVE_INFINITY;       // set all distances to positive infinity
        }
        distTo[s] = 0.0;    // source distance is 0

        queue = new Queue<Double>(graph.vertices());
        queue.insert(s, distTo[s]);
        while (!queue.isEmpty()) {                  // traverse all nodes
            //System.out.println("here");
            int min = queue.delMin();
            for (Edge e : graph.adjacents(min)) {
              //  System.out.println("here2");
                relax(e);
            }
        }
        //System.out.println("done shortest path");
    }

    // every edge is relaxed exactly once. however, as multiple edges can be directed towards a node, the distance to
    // a node might be lowered by e.g. the second edge pointing towards the node. When this happens the distTo[] for
    // that node is changed to the lower distance value, and the edgeTo[] is also change to the node that
    // had the 'shorter' route (if the edges represent distance, as it does now)
    private void relax(Edge e) {
        int tail = e.tail;
        int head = e.head;
        if (distTo[head] > distTo[tail] + e.weight) {   // if dist to next node is bigger than dist to tail+weight ...
            distTo[head] = distTo[tail] + e.weight;     // ... if NOT, move on
            edgeTo[head] = e;
            if (queue.contains(head)) {                 // when a node is found at lower cost path
                queue.decreaseKey(head, distTo[head]);  // change distance
            } else {
                queue.insert(head, distTo[head]);       // otherwise, insert head node to queue
            }                                           // so it will be traversed later
        }
    }

    // checks if there is a bath to node v
    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    // return the way from V, back to the source as a stack. Print each pop & it will be printed source -> destination
    public Stack<Edge> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        } else {
            Stack<Edge> path = new Stack<>();
            for (Edge e = edgeTo[v]; e != null; e = edgeTo[e.tail]) {
                path.push(e);
            }
            return path;
        }
    }

    // nested class indexed priority queue  (min binary heap), used by dijkstras algorithm (and relaxing of edges, a part of dijkstra)
    // priority queue is used in dijkstras algorithm to select the new vertex to 'traverse' to.
    private class Queue<Item extends Comparable<Item>> {
        private int size;       // number of elements in queue
        private int[] pq;       // binary heap 1-based indexing
        private int[] qp;       // inverse of pq
        private Item[] keys;    // keys[i] = priority of i

        // constructor
        public Queue(int max) {
            size = 0;
            keys = (Item[]) new Comparable[max + 1];
            pq = new int[max + 1];
            qp = new int[max + 1];
            for (int i = 0; i <= max; i++) {
                qp[i] = -1;
            }
        }

        // check if priority queue empty
        public boolean isEmpty() {
            return size == 0;
        }

        // check if priority queue contains index
        public boolean contains(int i) {
            return qp[i] != -1;
        }

        // associate key with index i
        public void insert(int i, Item key) {
            size++;
            qp[i] = size;
            pq[size] = i;
            keys[i] = key;
        }

        // removes a minimum key & returns the index
        public int delMin() {
            int min = pq[1];
            swap(1, size--);
            qp[min] = -1;
            return min;
        }

        // decrease the key associated with index i
        public void decreaseKey(int i, Item key) {
            keys[i] = key;
        }

        // swap the indexes between the priority queues
        private void swap(int i, int j) {
            int x = pq[i];
            pq[i] = pq[j];
            pq[j] = x;
            qp[pq[i]] = i;
            qp[pq[j]] = j;
        }
    }

    /**
     * The following is made for testing put "test" in program argument under "edit configuration" before running.
     *
     * TestNYC.txt contains 12 vertices and 15 edges
     */
    private static void runTest() {
        Scanner scan = new Scanner(System.in);
        testPathTo();
        testDistTo();
        testHasPathTo();
        testFindsShortestPath();
        testOwnInput(scan);
    }

    private static void testPathTo() {
        Digraph graph = createGraphFromTxt(new File(TEST_FILE_NAME), 12);
        ShortestPath path = new ShortestPath(graph, 0);

        Stack<Edge> pathTo6 = path.pathTo(6);
        boolean pathTo2IsRight = pathTo6.pop().head == 1;       // elements are added in stack from destination back
        boolean pathTo3IsRight = pathTo6.pop().head == 2;       // to source. this is because when popping stack and
        boolean pathTo5IsRight = pathTo6.pop().head == 5;       // printing, it will print from source to destination
        boolean pathTo6IsRight = pathTo6.pop().head == 6;

        Util.assertTrue(pathTo2IsRight && pathTo3IsRight && pathTo5IsRight && pathTo6IsRight, "wrong path from 1-6");
    }

    private static void testDistTo() {
        Digraph graph = createGraphFromTxt(new File(TEST_FILE_NAME), 12);
        ShortestPath path = new ShortestPath(graph, 1);

        boolean distTo2IsRight = path.distTo[2] == 2008;
        boolean distTo3IsRight = path.distTo[3] == 2108;
        boolean distTo7IsRight = path.distTo[7] == Double.POSITIVE_INFINITY; // no connection between these 2

        Util.assertTrue(distTo2IsRight && distTo7IsRight && distTo3IsRight, "incorrect calculations of distance between vertices");
    }

    private static void testHasPathTo() {
        Digraph graph = createGraphFromTxt(new File(TEST_FILE_NAME), 12);
        ShortestPath path = new ShortestPath(graph, 1);

        Util.assertTrue(path.hasPathTo(4), "vertex 1 should have a path to vertex 2");
        Util.assertTrue(!path.hasPathTo(10), "vertex 1 should NOT have a path to vertex 10");
    }

    private static void testFindsShortestPath() {
        Digraph graph = createGraphFromTxt(new File(TEST_FILE_NAME), 12);
        ShortestPath path = new ShortestPath(graph, 7);

        boolean hasPathTo11 = path.hasPathTo(11);   // shortest is the edge 7 to 11 with weight 20
        Stack<Edge> pathTo11 = path.pathTo(11);     // other path is from 7 to 8 to 9 to 10 to 11 with weight 12033

        Util.assertTrue(hasPathTo11, "Path should exist between 7 and 11");
        Util.assertTrue(pathTo11.size() == 1, "Path between 7 and 11 should be of length 1");
        Util.assertTrue(path.distTo[11] == 20, "Wrong distance between 7 and 11");
    }
    private static void testOwnInput(Scanner scan){
        System.out.println("Write 3 integers, from 0 to 12, like this: x x x, this will be the source, detour & destination nodes");
        System.out.println("leave the middle x as an x to not use the detour node");

        int sourceTest = scan.nextInt();            // enter 3 integers, from 0 & 12, like this: x x x
        String detourTest = scan.next();            // leave x as x to not do a detour
        int destinationTest = scan.nextInt();

        if(detourTest.equals("x")) {
            Digraph graph = createGraphFromTxt(new File(TEST_FILE_NAME), 12);   // create the graph

            ShortestPath mainRoute = new ShortestPath(graph, sourceTest);       // SPT from source

            // does the path exist? print out true or false
            System.out.println("does source have path detour? " + mainRoute.hasPathTo(destinationTest));

            // following segment prints the path it takes
            Stack<Edge> pathToDet = mainRoute.pathTo(destinationTest);
            while (!pathToDet.isEmpty()) {
                System.out.println(pathToDet.pop().head);
            }
        } else {
            int detourTest1 = Integer.parseInt(detourTest);
            Digraph graph = createGraphFromTxt(new File(TEST_FILE_NAME), 12);   // create the graph

            ShortestPath mainRoute = new ShortestPath(graph, sourceTest);        // SPT from source
            ShortestPath detourRoute = new ShortestPath(graph, detourTest1);     // SPT from detour

            // does the path exist? print out true or false
            System.out.println("does source have path detour?      " + mainRoute.hasPathTo(detourTest1));
            System.out.println("does detour have path destination? " + detourRoute.hasPathTo(destinationTest));

            if(mainRoute.hasPathTo(detourTest1) && detourRoute.hasPathTo(destinationTest)) {
                // following segment prints the path it takes
                Stack<Edge> pathToDet = mainRoute.pathTo(detourTest1);
                Stack<Edge> pathToDes = detourRoute.pathTo(destinationTest);
                while (!pathToDet.isEmpty()) {
                    System.out.println(pathToDet.pop().head);
                }
                while (!pathToDes.isEmpty()) {
                    System.out.println(pathToDes.pop().head);
                }
            } else {
                System.out.println("No path available");
            }
        }
    }
}