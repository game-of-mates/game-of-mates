package au.gameofmates.providers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import java.io.InputStreamReader;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.util.FileCopyUtils;

@RunWith(SpringRunner.class)
@RestClientTest({RemoteAphSenatorsService.class})
@TestPropertySource(properties="au.gameofmates.senators.url=https://www.aph.gov.au/~/media/senators")
public class RemoteAphSenatorsServiceTest {
  
  @Rule
  public ExpectedException thrown = ExpectedException.none();
  
  @Autowired
  private MockRestServiceServer server;
  
  @Autowired
  private RemoteAphSenatorsService service;
  
  @Before
  public void setUp() throws Exception {
      
    

    ResourceLoader resourceLoader = new DefaultResourceLoader();
    Resource resource = resourceLoader.getResource("classpath:/parliament/allsenel-export.csv");
    
    String csv = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream() ));
    
    
      this.server.expect(requestTo("https://www.aph.gov.au/~/media/senators"))
      .andRespond(withSuccess(csv, MediaType.TEXT_PLAIN));
       
      
  }
  
  @Test
  public void testServiceCalling() throws Exception {
    
    
    String response = this.service.getSenatorNodesEdges();
    //assertThat(response.equals(csv));
  }
  
//  @Test
//  public void testServiceCallout() throws Exception {
//    
//    this.thrown.expect(IllegalArgumentException.class);
//    this.service.getSenatorNodesEdges();
//    
//  }

}
