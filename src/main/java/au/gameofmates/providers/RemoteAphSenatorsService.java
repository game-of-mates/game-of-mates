package au.gameofmates.providers;

import java.io.InputStreamReader;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Getter;
import lombok.Setter;

/**
 * Remote service to collect a list of current Senators from APH.gov.au.
 * 
 * @author neilpiper
 *
 */
@Service
public class RemoteAphSenatorsService {

  private static final Logger logger = LoggerFactory.getLogger(RemoteAphSenatorsService.class);

  private final RestTemplate aphSenatorsTemplate;

  @Value("${au.gameofmates.senators.url}")
  private String au_gameofmates_senators_url;

  public RemoteAphSenatorsService(RestTemplateBuilder restBuilder) {
    this.aphSenatorsTemplate = restBuilder.build();
  }

  // List<VertexLoader>

  public String getSenatorNodesEdges() {

    logger.info("Entry");

    List<SenatorsCSV> senators = aphSenatorsTemplate.execute(au_gameofmates_senators_url,
        HttpMethod.GET, null, clientHttpResponse -> {



          CsvToBean<SenatorsCSV> csvToBean =
              new CsvToBeanBuilder(new InputStreamReader(clientHttpResponse.getBody()))
                  .withType(SenatorsCSV.class).withIgnoreLeadingWhiteSpace(true).build();


          return csvToBean.parse();
          // return StreamUtils.copyToString(clientHttpResponse.getBody(),
          // Charset.defaultCharset());
        });



    return senators.get(0).title;

    // aphSenatorsTemplate.execute( au_gameofmates_senators_url, HttpMethod.GET, null,
    // clientHttpResponse -> {
    // File ret = File.createTempFile("download", "tmp");
    // StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
    // return ret;
    // });

    // String a= aphSenatorsTemplate.getForObject(au_gameofmates_senators_url,String.class);
    // return csv;
  }


  public static class SenatorsCSV {



    // Title,Salutation,Surname,c,Other Name,Preferred Name,Initials,Post
    // Nominals,State,Political Party,Gender,Electorate Address Line 1,Electorate Address Line
    // 2,Electorate Suburb,Electorate State,Electorate PostCode,Electorate Telephone,Electorate
    // Fax,Electorate TollFree,Label Address,Label Suburb,Label State,Label postcode,Parliamentary
    // Titles

    @CsvBindByName(column = "Title")
    @Getter
    @Setter
    private String title;

    // private String salutation;

    @Getter
    @Setter
    @CsvBindByName(column = "Surname")
    private String surname;
    //
    // @CsvBindByName(column = "First Name")
    // private String firstname;
    //
    // @CsvBindByName(column = "Gender")
    // private String gender;


    // private String othername;
    // private String preferredName;
    // private String initials;
    // private String postnominals;

    @CsvBindByName(column = "State")
    private String state;

    // @CsvBindByName(column = "Political Party")
    // private String politicalparty;


    public String getStateID() {
      return "urn:iso:std:iso:3166:-2:AU-" + state.toUpperCase();
    }

    // private String AddressLine1;
    // private String ElectorateAddressLine2;
    // private String ElectorateSuburb;
    // private String ElectorateState;
    // private String ElectoratePostCode;
    // private String ElectorateTelephone;
    // private String ElectorateFax;
    // private String ElectorateTollFree;
    // private String LabelAddress;
    // private String LabelSuburb;
    // private String LabelState;
    // private String LabelPostcode;
    // private String ParliamentaryTitles;

  }

}
