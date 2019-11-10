package au.gameofmates.graph.model;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.CharsetEncoder;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.FileCopyUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
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
     
    LoadableGraphSpec spec = objectMapper.readValue(json, LoadableGraphSpec.class);  

    spec.getId();
  }
}
