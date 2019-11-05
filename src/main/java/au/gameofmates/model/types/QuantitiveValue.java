package au.gameofmates.model.types;

import org.apache.tinkerpop.gremlin.object.structure.Element;

public class QuantitiveValue extends Element {

  Number maxValue, minValue;
  String unitCode, unitText;
  String value;
  
  // valueReference Enum, PropertyValue, QuantiativeValue, QualitativeValue, StructuredValue
}
