package au.gameofmates.model.types;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.apache.tinkerpop.gremlin.object.model.Alias;
import org.apache.tinkerpop.gremlin.object.structure.Element;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/** Address record based on schema.org
 * 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Alias(label = "address")
@EqualsAndHashCode(of = {}, callSuper = false)
public class Address extends Element {

  String addressLocality, addressRegion, postOfficeBoxNumber,postalcode,streetAddress;
  String countryCode;
  
  List<String> countries = Arrays.asList(Locale.getISOCountries());
  
  public void setCountryCode(String cc)
  {
    if (!countries.contains(cc))
    {
      throw new RuntimeException("Country " + cc + " not in ISO-3166 Country codes.");
    }
    countryCode = cc;
  }
}
