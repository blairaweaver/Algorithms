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

    }

    private class Cluster {
        private ArrayList<Integer> verts;
//        private ArrayList<Edge> outEdges;
        private PriorityQueue<Edge> outEdges;
        private int numVerts = 0;

        public Cluster (int vert) {
            addVert(vert);
            addEdge(vert);
//            cleanEdges();
            numVerts++;
        }
        public void addVert(int vert) {
            verts.add(vert);
        }

        private void addEdge(int vert) {
            for (int i = 0; i < adjList[i].size(); i++) {
                if (!outEdges.contains(adjList[i].get(i))) {
                    outEdges.add(adjList[i].get(i));
                }
            }
        }

        public Edge minEdge() {
            return outEdges.poll();
        }

//        function to remove any edges that haven't been used, but that connect two vert in same cluster
//        private void cleanEdges() {
//            if (numVerts == 1) {
//                return;
//            }
//            else {
//                for (int i = 0; i < outEdges.size(); i++) {
//                    if (isCycle(i)) {
//                        outEdges.remove(i);
//                    }
//                }
//            }
//        }

//        public boolean isCycle(int index) {
//            return (verts.indexOf(outEdges.get(index).getDest()) != -1 && verts.indexOf(outEdges.get(index).getOrigin()) != -1);
//        }
    }

    private LinkedList<Edge> [] adjList;
    private int verticies = 0;
    private int edges = 0;

    public Graphs(int verticies) {
        adjList = new LinkedList[verticies];
        for (int i = 0; i < verticies; i++) {
            adjList[i] = new LinkedList<>();
        }
        this.verticies = verticies;
    }

    public void addEdge(int origin, int dest, int weight) {
        Edge e = new Edge(origin, dest, weight);
        adjList[origin].add(e);
        adjList[dest].add(e);
        edges++;
    }

    public LinkedList<Edge> Brovuka() {
//        span stores the edges that are in the tree
        PriorityQueue<Edge> queue = new PriorityQueue<>();
        LinkedList<Edge> span = new LinkedList<>();

//        keep track of clusters (subtrees) and initialize each vert to be a cluster
        ArrayList<Cluster> clusters = new ArrayList<>(adjList.length);
        for (int i = 0; i < adjList.length; i++) {
            clusters.add(new Cluster(i));
        }

//        runs until there is one cluster
        while (clusters.size() > 1) {
            for (int i = 0; i < clusters.size(); i++) {
                Edge e = clusters.get(i).minEdge();

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
