package au.gameofmates.model;

import java.time.Instant;
import java.util.List;
import org.apache.tinkerpop.gremlin.object.structure.Connection;
import org.apache.tinkerpop.gremlin.object.structure.Edge;

public class Role extends Edge {
  
  private Instant startDate;
  private Instant endDate;
  private RoleType roleType;

  @Override
  protected List<Connection> connections() {
    // TODO Auto-generated method stub
    return null;
  }

}
