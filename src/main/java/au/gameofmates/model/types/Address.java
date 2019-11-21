package au.gameofmates.model.types;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.tinkerpop.gremlin.object.model.Alias;
import org.apache.tinkerpop.gremlin.object.structure.Element;


/**
 * Address record based on schema.org
 * 
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Alias(label = "address")
@EqualsAndHashCode(of = {}, callSuper = false)
public class Address extends Element {

  String addressLocality;
  String addressRegion;
  String postOfficeBoxNumber;
  String postalcode;
  String streetAddress;
  String countryCode;

  @Builder.Default
  List<String> countries = Arrays.asList(Locale.getISOCountries());

  /**
   * Sets the country code for an Address.
   * 
   * @param cc country code.
   */
  public void setCountryCode(String cc) {
    if (!countries.contains(cc)) {
      throw new RuntimeException("Country " + cc + " not in ISO-3166 Country codes.");
    }
    countryCode = cc;
  }
}
