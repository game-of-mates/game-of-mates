package au.gameofmates.model.resources;

import org.apache.tinkerpop.gremlin.object.model.Alias;
import org.apache.tinkerpop.gremlin.object.structure.Vertex;
import au.gameofmates.model.types.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/** Place based on Schema.org
 * 
 * @author neilpiper
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Alias(label = "place")
@EqualsAndHashCode(of = {}, callSuper = false)
public class Place extends Vertex {

Number longitude,latitude;

Address address;
  
String description,alternateName,identifier,name;
  

}
