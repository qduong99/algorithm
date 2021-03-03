package com.qduong.graph.bfs;
/*
 *  Developed by Quang Duong on 2/17/21, 11:07 AM
 */

import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.ImmutableGraph;

import java.util.*;

/**
 * Breadth first search to find a cycle in graph
 */
public class BFSFindCycle<N> extends BFS<N>{
    protected Map<N, Integer> level = new HashMap<>();
    List<LinkedList> result = new ArrayList<>();

    public Integer getLevel(N v){
        return level.get(v);
    }
    public void setLevel(N v,  Integer value){
        level.put(v, value);
    }

    @Override
    protected void preVertexVisit(Graph graph, N v) {
        if(getLevel(v) == null){
            setLevel(v, 1);
        }
    }

    @Override
    protected void preDiscEdgeVisit(Graph graph, N v, EndpointPair<N> e, N w) {
        Integer nextLevel = getLevel(v) + 1;
        setLevel(w, nextLevel);
        setParent(w, e);
    }

    @Override
    protected void crossEdgeVisit(Graph graph, N v, EndpointPair<N> e, N w) {
        Integer levelV = getLevel(v);
        Integer levelW = getLevel(w);
        result.add(buildCycle(graph, v,w, levelV == levelW));

    }

    LinkedList buildCycle(Graph graph, N v, N w, boolean sameLevel){
        LinkedList result = new LinkedList();
        N v1;
        if(!sameLevel){
            result.addLast(w);
            EndpointPair<N> e = getParent(w);
            v1 = e.adjacentNode(w);
        }else{
            v1 = w;
        }
        LinkedList rightCycle = new LinkedList();
        EndpointPair<N> e,e1;
        while(v!=v1){
            result.addFirst(v1);
            e1 = getParent(v1);
            v1 = e1.adjacentNode(v1);
            rightCycle.addLast(v);
            e = getParent(v);
            v = e.adjacentNode(v);
        }
        result.addFirst(v1);//// add root of cycle , v1=v
        rightCycle.addLast(v);
        result.addAll(rightCycle);
        return result;
    }

    @Override
    protected Object result(Graph graph) {
        return result;
    }

    public static void main(String[] args) {
        ImmutableGraph<String> graph =
                GraphBuilder.undirected()
                        .<String>immutable()
                        .putEdge("A","B")


                        .putEdge("B", "F")
                        .putEdge("F", "E")

                        .putEdge("B", "C")
                        .putEdge("C", "D")
                        .putEdge("D", "K")
                        .putEdge("K", "E")
                        .putEdge("E", "C")

                        .putEdge("K", "J")
                        .putEdge("J", "A")
                        .build();
        BFS<String> bfs = new BFSFindCycle<>();
        System.out.println(" result "+  bfs.runBFS(graph));
    }
}
