import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Graphs {

    private LinkedList<Edge> [] adjList;

    public Graphs(int vertices) {
        adjList = new LinkedList[vertices];
        for (int i = 0; i < vertices; i++) {
            adjList[i] = new LinkedList<>();
        }
    }

    public void addEdge(int origin, int dest, int weight) {
        Edge e = new Edge(origin, dest, weight);
        adjList[origin].add(e);
        adjList[dest].add(e);
    }

    public LinkedList<Edge> Brovuka() {
        long startTime = System.nanoTime();
//        span stores the edges that are in the tree
        LinkedList<Edge> span = new LinkedList<>();

//        keep track of clusters (subtrees) and initialize each vert to be a cluster at beginning
        ArrayList<Cluster> clusters = new ArrayList<>(adjList.length);
        for (int i = 0; i < adjList.length; i++) {
            clusters.add(new Cluster(adjList, i));
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
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
        System.out.println("Execution time in nanoseconds for Boruvka's : " + timeElapsed);

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
        for (int i = 0; i < c2.getNumVerts(); i++) {
            c1.addVert(adjList, c2.getVert(i));
        }
//        removed the c2 from list to decrement the number of clusters
        clusters.remove(c2);
    }

    public void printSpan(LinkedList<Edge> span) {
//        currently, prints edges in order that was added to list.
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

    public void printWeight(LinkedList<Edge> span) {
//        This method prints the total weight of the MST
        int sum = 0;
        for (int i = 0; i < span.size(); i++) {
            sum += span.get(i).getWeight();
        }
        System.out.println("The total weight of the MST is " + sum);
    }

    //    This code is adapted from my Data Structure class
    public LinkedList<Edge> Kruskal() {
        long startTime = System.nanoTime();
//        span stores the edges that are in the tree
        PriorityQueue<Edge> queue = new PriorityQueue<>();
        LinkedList<Edge> span = new LinkedList<>();

//        keep track of clusters (subtrees)
        ArrayList<ArrayList<Integer>> clusters = new ArrayList<>(adjList.length);

//        Creating and adding Edges to queue
        for (int i = 0; i < adjList.length; i++) {
            for (int j = 0; j < adjList[i].size(); j++) {
                queue.add(adjList[i].get(j));
            }
        }

        while (!queue.isEmpty()) {
            Edge current = queue.poll();
//            checks if this edge should be added
            if (toAdd(current, clusters)) {
//                adds edge to the span and then adjust the subtrees
                span.add(current);
                adjustSubtrees(current, clusters);
            }
        }
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
        System.out.println("Execution time in nanoseconds for Kruskal's : " + timeElapsed);

        return span;
    }

    private boolean toAdd(Edge x, ArrayList<ArrayList<Integer>> subTrees) {
        int originIndex = -1, destIndex = -1;
        for (int i =0; i < subTrees.size(); i++) {
            if (subTrees.get(i).indexOf(x.getOrigin()) != -1) {
                originIndex = i;
            }
            if (subTrees.get(i).indexOf(x.getDest()) != -1) {
                destIndex = i;
            }
        }

        return !(originIndex == destIndex && originIndex != -1);
    }

    private void adjustSubtrees(Edge x, ArrayList<ArrayList<Integer>> subTrees) {
        int originIndex = -1, destIndex = -1;
        for (int i =0; i < subTrees.size(); i++) {
            if (subTrees.get(i).indexOf(x.getOrigin()) != -1) {
                originIndex = i;
            }
            if (subTrees.get(i).indexOf(x.getDest()) != -1) {
                destIndex = i;
            }
        }

        if (originIndex == -1 && destIndex == -1) {
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(x.getOrigin());
            temp.add(x.getDest());
            subTrees.add(temp);
        }
        else if (originIndex != -1 && destIndex != -1) {
            if (originIndex < destIndex) {
                for (int i = 0; i < subTrees.get(destIndex).size(); i++) {
                    subTrees.get(originIndex).add(subTrees.get(destIndex).get(i));
                }
                subTrees.remove(destIndex);
            }
            else {
                for (int i = 0; i < subTrees.get(originIndex).size(); i++) {
                    subTrees.get(destIndex).add(subTrees.get(originIndex).get(i));
                }
                subTrees.remove(originIndex);
            }
        }
        else if (originIndex == -1) {
            subTrees.get(destIndex).add(x.getOrigin());
        }
        else {
            subTrees.get(originIndex).add(x.getDest());
        }
    }


    public static void main(String[] args) {
//        Add Edges to graph. Vertices are just numbered from 0 to 8. example graph (modified) is from
//        https://www.geeksforgeeks.org/boruvkas-algorithm-greedy-algo-9/ for reference
//        Program assumes that the graph is completely connected.
        Graphs graph1 = new Graphs(18);
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
        graph1.addEdge(9,10,4);
        graph1.addEdge(10,11,8);
        graph1.addEdge(11,12,7);
        graph1.addEdge(12,13,9);
        graph1.addEdge(13,14,10);
        graph1.addEdge(14,15,2);
        graph1.addEdge(15,16,1);
        graph1.addEdge(16,9,8);
        graph1.addEdge(16,17,7);
        graph1.addEdge(10,16,11);
        graph1.addEdge(11,17,2);
        graph1.addEdge(11,14,4);
        graph1.addEdge(12,14,14);
        graph1.addEdge(15,17,6);
        graph1.addEdge(0,13,20);

        System.out.println("Boruvka's");
        LinkedList<Edge> span = graph1.Brovuka();
        graph1.printSpan(span);
        graph1.printWeight(span);

        System.out.println();

        System.out.println("Kruskal's");
        LinkedList<Edge> spanK = graph1.Kruskal();
        graph1.printSpan(spanK);

        graph1.printWeight(spanK);

    }
}
