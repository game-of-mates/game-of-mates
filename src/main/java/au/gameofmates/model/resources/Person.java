package au.gameofmates.model.resources;

import java.util.Date;
import org.apache.tinkerpop.gremlin.object.model.Alias;
import org.apache.tinkerpop.gremlin.object.model.OrderingKey;
import org.apache.tinkerpop.gremlin.object.model.PrimaryKey;
import org.apache.tinkerpop.gremlin.object.reflect.Label;
import org.apache.tinkerpop.gremlin.object.structure.Vertex;
import au.gameofmates.model.relationships.Knows;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Alias(label = "person")
@Accessors(fluent = true, chain = true)
@EqualsAndHashCode(of = {}, callSuper = true)
public class Person extends Vertex {
  public static ToVertex KnowsPeople =
      traversal -> traversal.out(Label.of(Knows.class)).hasLabel(Label.of(Person.class));

  @PrimaryKey
  private String name;

  @OrderingKey
  private Date birthDate;

  /** Static constructor for a Person
   * 
   * @param nm name
   * @param localDate birthdate
   * @return
   */
  public static Person of(String nm, Date localDate) {
    Person.PersonBuilder builder = Person.builder().name(nm).birthDate(localDate);
    return builder.build();
  }

  public String name() {
    // TODO Auto-generated method stub
    return name;
  }


}

