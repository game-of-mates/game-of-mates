package au.gameofmates.graph.model;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import au.gameofmates.model.LoadableGraphSpec;
import au.gameofmates.model.RelationshipGraphSpec;

public class RelationshipGraphSpecTest {

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
    
    // Add 2 vertexes - Australia, NSW
    newGraph.addVertex("label", "country","id","urn:iso:std:iso:3166:AU","name","Australia");
    newGraph.addVertex("label", "province","id","urn:iso:std:iso:3166:-2:AU-NSW","name","New South Wales");
    newGraph.addVertex("label", "province","id","urn:iso:std:iso:3166:-2:AU-QLD","name","Queensland");
    
    
    // For each Relationship entry
     
     // Get the vertex for the Subject 
     //   - Find Vertex with Value of Relationship.{subject_id} 
     // Get the vertex for the Object - label + id
      // - Find Vertex with Value of Relationship.{object_id} 
     // Add an Edge between Subject --> Object, 
       // with label = {relation} and attributes
     
    
    
   }

}
