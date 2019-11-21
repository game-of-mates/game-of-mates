package au.gameofmates.config;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import au.gameofmates.model.LoadableGraphSpec;
import au.gameofmates.model.RelationshipGraphSpec;

@Configuration
@EnableConfigurationProperties
public class DefaultConfiguration {
  
  Logger logger = LoggerFactory.getLogger(DefaultConfiguration.class);

  @Value("${au.gameofmates.loadspec}")
  private String vertexLoadSpec;
  
  @Value("${au.gameofmates.edgespec}")
  private String edgeLoadSpec;
  
  @Bean
  public List<LoadableGraphSpec> vertexesToLoad()
  {
    ObjectMapper objectMapper = new ObjectMapper();
    ResourceLoader resourceLoader = new DefaultResourceLoader();
    Resource resource = resourceLoader.getResource(vertexLoadSpec);
    
    String json;
    try {
      json = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream() ));
      List<LoadableGraphSpec> ppl2 = Arrays.asList(objectMapper.readValue(json, LoadableGraphSpec[].class));
      
      logger.info("Load record of " + ppl2.size() + " elements.");
      return ppl2;
    } catch (IOException e) {
      logger.error("Could not read the vertexLoad specification specified by attribute `au.gameofmates.loadspec`",e);
      throw new RuntimeException(e);
    }
   
     
  }
  
  @Bean
  public List<RelationshipGraphSpec> edgesToLoad()
  {
    ObjectMapper objectMapper = new ObjectMapper();
    ResourceLoader resourceLoader = new DefaultResourceLoader();
    Resource resource = resourceLoader.getResource(edgeLoadSpec);
    String json;
    
    try {
      json = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream() ));
      List<RelationshipGraphSpec> ppl2 = Arrays.asList(objectMapper.readValue(json, RelationshipGraphSpec[].class));
      
      logger.info("Load record of " + ppl2.size() + " relationship record elements.");
      return ppl2;
    } catch (IOException e) {
      logger.error("Could not read the edgeLoad specification specified by attribute `au.gameofmates.loadspec`",e);
      throw new RuntimeException(e);
    }
    
  }
    
  @Bean
  public Graph initialiseGraph(List<LoadableGraphSpec> specsToLoad, List<RelationshipGraphSpec> edges)
  {
    logger.info("Classes " + vertexLoadSpec + " : " + edgeLoadSpec);
    
    Graph newGraph = TinkerGraph.open();
    
 // Try Load the set of Vertexs into Gremplin
    for (LoadableGraphSpec spec: specsToLoad) {
      
      List<List<Object>> arrayOfLines = spec.keyValueList();
      
      for (List<Object> vertexAttrs : arrayOfLines)
      {
        Vertex v = newGraph.addVertex( vertexAttrs.toArray() );
        logger.debug( "Added " + v.label() + " id: " + v.value("id"));
        
      }
      

     
    }

    loadRelationships(newGraph, edges);
    logger.info(newGraph.toString());
    
    return newGraph;
  }
  
  protected void loadRelationships(Graph g, List<RelationshipGraphSpec> rels)
  {
    for ( RelationshipGraphSpec rgs: rels)
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
          
          g.traversal().V().filter( t -> t.get().value("id").equals(subjID)).forEachRemaining( y -> vertexList.add(y) );
          g.traversal().V().filter( t -> t.get().value("id").equals(objID)).forEachRemaining( y -> vertexList.add(y) );
          
          if (vertexList.size() < 2)
          {
            logger.error("Could not find Vertices for edge between subject: " + subjID + " object: "+ objID);
            throw new RuntimeException("Invalid relationship specification for subject " + subjID + " Object: " + objID );
          }
          
          Edge edge = vertexList.get(0).addEdge(rgs.getLabel(), vertexList.get(1), relValues.toArray());
          logger.info("Added Edge " + edge.label() + " " + edge.outVertex().value("id") + " between " + edge.inVertex().value("id") );
       }
       
    }
  }

  
}
