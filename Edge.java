class Edge implements Comparable<Edge> {
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