import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    public static class Vertex {
        int id;
        List<Edge> edges = new ArrayList<Edge>();
    }
    
    public static void printVertex(Vertex v) {
        System.out.print("Vertex : " + v.id + " ");
        System.out.print("Edges [");
        for(Edge e : v.edges) {
            System.out.print("<"+e.leftVertex.id+","+e.rightVertex.id+"> ");
        }
        System.out.println("]");
        System.out.println(getVertexCount(v));
    }
    
    public static void printVertexes() {
        for (Vertex v : vertexList) {
            printVertex(v);
        }
    }
    
    public static class Edge {
        long leftCount = 0;
        Vertex leftVertex;
        long rightCount = 0;
        Vertex rightVertex;
    }
    
    static List<Vertex> vertexList = new ArrayList<Vertex>();
    static List<Edge> edgeList = new ArrayList<Edge>();
    static long edgesToRemove = 0;
    //static List<Edge> traversedEdges = new ArrayList<Edge>();
    //static List<Vertex> countedVertexes = new ArrayList<Vertex>();
    
    public static void updateCounts() {
        Iterator<Edge> edgeIterator = edgeList.iterator();
        
        while (edgeIterator.hasNext()) {
            Edge e = edgeIterator.next();
            removeEdge(e);
            e.leftCount = getVertexCount(e.leftVertex);
            e.rightCount = getVertexCount(e.rightVertex);
            if (e.leftCount%2==0 && e.rightCount%2==0) { 
                edgesToRemove++;
            }
            addEdge(e);
        }
    }
    
    public static long getVertexCount(Vertex v) {
        long vertexCount = 1;
        List<Edge> removedEdges = new ArrayList<Edge>();
        while (v.edges.size()>0) {
            Edge e = v.edges.get(0);
            removeEdge(e);
            removedEdges.add(e);
            vertexCount += getVertexCount(getOtherVertex(e, v));
        }
        
        for (Edge e : removedEdges) {
            addEdge(e);
        }
        
        return vertexCount;
    }
    
    public static void removeEdge(Edge e) {
        e.leftVertex.edges.remove(e);
        e.rightVertex.edges.remove(e);
    }

    public static void addEdge(Edge e) {
        e.leftVertex.edges.add(e);
        e.rightVertex.edges.add(e);
    }

    public static Vertex getOtherVertex(Edge e, Vertex v) {
        if (e.leftVertex==v) {
            return e.rightVertex;
        } else {
            return e.leftVertex;
        }
    }
    
    public static void createVertices(int n) {
        for (int i=0;i<n;i++) {
            Vertex v = new Vertex();
            v.id = i;
            vertexList.add(v);
        }
    }

    public static void createEdge(int lvId, int rvId) {
        Vertex lv = vertexList.get(lvId);
        Vertex rv = vertexList.get(rvId);
        Edge e = new Edge();
        e.leftVertex = lv;
        e.rightVertex = rv;
        lv.edges.add(e);
        rv.edges.add(e);
        edgeList.add(e);
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int vCount = in.nextInt();
        int eCount = in.nextInt();
        createVertices(vCount);
        for(int i=0; i < eCount; i++){
            int lvId = in.nextInt();
            int rvId = in.nextInt();
            createEdge(--lvId, --rvId);
        }
        updateCounts();
        //printVertexes();
        System.out.println(edgesToRemove);
    }
}
