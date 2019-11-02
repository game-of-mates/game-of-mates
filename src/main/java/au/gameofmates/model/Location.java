package au.gameofmates.model;

import java.time.Instant;
import lombok.Data;
import org.apache.tinkerpop.gremlin.object.model.Alias;
import org.apache.tinkerpop.gremlin.object.model.OrderingKey;
import org.apache.tinkerpop.gremlin.object.model.PropertyValue;
import org.apache.tinkerpop.gremlin.object.structure.Element;


@Data
@Alias(label = "location")
public class Location extends Element {

  @OrderingKey
  @PropertyValue
  private String name;
  @OrderingKey
  private Instant startTime;
  private Instant endTime;

}
