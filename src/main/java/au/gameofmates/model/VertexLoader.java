package au.gameofmates.model;

import java.util.List;

/**
 * Interface for Loading a Vertex.
 * 
 * @author neilpiper
 *
 */
public interface VertexLoader {


  List<String> keys();

  List<List<Object>> keyValueList();

}
