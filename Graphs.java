import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

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
            verts = new ArrayList<>();
            outEdges = new PriorityQueue<>();
            addVert(vert);
            addEdge(vert);
            numVerts++;
        }

        public int getNumVerts() {
            return numVerts;
        }

        public int getVert(int index) {
            return verts.get(index);
        }

        public void addVert(int vert) {
            verts.add(vert);
        }

        private void addEdge(int vert) {
            for (int i = 0; i < adjList[vert].size(); i++) {
//                checks to make sure edge isn't already in list and doesn't connect to two verts in cluster
                if (!outEdges.contains(adjList[vert].get(i)) && !isCycle(adjList[vert].get(i))) {
                    outEdges.add(adjList[vert].get(i));
                }
            }
        }

        public Edge minEdge() {
//            will return smallest edge that isn't cycle
            Boolean foundCan = false;
            Edge candidate = null;
            while (!foundCan) {
                 candidate = outEdges.poll();
                if (!isCycle(candidate)) {
                    foundCan = true;
                }
            }
            return candidate;
        }

//        returns if the cluster has the vert
        public boolean hasVert(int vert) {
            return verts.contains(vert);
        }

//      returns if edge creates cycle with cluster
        public boolean isCycle(Edge e) {
            return (verts.indexOf(e.getDest()) != -1 && verts.indexOf(e.getOrigin()) != -1);
        }
    }

    private LinkedList<Edge> [] adjList;
    private int vertices = 0;
    private int edges = 0;

    public Graphs(int vertices) {
        adjList = new LinkedList[vertices];
        for (int i = 0; i < vertices; i++) {
            adjList[i] = new LinkedList<>();
        }
        this.vertices = vertices;
    }

    public void addEdge(int origin, int dest, int weight) {
        Edge e = new Edge(origin, dest, weight);
        adjList[origin].add(e);
        adjList[dest].add(e);
        edges++;
    }

    public LinkedList<Edge> Brovuka() {
//        span stores the edges that are in the tree
        LinkedList<Edge> span = new LinkedList<>();


//        keep track of clusters (subtrees) and initialize each vert to be a cluster at beginning
        ArrayList<Cluster> clusters = new ArrayList<>(adjList.length);
        for (int i = 0; i < adjList.length; i++) {
            clusters.add(new Cluster(i));
        }

//        runs until there is one cluster
        while (clusters.size() > 1) {
//            reset the array every loop
            Edge temp[] = new Edge[clusters.size()];
//            Get the min edge from every cluster and add to the array
            for (int i = 0; i < clusters.size(); i++) {
                temp[i] = clusters.get(i).minEdge();
            }
//            Wait till after all edges have been retrieved
            for (int i = 0; i < temp.length; i++) {
                Cluster cluster1 = findCluster(clusters, temp[i].getOrigin());
                Cluster cluster2 = findCluster(clusters, temp[i].getDest());

//                checks to see if the clusters are the same (ie the edge was picked twice) and goes to next edge
                if (cluster1 == cluster2) continue;

//                if different cluster, combine and add edge to span
                combineCluster(clusters, cluster1, cluster2);
                span.add(temp[i]);
            }
        }

        return span;
    }
    
    public Cluster findCluster(ArrayList<Cluster> clusters, int vertice) {
        Cluster c = null;
        for (int i = 0; i < clusters.size(); i++) {
            if (clusters.get(i).hasVert(vertice)) {
                c = clusters.get(i);
                break;
            }
        }
        return c;
    }

//    This will combine the two clusters and remove one from the list of clusters
    public void combineCluster(ArrayList<Cluster> clusters, Cluster c1, Cluster c2) {
        if (c1.getNumVerts() < c2.getNumVerts()) {
//            all this does is rearrange the c1 and c2 in order that c1 is always greater than or equal to c2
//            so that the code for combining can be the same
            Cluster temp = c2;
            c2 = c1;
            c1 = temp;
        }
//        combine c1 and c2 by adding all the verts in c2 to c1
        for (int i = 0; i < c2.numVerts; i++) {
            c1.addVert(c2.getVert(i));
        }
//        removed the c2 from list to decrement the number of clusters
        clusters.remove(c2);
    }

    public void printSpan(LinkedList<Edge> span) {
//        currently, prints edges in order that was added to list. This function can be modified to print other
//        information such as total weight of the MST
        for (int i = 0; i < span.size(); i++){
            Edge current = span.get(i);
            if (i == span.size() - 1) {
                System.out.println(current.getOrigin() + "-" + current.getDest());
            }
            else {
                System.out.print(current.getOrigin() + "-" + current.getDest() + ", ");
            }
        }
    }


    public static void main(String[] args) {
//        Add Edges to graph. Vertices are just numbered from 0 to 8. example graph is from
//        https://www.geeksforgeeks.org/boruvkas-algorithm-greedy-algo-9/ for reference
        Graphs graph1 = new Graphs(9);
        graph1.addEdge(0,1,4);
        graph1.addEdge(1,2,8);
        graph1.addEdge(2,3,7);
        graph1.addEdge(3,4,9);
        graph1.addEdge(4,5,10);
        graph1.addEdge(5,6,2);
        graph1.addEdge(6,7,1);
        graph1.addEdge(7,0,8);
        graph1.addEdge(7,8,7);
        graph1.addEdge(1,7,11);
        graph1.addEdge(2,8,2);
        graph1.addEdge(2,5,4);
        graph1.addEdge(3,5,14);
        graph1.addEdge(6,8,6);

        graph1.printSpan(graph1.Brovuka());;

    }
}
