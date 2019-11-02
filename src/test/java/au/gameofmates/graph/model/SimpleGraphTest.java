package au.gameofmates.graph.model;


import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.Test;

public class SimpleGraphTest {

  
  @Test
  public void vertexTest()
  {
    
    Graph graph = TinkerGraph.open();
    GraphTraversalSource g = graph.traversal();
    
        
    Vertex v1 = g.addV("person").property("name","marko").next();
    Vertex v2 = g.addV("person").property("name","stephen").next();
    g.V(v1).addE("knows").to(v2).property("weight",0.75).iterate();
    
    g.V().valueMap(true);
  }
}
