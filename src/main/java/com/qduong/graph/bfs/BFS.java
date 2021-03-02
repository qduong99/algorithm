package com.qduong.graph.bfs;/*
 *  Developed by Quang Duong on 2/16/21, 3:00 PM
 * Copyright (c) DnqSoft 2020. All rights reserved.
 */

import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graph;
import com.qduong.graph.Label;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/*
 *  Developed by Quang Duong on 2/17/21, 11:06 AM
 * Copyright (c) DnqSoft 2020. All rights reserved.
 */

public abstract class BFS<N> {

    protected void initResult(Graph graph){}
    protected void preComponentVisit(Graph graph, N v){}
    protected void postComponentVisit(Graph graph, N v){}
    protected void postInitVertices(N u){}
    protected void postInitEdges(EndpointPair<N> e){}
    protected void startBFS(Graph graph, N s){}
    protected void preVertexVisit(Graph graph, N v){}
    protected void preEdgeVisit(Graph graph, N v, EndpointPair<N> e){}
    protected void preDiscEdgeVisit(Graph graph, N v, EndpointPair<N> e, N w){}
    protected void postDiscEdgeVisit(Graph graph, N v, EndpointPair<N> e, N w){}
    protected void crossEdgeVisit(Graph graph, N v, EndpointPair<N> e, N w) {}
    protected void postVertexVisit(Graph graph, N v){}
    protected void finishBFS(Graph graph, N s){}
    protected Object result(Graph graph){
        return graph;
    }

    protected boolean isNextComponent(Graph graph, N v){
        return getLabel(v) == Label.UNEXPLORED;
    }

    public Object runBFS(Graph<N> graph) {
        initResult(graph);
        graph.nodes().forEach( u -> {
            setLabel(u, Label.UNEXPLORED);
            postInitVertices(u);
        });
        graph.edges().forEach(e -> {
            setLabel(e, Label.UNEXPLORED);
            postInitEdges(e);
        });
        graph.nodes().forEach( v -> {
           if(isNextComponent(graph, v)){
               preComponentVisit(graph, v);
               callBFSComponent(graph, v);
               postComponentVisit(graph, v);
           }
        });
        return result(graph);
    }

    private void callBFSComponent(Graph graph, N s){
        startBFS(graph, s);
        setLabel(s, Label.VISITED);
        LinkedList<N> l = new LinkedList();
        l.addLast(s);
        while (!l.isEmpty()) {
            N v = l.removeFirst();
            preVertexVisit(graph, v);
            Iterator<EndpointPair<N>> incidentEdges = graph.incidentEdges(v).iterator();
            for (Iterator<EndpointPair<N>> iterator = incidentEdges; iterator.hasNext();){
                    EndpointPair<N> e = iterator.next();
                    preEdgeVisit(graph, v, e);
                    if (getLabel(e) == Label.UNEXPLORED){
                        N w = e.adjacentNode(v);
                        if (getLabel(w) == Label.UNEXPLORED) {
                            preDiscEdgeVisit(graph, v, e, w);
                            setLabel(e, Label.DISCOVERY);
                            setLabel(w, Label.VISITED);
                            l.addLast(w);
                            postDiscEdgeVisit(graph, v, e, w);
                        } else {
                            setLabel(e, Label.CROSS);
                            crossEdgeVisit(graph, v, e, w);

                        }
                    }
                }
            postVertexVisit(graph, v);
        }
        finishBFS(graph, s);

    }

    protected Map<N, Label> labelNodes = new HashMap<>();
    public Label getLabel(N e){
        return labelNodes.get(e);
    }
    public void setLabel(N e, Label label){
        labelNodes.put(e, label);
    }

    protected Map<EndpointPair<N>, Label> labelEdges = new HashMap<>();
    public void setLabel(EndpointPair<N> e, Label label){
        labelEdges.put(e, label);
    }
    public Label getLabel(EndpointPair<N> e){
        return labelEdges.get(e);
    }

    protected Map<N, EndpointPair<N>> parentNodes = new HashMap<>();
    public EndpointPair<N> getParent(N v){
        return parentNodes.get(v);
    }
    public void setParent(N v,  EndpointPair<N> e){
        parentNodes.put(v, e);
    }

}

