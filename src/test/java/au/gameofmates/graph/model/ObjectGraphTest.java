package au.gameofmates.graph.model;

import static org.apache.tinkerpop.gremlin.object.structure.Graph.Should.CREATE;
import static org.apache.tinkerpop.gremlin.object.structure.Graph.Should.IGNORE;
import static org.apache.tinkerpop.gremlin.object.structure.Graph.Should.INSERT;
import static org.apache.tinkerpop.gremlin.object.structure.Graph.Should.MERGE;
import static org.apache.tinkerpop.gremlin.object.structure.Graph.Should.REPLACE;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.apache.tinkerpop.gremlin.object.provider.GraphFactory;
import org.apache.tinkerpop.gremlin.object.structure.Graph;
import org.apache.tinkerpop.gremlin.object.traversal.Query;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ObjectGraphTest {

  public ObjectGraphTest()
  {
    query = null;
    graph = null;
  }
  
  protected Query query;

  protected final Graph graph;
  

  private static final GraphFactory FACTORY = GraphFactory.of(TinkerGraph.open().traversal());
  
 
  @Test
  public void testGetFirstNames() {

    try{
    PollieModel model = PollieModel.of(FACTORY.graph());

    List<String> names = query.by(g -> g.V().values("name")).list(String.class);
    assertNotNull(names);
    assertTrue(names.contains(model.vadas.name()));
    assertTrue(names.contains(model.marko.name()));
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw e;
    }
  }

}
