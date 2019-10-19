import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

class Cluster {
    private ArrayList<Integer> verts;
    //        private ArrayList<Edge> outEdges;
    private PriorityQueue<Edge> outEdges;
    private int numVerts = 0;

    public Cluster (LinkedList<Edge>[] adjList, int vert) {
        verts = new ArrayList<>();
        outEdges = new PriorityQueue<>();
        addVert(adjList, vert);
    }

    public int getNumVerts() {
        return numVerts;
    }

    public int getVert(int index) {
        return verts.get(index);
    }

    public void addVert(LinkedList<Edge>[] adjList, int vert) {
//            When a vert is added to the cluster, add edges from that vert
        verts.add(vert);
        addEdge(adjList, vert);
        numVerts++;
    }

    private void addEdge(LinkedList<Edge>[] adjList, int vert) {
        for (int i = 0; i < adjList[vert].size(); i++) {
            outEdges.add(adjList[vert].get(i));
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