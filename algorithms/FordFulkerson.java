import java.io.*;
import java.util.*;


public class FordFulkerson {


    public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph) {
        ArrayList<Integer> Stack = new ArrayList<Integer>();
        int time = 0;
        int[][] visited = new int[graph.getNbNodes()][2]; //node [start][finish]



        DFSvisit(source, visited, graph, time, Stack);

        //remove nodes visited after destination
       while (!Stack.get(Stack.size()-1).equals(destination)) {
       if(Stack.size()<2) { return null;} //path is just 1 node

       Stack.remove(Stack.size()-1);
       }
       ArrayList<Integer> path = new ArrayList<>();

       //parenthesis theorem, if closed bracket then
       for (int i =0; i< Stack.size(); i++){
           if (path.contains(Stack.get(i))){
               path.remove(Stack.get(i)); //pop it off
               continue;
           }
           path.add(Stack.get(i));

       }

        return path;
    }

    public static void DFSvisit(Integer node, int[][] visited, WGraph g, int time, ArrayList<Integer> Stack) {
        time += 1;
        visited[node][0] = time; //update start time
        Stack.add(node); //add node at discovery
        if (node.equals(g.getDestination())){ //don't visit any destination neighbours
            return;
        }

        ArrayList<Integer> adjList = getAdjList(g.getEdges(), node);
        for (Integer neighbour : adjList) {
            if (visited[neighbour][0] == 0 && g.getEdge(node, neighbour).weight!= 0) {  //if hasn't been visited and edge is not zero

                DFSvisit(neighbour, visited, g, time, Stack);

            }
        }


        time += 1;
        visited[node][1] = time;
        Stack.add(node); //add node at finishing time
    }


    public static ArrayList<Integer> getAdjList(ArrayList<Edge> edges, Integer node) {
        ArrayList<Integer> adjList = new ArrayList<>();
        for (Edge e : edges) {           //find list of adjacent nodes
            Integer first = e.nodes[0];
            if (first.equals(node)) {
                adjList.add(e.nodes[1]);
            }
        }
        return adjList;
    }

    public static WGraph compute_gf(WGraph flow, WGraph capacity){
        //initial creation of gf
        WGraph gf = new WGraph(flow);

        for (Edge e : capacity.getEdges()){
            int cap = e.weight; //cap edge
            gf.getEdge(e.nodes[0], e.nodes[1]).weight = cap;
            gf.addEdge(new Edge(e.nodes[1], e.nodes[0], cap)); // the back edge
        }

        return gf;
    }
    public static WGraph update_gf(WGraph flow, WGraph capacity, WGraph gf){

        for (Edge e : capacity.getEdges()){
            int cap = e.weight; //cap edge
            int flo = flow.getEdge(e.nodes[0], e.nodes[1]).weight; //curr flow value
            int res = cap - flo;

            //update gf
            gf.getEdge(e.nodes[0], e.nodes[1]).weight = res; // update how much forward can you go in gf
            gf.getEdge(e.nodes[1], e.nodes[0]).weight = flo; //back edge can go back current flow amount
        }

        return gf;
    }


    public static ArrayList<Edge> getEdgesInPath(ArrayList<Integer> path, WGraph gf){
        ArrayList<Edge> pathEdges = new ArrayList<>();


        for (int i =0; i< path.size()-1; i++){
            Integer n1 = path.get(i);
            Integer n2 = path.get(i+1); //second node

            pathEdges.add(gf.getEdge(n1, n2));

        }

        return pathEdges;
    }

    public static void fordfulkerson(Integer source, Integer destination, WGraph graph, String filePath) {
        String answer = "";
        int maxFlow = 0;

        //initialize flow graph will all flow = 0
        WGraph flow = new WGraph(graph);
        for (Edge e : flow.getEdges()) {
         e.weight =0;
        }

        WGraph gf = compute_gf(flow, graph);
        ArrayList<Integer> augPath = pathDFS(gf.getSource(), gf.getDestination(), gf);




        while (augPath != null){///dfs returns null if no path to sink is found

            int B =Integer.MAX_VALUE;
            ArrayList<Edge> pEdges = getEdgesInPath(augPath, gf); //get edge list of path

            int weight = 0;
            for (Edge e : pEdges){ //find bottleneck among edge list

                weight = e.weight;
                if (weight< B){
                    B = weight;
                }
            }

            //update flow (augment path)
            for (Edge e : pEdges){
                Integer n1 = e.nodes[0];
                Integer n2 = e.nodes[1];
                if (graph.getEdge(n1, n2)== null){ //the edge to be augmented does not exist in g, its a back edge
                    //reverse edge to get real edge and subtract flow
                    flow.getEdge(n2, n1).weight = flow.getEdge(n2, n1).weight - B; //update flow to subtract bottleneck

                }
                else {
                    //if forward edge, add flow
                    flow.getEdge(n1,n2).weight = flow.getEdge(n1,n2).weight + B;

                }
            }


            //update gf
            update_gf(flow, graph, gf);


           //find new path ing gf
            augPath = pathDFS(gf.getSource(), gf.getDestination(), gf);


        }

        //find max flow
        ArrayList <Integer> sourceBuddies = getAdjList(flow.getEdges(), flow.getSource());
        for (Integer buddy : sourceBuddies){
            maxFlow += flow.getEdge(flow.getSource(), buddy).weight; //add all flow from outgoing source edges
        }

        //WARNING : return flow not graph
        answer += maxFlow + "\n" + flow.toString();
        writeAnswer(filePath + ".txt", answer);
        System.out.println(answer);
    }


    public static void writeAnswer(String path, String line) {
        BufferedReader br = null;
        File file = new File(path);
        // if file doesnt exists, then create it

        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(line + "\n");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String file = args[0];
        File f = new File(file);
        WGraph g = new WGraph(file);
        fordfulkerson(g.getSource(),g.getDestination(),g,f.getAbsolutePath().replace(".txt",""));
    }


}
