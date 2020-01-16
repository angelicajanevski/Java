public class BellmanFord{

    /**
     * Utility class. Don't use.
     */
    public class BellmanFordException extends Exception{
        private static final long serialVersionUID = -4302041380938489291L;
        public BellmanFordException() {super();}
        public BellmanFordException(String message) {
            super(message);
        }
    }

    /**
     * Custom exception class for BellmanFord algorithm
     *
     * Use this to specify a negative cycle has been found
     */
    public class NegativeWeightException extends BellmanFordException{
        private static final long serialVersionUID = -7144618211100573822L;
        public NegativeWeightException() {super();}
        public NegativeWeightException(String message) {
            super(message);
        }
    }

    /**
     * Custom exception class for BellmanFord algorithm
     *
     * Use this to specify that a path does not exist
     */
    public class PathDoesNotExistException extends BellmanFordException{
        private static final long serialVersionUID = 547323414762935276L;
        public PathDoesNotExistException() { super();}
        public PathDoesNotExistException(String message) {
            super(message);
        }
    }

    private int[] distances = null;
    private int[] predecessors = null;
    private int source;

    BellmanFord(WGraph g, int source) throws BellmanFordException{

        int vertices = g.getNbNodes();
        distances = new int[vertices];
        predecessors = new int[vertices];
        this.source =source;

        for (int i =0; i< vertices; i++){ // -1 means no predecessor, MAX_VALUE is infinite distance
            this.distances[i] = Integer.MAX_VALUE;
            this.predecessors[i] = -1;
        }
        this.distances[source] = 0;

        for (int i =0; i< vertices-1; i++){ // -
            for (Edge e : g.getEdges() ) {
                Integer u = e.nodes[0];
                Integer v = e.nodes[1];
                if(distances[u]+ e.weight < distances[v] ){
                    distances[v] = distances[u] + e.weight;
                    predecessors[v] = u;
                }
            }

        }

        for (Edge e : g.getEdges()){
            Integer u = e.nodes[0];
            Integer v = e.nodes[1];
            if(distances[u]+ e.weight < distances[v]){
                throw new NegativeWeightException("Negative cycle found");
            }
        }

    }

    public int[] shortestPath(int destination) throws BellmanFordException{
        /*Returns the list of nodes along the shortest path from
         * the object source to the input destination
         * If not path exists an Exception is thrown
         * Choose appropriate Exception from the ones given
         */

        if (this.distances[destination] == Integer.MAX_VALUE){
            throw new PathDoesNotExistException("no path from source to distance");
        }


        int [] revpath = new int[distances.length]; // path can be as long as all nodes
        int node = destination; //start of path
        int i =0;
        while (node != this.source ){
            revpath[i] = node;
            node = predecessors[node];
            i++;
        }
        revpath[i] = this.source;
        //revpath is [dest, anc1, anc2, anc3, .., source]

        int [] path = new int[i];
        int k =path.length-1; //index of last element in rev path
        for (int j=0; j<path.length; j++){
            path[j] = revpath[k];
            k--;
        }


        return path;
    }

    public void printPath(int destination){
        /*Print the path in the format s->n1->n2->destination
         *if the path exists, else catch the Error and
         *prints it
         */
        try {
            int[] path = this.shortestPath(destination);
            for (int i = 0; i < path.length; i++){
                int next = path[i];
                if (next == destination){
                    System.out.println(destination);
                }
                else {
                    System.out.print(next + "-->");
                }
            }
        }
        catch (BellmanFordException e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){
        String file = args[0];
        WGraph g = new WGraph(file);
        try{
            BellmanFord bf = new BellmanFord(g, g.getSource());
            bf.printPath(g.getDestination());
        }
        catch (BellmanFordException e){
            System.out.println(e);
        }

    }
}
