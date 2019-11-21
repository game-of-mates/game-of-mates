package au.gameofmates.model;

import lombok.Data;

@Data
public class LoadableGraphSpec extends AbstractVertexLoader {

  String urn_prefix;
  String source;

}
