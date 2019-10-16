import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import static java.util.Arrays.fill;

public class Graphs {

    private class Edge implements Comparable<Edge> {
        private int origin;
        private int dest;
        private int weight;
        

        public Edge() {
            origin = -1;
            dest = -1;
            weight = 0;
        }

        public Edge(int origin, int dest, int weight) {
            this.origin = origin;
            this.dest = dest;
            this.weight = weight;
        }

        public int getOrigin() {
            return origin;
        }

        public int getDest() {
            return dest;
        }

        public int getWeight() {
            return weight;
        }

        public void setOrigin(int origin) {
            this.origin = origin;
        }

        public void setDest(int dest) {
            this.dest = dest;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge edge) {
            if (this.getWeight() > edge.getWeight()) {
                return 1;
            }
            else if (this.getWeight() < edge.getWeight()) {
                return -1;
            }
            else {
                return 0;
            }
        }

//        public void setPrevious(Edge previous) {
//            this.previous = previous;
//        }
//
//        public void setNext(Edge next) {
//            this.next = next;
//        }
    }

    private class Cluster {
        ArrayList<Integer> verts;
        ArrayList<Edge> outEdges;
        ???/
    }

    private LinkedList<Edge> [] adjList;
    private int verticies = 0;
    private int edges = 0;

    public Graphs(int verticies) {
        adjList = new LinkedList[verticies];
        for (int i = 0; i < verticies; i++) {
            adjList[i] = new LinkedList<>();
        }
    }

    public void addEdge(int origin, int dest, int weight) {
        Edge e = new Edge(origin, dest, weight);
        adjList[origin].add(e);
        adjList[dest].add(e);
    }

    public LinkedList<Edge> Brovuka() {
//        span stores the edges that are in the tree
        PriorityQueue<Edge> queue = new PriorityQueue<>();
        LinkedList<Edge> span = new LinkedList<>();

//        keep track of clusters (subtrees) and initialize each vert to be a cluster
        ArrayList<ArrayList<Integer>> clusters = new ArrayList<>(adjList.length);
        for (int i = 0; i < adjList.length; i ++) {
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(i);
            clusters.add(i, temp);
        }
//        runs until there is one cluster
        while (clusters.size() > 1) {
            for (int i = 0; i < clusters.size(); i++) {
                for (int j = 0; j < clusters.get(i).size(); j++)

            }
        }

        return span;
    }

    public boolean cycle(Edge e, Cluster c) {
        ?????
    }

    public static void main(String[] args) {
//        Vertices are just numbered.
        Graphs graph1 = new Graphs(5);
        graph1.addEdge(0, 5, 15);
    }
}
