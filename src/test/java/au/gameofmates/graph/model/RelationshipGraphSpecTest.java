package au.gameofmates.graph.model;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import au.gameofmates.model.RelationshipGraphSpec;

public class RelationshipGraphSpecTest {

  Logger logger = LoggerFactory.getLogger(RelationshipGraphSpecTest.class);
  
  @Test
  public void testRelationshipLoad() throws IOException {
    
    ObjectMapper objectMapper = new ObjectMapper();

    final Graph newGraph = TinkerGraph.open();
    // user.dir is root dir of project
    
    ResourceLoader resourceLoader = new DefaultResourceLoader();
    Resource resource = resourceLoader.getResource("classpath:RelationshipGraphSpec.json");
    
    String json = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream() ));
   
    List<RelationshipGraphSpec> ppl2 = Arrays.asList(objectMapper.readValue(json, RelationshipGraphSpec[].class));
    
    //"subject_identifier": "country_id", // urn:iso:std:iso:3166:AU
    //"object_identifier": "province_id", // urn:iso:std:iso:3166:-2:AU-NSW
    
    logger.info("Adding Australia, NSW, QLD");
    // Add 2 vertexes - Australia, NSW
    newGraph.addVertex("label", "country","id","urn:iso:std:iso:3166:AU","name","Australia");
    newGraph.addVertex("label", "province","id","urn:iso:std:iso:3166:-2:AU-NSW","name","New South Wales");
    newGraph.addVertex("label", "province","id","urn:iso:std:iso:3166:-2:AU-QLD","name","Queensland");
    
    for ( RelationshipGraphSpec rgs: ppl2)
    {
       // Values - key Value List (Read)
       List<List<Object>> arrayCSV = rgs.keyValueList();
       
       // For each Relationship entry
       for ( List<Object> relValues : arrayCSV)
       {
         // Build a KV Map for easier access
          Map<String,String> kvMap = new HashMap<String,String>( );
          Iterator<Object> it = relValues.iterator();
          while(it.hasNext()) {
            kvMap.put((String)it.next(), (String)it.next());
          }
          
          // Get the Subject ID
          String subjID = kvMap.get(rgs.getSubject_identifier());
          String objID = kvMap.get(rgs.getObject_identifier());
          
          List<Vertex> vertexList = new ArrayList<Vertex>();
          
          newGraph.traversal().V().filter( t -> t.get().value("id").equals(subjID)).forEachRemaining( y -> vertexList.add(y) );
          newGraph.traversal().V().filter( t -> t.get().value("id").equals(objID)).forEachRemaining( y -> vertexList.add(y) );
          
          Edge edge = vertexList.get(0).addEdge(rgs.getLabel(), vertexList.get(1), relValues.toArray());
          logger.info("Added Edge " + edge.label() + " " + edge.outVertex().value("name") + " to " + edge.inVertex().value("name") );
       }
       
    
    //newGraph.traversal().V().filter(i -> i.get().property("id").value().equals(  ))
    // For each Relationship entry
    //ppl2.stream().
     // Get the vertex for the Subject 
     //   - Find Vertex with Value of Relationship.{subject_id} 
     // Get the vertex for the Object - label + id
      // - Find Vertex with Value of Relationship.{object_id} 
     // Add an Edge between Subject --> Object, 
       // with label = {relation} and attributes
    }
    
    
   }

}
