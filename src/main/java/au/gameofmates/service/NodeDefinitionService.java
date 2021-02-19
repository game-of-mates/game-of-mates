package au.gameofmates.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import au.gameofmates.config.DefaultConfiguration;
import au.gameofmates.model.LoadableGraphSpec;
import au.gameofmates.openapi.api.NodeDefinitionsApiDelegate;
import au.gameofmates.openapi.model.NodeDefinition;
import au.gameofmates.openapi.model.NodeDefinition.DataFormatEnum;

@Service
public class NodeDefinitionService implements NodeDefinitionsApiDelegate {
  
  @Autowired
  DefaultConfiguration config;

  @Override
  public ResponseEntity<NodeDefinition> nodeDefinitionsIdGet(String id) {
    
    
//    return Optional
//        .ofNullable( userRepository.findOne(id) )
//        .map( user -> ResponseEntity.ok().body(user) )          //200 OK
//        .orElseGet( () -> ResponseEntity.notFound().build() );  //404 Not found
//}
//    
    Optional<LoadableGraphSpec> nodeMatch = config.getVertexDefinitions().stream().filter( t -> t.getId().equals( id) ).findFirst();
    
    
    if (nodeMatch.isPresent())
    {
      return ResponseEntity.ok( mapNodeDefinitionToLoadableGraphSpec(nodeMatch.get()) );
    }
    else
    {
      return ResponseEntity.notFound().build();
    }
    
    
  }
  
  private NodeDefinition mapNodeDefinitionToLoadableGraphSpec(LoadableGraphSpec lds)
  {
    
    
    NodeDefinition nd = new NodeDefinition();
    nd.setDataFormat(DataFormatEnum.CSV);
    nd.setId(lds.getId());
    nd.setLabel(lds.getLabel());
    nd.setRelativeLoadPriority(lds.getRelative_load_priority().intValue());
    nd.setSchemaRef(lds.getSchema_ref());
    nd.setTemporal(lds.getTemporal());
    nd.setUrnPrefix(lds.getUrn_prefix());
    return nd;
    
  }


}
