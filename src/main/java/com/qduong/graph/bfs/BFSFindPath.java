package com.qduong.graph.bfs;/*
 *  Developed by Quang Duong on 2/17/21, 9:46 AM
 * Copyright (c) DnqSoft 2020. All rights reserved.
 */

import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.ImmutableGraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 *  Developed by Quang Duong on 2/17/21, 11:06 AM
 */

public class BFSFindPath<N> extends BFS<N>{
    private N start;
    private N end;

    public BFSFindPath(N start, N end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected void preDiscEdgeVisit(Graph graph, N v, EndpointPair<N> e, N w) {
        setParent(w, e);
    }

    @Override
    protected Object result(Graph graph) {
        return buildPath(graph, start, end);
    }


    private List buildPath(Graph graph, N v, N w){
        List path = new ArrayList();
        N x = end;
        while (x != start){
            path.add(x);
            EndpointPair<N> e2 = getParent(x);
            path.add(e2);
            x = e2.adjacentNode(x);
        }
        path.add(start);
        Collections.reverse(path);
        return path;
    }

    @Override
    protected boolean isNextComponent(Graph graph, Object v) {
        return v==start;
    }

    public static void main(String[] args) {
        ImmutableGraph<String> graph =
                GraphBuilder.undirected()
                        .<String>immutable()
                        .addNode("A")


                        .putEdge("B", "F")
                        .putEdge("F", "E")

                        .putEdge("B", "C")
                        .putEdge("C", "D")
                        .putEdge("D", "K")
                        .putEdge("K", "E")
                        .putEdge("E", "C")

                        .addNode("F")
                        .putEdge("K", "J")
                        .build();
        BFS<String> bfs = new BFSFindPath<>("B", "E");
        System.out.println(" result "+  bfs.runBFS(graph));
    }

}
