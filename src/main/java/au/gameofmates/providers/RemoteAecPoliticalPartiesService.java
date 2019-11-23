package au.gameofmates.providers;

import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Retrieve the official list of registered Political parties from the AEC.
 * 
 * @author neilpiper
 *
 */
@Service
public class RemoteAecPoliticalPartiesService {

  private static final Logger logger =
      LoggerFactory.getLogger(RemoteAecPoliticalPartiesService.class);
  
  private final RestTemplate aecPartiesTemplate;

  @Value("${au.gameofmates.parties.url}")
  private String au_gameofmates_parties_url;

  public RemoteAecPoliticalPartiesService(RestTemplateBuilder restBuilder) {
    this.aecPartiesTemplate = restBuilder.build();
  }

  /**
   * Return a list of political parties.
   * 
   * @return list of parties.
   */
  public List<String> getParties() {
    logger.info("Entry");

    String a = aecPartiesTemplate.getForObject(au_gameofmates_parties_url, String.class);
    // .getForObject(au_gameofmates_parties_url,String.class , null);
    return Arrays.asList(new String[] {a});
  }

}
