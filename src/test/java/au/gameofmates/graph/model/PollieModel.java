package au.gameofmates.graph.model;

import au.gameofmates.model.relationships.Knows;
import au.gameofmates.model.resources.Person;
import java.time.LocalDate;
import java.util.Date;
import org.apache.tinkerpop.gremlin.object.structure.Graph;

import lombok.Data;

@Data
public class PollieModel {
  
  private final Graph graph;
  
  public Person marko, vadas, josh, peter;
  
  public static PollieModel of(Graph graph) {
    PollieModel pollies = new PollieModel(graph);
    pollies.generateGameOfMates();
    return pollies;
  }

  private void generateGameOfMates() {
    // Things / Resources / People

    
    
    graph
    .addVertex(Person.of("marko", new Date() )).as(this::setMarko)
    .addVertex(Person.of("vadas", new Date() )).as(this::setVadas)
    .addVertex(Person.of("josh", new Date() )).as(this::setJosh);
    
    
    graph
    .addEdge(Knows.of(0.5d), marko, vadas)
    .addEdge(Knows.of(0.7d) ,josh,vadas);
    
    //josh = graph.get("josh", Person.class);
    
  }

}
