package au.gameofmates.model.resources;

import au.gameofmates.model.types.Address;
import java.net.URL;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.tinkerpop.gremlin.object.model.Alias;
import org.apache.tinkerpop.gremlin.object.structure.Vertex;



@Builder
@NoArgsConstructor
@AllArgsConstructor
@Alias(label = "person")
@Accessors(fluent = true, chain = true)
@EqualsAndHashCode(of = {}, callSuper = true)
public class Organisation extends Vertex {

  Address address;
  LocalDate foundingDate;
  Place foundingLocation;
  LocalDate dissolutionDate;
  String duns;
  String email;
  // Person founder, Person funder; - relationship
  String globalLocationNumber; // https://www.gs1.org/docs/idkeys/GS1_Global_Location_Numbers.pdf
  String isicV4; // ISIC v4 -
  // https://en.wikipedia.org/wiki/International_Standard_Industrial_Classification
  String legalName; // Registered company name
  String leiCode; // ISO 17442
  URL logo;
  String naics; // North american Industry classification system
  String taxID; // Tax, ABN, etc;
  String vatID;
  String tickerSymbol; // ISO15022


}
