package com.qduong.graph.dfs;

import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graph;
import com.qduong.graph.Label;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/*
 *  Developed by Quang Duong on 2/17/21, 3:24 PM
 */

public class DepthFirstSearch<N> {
    protected void initResult(Graph graph){}
    protected void postInitVertices(N u){}
    protected void postInitEdges(EndpointPair<N> e){}
    protected void preComponentVisit(Graph graph, N v){}
    protected void postComponentVisit(Graph graph, N v){}
    protected boolean isNextComponent(Graph graph, N v){
        return getLabel(v) == Label.UNEXPLORED;
    }
    protected Object result(Graph graph){
        return graph;
    }
    protected void startVertexVisit(Graph graph, N v){}
    protected void preEdgeTraversal(Graph graph, N v, EndpointPair<N> e, N w){}
    protected void preDiscoveryTraversal(Graph graph, N v, EndpointPair<N> e, N w){}
    protected void postDiscoveryTraversal(Graph graph, N v, EndpointPair<N> e, N w){}
    protected void backTraversal(Graph graph, N v, EndpointPair<N> e, N w){}
    protected void finishVertexVisit(Graph graph, N s){}
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
                callDFSComponent(graph, v);
                postComponentVisit(graph, v);
            }
        });
        return result(graph);
    }

    private void callDFSComponent(Graph graph, N v){
        setLabel(v, Label.VISITED);
        startVertexVisit(graph, v);
        for (Iterator<EndpointPair<N>> iterator = graph.incidentEdges(v).iterator(); iterator.hasNext();){
            EndpointPair<N> e = iterator.next();
            if(getLabel(e) == Label.UNEXPLORED){
                N w = e.adjacentNode(v);
                preEdgeTraversal(graph, v, e, w);
                if(getLabel(w) == Label.UNEXPLORED){
                    setLabel(e, Label.DISCOVERY);
                    preDiscoveryTraversal(graph, v, e, w);
                    callDFSComponent(graph, w);
                    postDiscoveryTraversal(graph, v, e, w);
                }else{
                    setLabel(e, Label.BACK);
                    backTraversal(graph, v, e, w);
                }
            }
        }
        finishVertexVisit(graph, v);
    }

}
