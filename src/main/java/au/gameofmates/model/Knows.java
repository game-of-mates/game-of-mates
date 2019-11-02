package au.gameofmates.model;

import java.time.Instant;
import java.util.List;
import org.apache.tinkerpop.gremlin.object.reflect.Label;
import org.apache.tinkerpop.gremlin.object.structure.Connection;
import org.apache.tinkerpop.gremlin.object.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Direction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true, chain = true)
@EqualsAndHashCode(of = {}, callSuper = true)
public class Knows extends Edge {
  
  /**
   * Go to the {@link Person}s you know, assuming you're currently selected.
   */
  public final static ToVertex KnowsWho = traversal -> traversal
      .outE(Label.of(Knows.class))
      .toV(Direction.IN);
  
  public final static ToVertex KnownBy = traversal -> traversal
      .inE(Label.of(Knows.class))
      .toV(Direction.OUT);

  private Double weight;

  private Instant since;
      
  public static Knows of(double weight) {
    return of(weight, null);
  }

  public static Knows of(Instant since) {
    return of(null, since);
  }

  public static Knows of(Double weight, Instant since) {
    return Knows.builder().weight(weight).since(since).build();
  }

  @Override
  protected List<Connection> connections() {
    return Connection.list(Person.class, Person.class);
  }
}
