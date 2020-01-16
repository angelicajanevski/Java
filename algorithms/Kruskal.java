import java.util.*;

public class Kruskal{

    public static WGraph kruskal(WGraph g){
        WGraph A = new WGraph(); //Spanning tree
        ArrayList<Edge> sortedEdges= g.listOfEdgesSorted();
        DisjointSets nodes = new DisjointSets(g.getNbNodes());

        while (A.getNbNodes() != g.getNbNodes()){   //break when span tree has same # nodes as OG tree
            for (Edge edge : sortedEdges ){
                if (IsSafe(nodes, edge)){ //if nodes are disjoint, add edge
                    A.addEdge(edge);
                }
            }


        }

        return A;
    }

    public static Boolean IsSafe(DisjointSets p, Edge e){
        int n1 = e.nodes[0];
        int n2 = e.nodes[1];

        if (p.find(n1)==p.find(n2)){ //if same reps then don't add
            return false;
        }
        //it will be added to same set, so union nodes
        p.union(n1, n2);
        return true;

    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        WGraph t = kruskal(g);
        System.out.println(t);

    }
}
