package au.gameofmates.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.io.graphml.GraphMLWriter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GremlinController {
  
  private Graph graph;
  
  public GremlinController( Graph g)
  {
    this.graph = g;
  }
  
  @ResponseBody
  @RequestMapping(value = "/graphextract", method = RequestMethod.GET, produces = "application/xml")
  public void graphextract(HttpServletResponse response) throws IOException {
    
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    
    try {
      GraphMLWriter.build().create().writeGraph(response.getOutputStream(), graph);
    } catch (IOException e) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());

    }
    
    //return outputStream.toByteArray();
 

  }

}
