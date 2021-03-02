package com.qduong.graph.bfs;/*
 *  Developed by Quang Duong on 2/16/21, 10:42 PM
 * Copyright (c) DnqSoft 2020. All rights reserved.
 */

import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.ImmutableGraph;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 *  Developed by Quang Duong on 2/17/21, 11:06 AM
 * Copyright (c) DnqSoft 2020. All rights reserved.
 */

public class BFSLabelComponent<N> extends BFS<N>{
    protected Map<N, Integer> labelComponent = new HashMap<>();
    int componentIndex = 0;

    @Override
    protected void postComponentVisit(Graph graph, Object v) {
        componentIndex++;
    }

    @Override
    protected void preVertexVisit(Graph graph, N v) {
        setLabelComponent(v ,componentIndex);
    }

    @Override
    protected void initResult(Graph graph) {
        componentIndex=0;
    }

    public void setLabelComponent(N e, int label) {
        labelComponent.put(e, label);
    }

    @Override
    protected Object result(Graph graph) {
        return labelComponent;
    }

    public static void main(String[] args) {
        ImmutableGraph<String> graph =
                GraphBuilder.undirected()
                        .<String>immutable()
                        .addNode("A")
                        .putEdge("B", "C")
                        .putEdge("C", "D")
                        .putEdge("D", "E")
                        .putEdge("E", "C")
                        .addNode("F")
                        .putEdge("K", "J")
                        .build();
        Set<String> successorsOfTwo = graph.successors("D"); // returns {3}
        System.out.println(" successor" + successorsOfTwo);

        BFS<String> bfs = new BFSLabelComponent<>();
        System.out.println(" result "+  bfs.runBFS(graph));
    }
}
