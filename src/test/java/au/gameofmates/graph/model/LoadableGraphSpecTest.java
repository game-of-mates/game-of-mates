package au.gameofmates.graph.model;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import au.gameofmates.model.LoadableGraphSpec;



public class LoadableGraphSpecTest {

  
  
  @Test
  public void testLoadSpec() throws IOException
  {
    ObjectMapper objectMapper = new ObjectMapper();

    final Graph newGraph = TinkerGraph.open();
    // user.dir is root dir of project
    
    ResourceLoader resourceLoader = new DefaultResourceLoader();
    Resource resource = resourceLoader.getResource("classpath:LoadableGraphSpec.json");
    
    String json = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream() ));
   
    List<LoadableGraphSpec> ppl2 = Arrays.asList(objectMapper.readValue(json, LoadableGraphSpec[].class));
    
    // Try Load the set of Vertexs into Gremplin
    for (LoadableGraphSpec spec: ppl2) {
    // Read in as CSV
      
      if (spec.getData_format().equals("text/csv"))
      {
        Resource res1 = resourceLoader.getResource("classpath:" + spec.getData_location());
        
        CSVReader csvReader = new CSVReader(new InputStreamReader(res1.getInputStream()));
        String [] keysLine;
        keysLine = csvReader.readNext();
        int keysLength = keysLine.length;
        
        String[] valueLine;
        
        while ((valueLine = csvReader.readNext()) != null) {
          List<Object> objList = new ArrayList<Object>();
          for (int i=0; i < keysLength; i++)
          {
            objList.add(keysLine[i]);
            objList.add(valueLine[i]);
          }
          objList.add("label");
          objList.add(spec.getLabel());
          Vertex v = newGraph.addVertex(objList.toArray());
          v.id();
          
        }
        csvReader.close();
      }
   
    }
    
    newGraph.traversal().V().fold().toStream().forEach(t -> t.forEach(y -> System.out.println(y.properties("name").next().value())));
    
    //newGraph.traversal().V().map(c -> c.get().value("name") + " is the Country name").forEachRemaining(System.out::println);
    
    
  }
  
  
  @Test
  public void testLoadSpecWithMethods() throws IOException
  {
    ObjectMapper objectMapper = new ObjectMapper();

    final Graph newGraph = TinkerGraph.open();
    // user.dir is root dir of project
    
    ResourceLoader resourceLoader = new DefaultResourceLoader();
    Resource resource = resourceLoader.getResource("classpath:LoadableGraphSpec.json");
    
    String json = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream() ));
   
    List<LoadableGraphSpec> ppl2 = Arrays.asList(objectMapper.readValue(json, LoadableGraphSpec[].class));
    
    // Try Load the set of Vertexs into Gremplin
    for (LoadableGraphSpec spec: ppl2) {
      
      List<List<Object>> arrayOfLines = spec.keyValueList();
      
      for (List<Object> vertexAttrs : arrayOfLines)
      {
        Vertex v = newGraph.addVertex( vertexAttrs.toArray() );
      }
      
      Vertex v = newGraph.vertices().next();
      String s = (String)v.property("name").value();
    }
    
    }

}
