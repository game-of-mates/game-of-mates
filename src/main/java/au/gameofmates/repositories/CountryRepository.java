package au.gameofmates.repositories;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;
import au.gameofmates.config.DefaultConfiguration;
import au.gameofmates.openapi.model.Country;

@Component
public class CountryRepository implements Repository<Country, String> {

  
  private final DefaultConfiguration config;
  
  @org.springframework.beans.factory.annotation.Autowired
  public CountryRepository(DefaultConfiguration conf) {
      this.config = conf;
  }
  

  public Optional<Country> findById(String id) {
    return Optional.ofNullable(null);
  }

  public Optional<List<Country>> findAll() {


    GraphTraversalSource gts = config.getGraph().traversal();

    // Return all vertexs with label = 'country'
    return Optional.of(gts.V().has("label", "country").toStream().map(v -> vertexToCountry(v))
        .collect(Collectors.toList()));

  }

  public static Country vertexToCountry(Vertex v) {


    Country c = new Country();
    c.setName((String) v.property("name").value());
    c.setId((String) v.property("id").value());
    c.setShortName((String) v.property("shortName").value());

    return c;

  }

}
