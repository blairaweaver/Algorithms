public class Graphs {

private class VectorNode<E extends Comparable<E>> {
        private E element;
        private VectorNode next;
        private VectorNode previous;

        public VectorNode(E e) {
            this.element = e;
            next = null;
            previous = null;
        }

        public VectorNode(E e, VectorNode previous) {
            this.element = e;
            this.previous = previous;
            this.next = null;
        }

        public VectorNode(E e, VectorNode previous, VectorNode next) {
            this.element = e;
            this.previous = previous;
            this.next = next;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public E getElement() {
            return element;
        }

        public VectorNode getNext() {
            return next;
        }

        public VectorNode getPrevious() {
            return previous;
        }

        public void setNext(VectorNode next) {
            this.next = next;
        }

        public void setPrevious(VectorNode previous) {
            this.previous = previous;
        }

        public boolean hasNext() {
            return this.next != null;
        }
    }

    private int[][] adjMatrix;


    public Graphs(int verticies) {
        adjMatrix = new int[verticies][verticies];
        adjList = new VectorNode[verticies];
    }



    public VectorNode[] getAdjList() {
        return adjList;
    }

    public int numVertices() {
        return verticies;
    }

    public int numEdges() {
        return edges;
    }

    public int outDegree(int[][] adjMatrix, int vert) {
        int sum = 0;
        for (int i = 0; i < adjMatrix[vert].length; i++) {
            if (adjMatrix[vert][i] != -1) {
                sum += adjMatrix[vert][i];
            }
        }
        return sum;
    }

    public int outDegree(VectorNode[] adjList, int vert) {
        int sum = 0;
        VectorNode current = adjList[vert];
        if (current != null) {
            sum++;
        }
        while (current.hasNext()) {
            current = current.getNext();
            sum++;
        }
        return sum;
    }

//    Currently Assuming non directional graph
//    public int inDegree() {}
//
//    public getEdge() {}
//
//    public getVerticies() {}

    public boolean areAdjacent(int[][] adjMatrix, int vert1, int vert2) {
        return adjMatrix[vert1][vert2] == 1;
    }

    public boolean areAdjacent(VectorNode[] adjList, int vert1, int vert2) {
        VectorNode current = adjList[vert1];
        while (current.hasNext()) {
            int cmp = current.getElement().compareTo(vert2);
            if (cmp == 0) {
                return true;
            }
        }

        return false;
    }

    public void insertVertex() {
        int oldSize = adjMatrix[0].length;
        int[][] tempMatrix = new int[oldSize + 1][oldSize + 1];
        VectorNode[] tempArray = new VectorNode[oldSize + 1];
        for (int i = 0; i < adjMatrix.length; i++) {
            tempMatrix[i][i] = adjMatrix[i][i];
            tempArray[i] = adjList[i];
        }
        verticies++;
    }

    public void insertEdge(int vert1, int vert2) {
        adjMatrix[vert1][vert2] = 1;
        adjMatrix[vert2][vert1] = 1;
        if (!edgeExist(vert1,vert2)) {
            addEdgeToList(vert1,vert2);
        }
        edges++;
    }

    public void removeEdge(int vert1, int vert2) {
        adjMatrix[vert1][vert2] = 0;
        adjMatrix[vert2][vert1] = 0;
        if (edgeExist(vert1,vert2)) {
            VectorNode current = adjList[vert1];
            remove(vert2, current);
            current = adjList[vert2];
            remove(vert1, current);
        }
        edges--;
    }

    public void removeVertex(int vert) {
        for (int i = 0; i < adjMatrix.length; i++) {
            adjMatrix[vert][i] = -1;
            adjMatrix[i][vert] = -1;
            if (i != vert && edgeExist(i,vert)) {
                VectorNode current = adjList[i];
                remove(vert, current);
            }
        }
        adjList[vert] = null;
        verticies--;
    }

    public boolean edgeExist(int vert1, int vert2) {
        VectorNode current = adjList[vert1];
        while (current != null) {
            int cmp = current.getElement().compareTo(vert2);
            if (cmp == 0) {
                return true;
            }
            else {
                current = current.getNext();
            }
        }

        return false;
    }

    private void addEdgeToList(int vert1, int vert2) {
        VectorNode current = adjList[vert1];
        while (current != null && current.hasNext()) {
            current = current.getNext();
        }
        if (current == null) {
            adjList[vert1] = new VectorNode(vert2, null, null);
        }
        else current.setNext(new VectorNode(vert2, current, null));
        current = adjList[vert2];
        while (current != null && current.hasNext()) {
            current = current.getNext();
        }
        if (current == null) {
            adjList[vert2] = new VectorNode(vert1, null, null);
        }
        else current.setNext(new VectorNode(vert1, current, null));
    }

    private void remove(int vert1, VectorNode current) {
        while (current != null) {
            int cmp = current.getElement().compareTo(vert1);
            if (cmp == 0) {
                current.getPrevious().setNext(current.getNext());
                if (current.hasNext()) {
                    current.getNext().setPrevious(current.getPrevious());
                }
                current = null;
            }
            else {
                current = current.getNext();
            }
        }
    }

    public void print(){
        for (int i = 0; i < adjMatrix.length; i++) {
            System.out.print(i + ": ");
            for (int j = 0; j < adjMatrix.length; j++) {
                if (adjMatrix[i][j] == 1) {
                    System.out.print(j + " ");
                }
            }
            System.out.println();
        }

        System.out.println();

        for (int i = 0; i < adjMatrix.length; i++) {
            System.out.print(i + ": ");
            VectorNode current = adjList[i];
            while (current != null) {
                System.out.print(current.getElement() + " ");
                current = current.getNext();
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Graphs test = new Graphs(6);
        System.out.println(test.numVertices());
        test.insertEdge(1,5);
        test.insertEdge(1,2);
        test.insertEdge(2,3);
        test.insertEdge(2,4);
        test.insertEdge(2,5);
        test.insertEdge(4,3);
        test.insertEdge(4,5);
        test.print();
        test.removeEdge(4,5);
        test.print();
    }
}