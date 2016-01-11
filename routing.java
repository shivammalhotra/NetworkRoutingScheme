import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class routing {

    String ipFile;
    SspGraph mainSSP;
    int sourceNode;
    int destNode;
    LinkedList<String> prefixArray;
    String[] aryIP;
    BTrie[] aryTrie;

    public routing() {
        ipFile = "";
        
        sourceNode = -1;
        destNode = -1;
        prefixArray = new LinkedList<String>();
    }
    public static void main(String[] args) {
        final long startTime = System.currentTimeMillis();
        routing routing = new routing();

        routing.processCommandLineArguments(args);
        //routing.mainSSP.processFile();
        routing.processFile();
        for(int i=0;i<routing.mainSSP.verteces;i++) {
            routing.mainSSP.shortestPathSetup(i);
          //  routing.mainSSP.runDijkstra();
            for(int j=0;j<routing.mainSSP.verteces;j++) {
                int nextHop = routing.mainSSP.getNextNode(i,j);
                if(nextHop != -1) {
                   // routing.mainSSP.vertices[i].btrie.addNode(routing.getKey(routing.mainSSP.vertices[j].ip), nextHop);
                    routing.aryTrie[i].addNode(routing.getKey(routing.aryIP[j]), nextHop);
                }
            }
            routing.aryTrie[i].cleanUp();
        }

        int net_weight = routing.transferPacket(routing.sourceNode, routing.destNode);
        System.out.println(net_weight);
        while(!routing.prefixArray.isEmpty())
            System.out.print(routing.prefixArray.poll() + " ");
        System.out.print("\n");
        final long endTime = System.currentTimeMillis();
        //System.out.println("Execution Time: " + (endTime - startTime) + "ms"); /* Execution time of the algorithm */
    }

    private int transferPacket(int source, int dest) {
        int net_weight = 0;
        //System.out.print(source + " -- ");
        if(source == dest) {
            //System.out.println("\n DONE ... ");
            return 0;
        }
        else {
            int hop = this.aryTrie[source].getNextHop(this.getKey(this.aryIP[dest]), this.prefixArray);
            
            if (hop == -1) {
                //System.out.println("\nNo Path found ... ");
                System.exit(1);
            }
            net_weight = this.getEdgeWeight(source, hop) + this.transferPacket(hop, dest);
        }
        return net_weight;
    }
    
    

    private int getEdgeWeight(int source, int dest) {
        SspLinkedList sll = mainSSP.aryll.get(source);
    	SspNode temp = sll.head;
        while(temp!=null){
        	if(temp.edge==dest){
        		return temp.weight;
        	}
        	temp = temp.id;
        }
        return -1;
    }
    	
    	/*Vertex sourceVertex = this.mainSSP.vertices[source];
        Edges temp = sourceVertex.edges;
        while(temp != null) {
            if(temp.destVert == dest)
                return temp.weight;
            temp = temp.next;
        }
        return -1;
    }
*/
    private void processFile() {
        StringTokenizer st;
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.ipFile));

            String inputLines;
            int vertexCount = 0;
            while((inputLines = br.readLine()) != null) {
                if(!inputLines.trim().isEmpty()) { /* Avoid empty lines in the input file */
                	aryIP[vertexCount] = inputLines.trim();
                   // this.mainSSP.vertices[vertexCount].ip = 
                    vertexCount++;
                }
            }
           /* if(vertexCount != this.mainSSP.numVertices) {
                System.out.println("Not sufficient ips ... Exiting ...");
                System.exit(1);
            }*/
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found ... Exiting");
            System.exit(1);
        } catch (IOException ex) {
            System.out.println("Error in File Format ... Exiting");
            System.exit(1);
        }
    }

    private void processCommandLineArguments(String[] args) {
        if(args.length != 4) {
            System.out.println("Arguments not specified properly ....");
            System.out.println("Format: java -jar routing.jar <graph file> <ip file> <source vertex index> <destination vertex index>");
            System.exit(1);
        }
        mainSSP = new SspGraph(args[0]);
        aryIP = new String[mainSSP.verteces];
        aryTrie = new BTrie[mainSSP.verteces];
       // this.mainSSP.inputFile = args[0];
        for (int i = 0; i < mainSSP.verteces; i++) {
			aryTrie[i] = new BTrie();
		}
        this.ipFile = args[1];
        this.sourceNode = Integer.parseInt(args[2]);
        this.destNode = Integer.parseInt(args[3]);
    }

    private boolean[] getKey(String ip) {

        StringTokenizer st = new StringTokenizer(ip, ".");
        int ip1 = Integer.parseInt(st.nextToken());
        int ip2 = Integer.parseInt(st.nextToken());
        int ip3 = Integer.parseInt(st.nextToken());
        int ip4 = Integer.parseInt(st.nextToken());
        //System.out.println(ip1 + "." + ip2 + "." + ip3 + "." + ip4);
        //System.out.println(Integer.toBinaryString(ip1) + " " + Integer.toBinaryString(ip2) + " " + Integer.toBinaryString(ip3) + " " + Integer.toBinaryString(ip4));
        boolean[] b = new boolean[32];
        for(int j=0;j<8;j++) {
            b[7-j] = (((ip1 >> j) & 1) == 1) ? true:false;
        }
        for(int j=0;j<8;j++) {
            b[15-j] = (((ip2 >> j) & 1) == 1) ? true:false;
        }
        for(int j=0;j<8;j++) {
            b[23-j] = (((ip3 >> j) & 1) == 1) ? true:false;
        }
        for(int j=0;j<8;j++) {
            b[31-j] = (((ip4 >> j) & 1) == 1) ? true:false;
        }

        return b;
    }
}
