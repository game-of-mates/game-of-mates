package au.gameofmates.model;

import lombok.Data;

@Data
public class RelationshipGraphSpec {
  
  String id;
  //String urn_prefix;
  Number relative_load_priority;
  String subject_identifier;
  String object_identifier;
  String label;
  String data_location;
  String data_format;
  Boolean temporal;
  String schema_ref;
  
}
