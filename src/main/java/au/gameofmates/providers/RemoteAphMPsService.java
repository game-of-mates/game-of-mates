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

/**
 * Remote service to collect a list of current MP's from APH.gov.au.
 * 
 * @author neilpiper
 *
 */
@Service
public class RemoteAphMPsService {

  private static final Logger logger = LoggerFactory.getLogger(RemoteAphMPsService.class);

  private final RestTemplate aphMpsTemplate;

  @Value("${au.gameofmates.membersparl.url}")
  private String au_gameofmates_mps_url;

  public RemoteAphMPsService(RestTemplateBuilder restBuilder) {
    this.aphMpsTemplate = restBuilder.build();
  }

  public String getMPNodesEdges() {

    logger.info("Entry");

    List<MemberOfParliamentCSV> members =
        aphMpsTemplate.execute(au_gameofmates_mps_url, HttpMethod.GET, null, clientHttpResponse -> {



          CsvToBean<MemberOfParliamentCSV> csvToBean =
              new CsvToBeanBuilder(new InputStreamReader(clientHttpResponse.getBody()))
                  .withType(MemberOfParliamentCSV.class).withIgnoreLeadingWhiteSpace(true).build();


          return csvToBean.parse();
          // return StreamUtils.copyToString(clientHttpResponse.getBody(),
          // Charset.defaultCharset());
        });



    return members.get(0).honorific;

  }



  public static class MemberOfParliamentCSV {

    // Honorific,Salutation,Post Nominals,Surname,First Name,Other Name,Preferred
    // Name,Initials,Electorate,State,Political Party,Gender,Telephone,Fax,Electorate Address Line
    // 1,Electorate Address Line 2,Electorate Suburb,Electorate State,Electorate PostCode,Electorate
    // Telephone,Electorate Fax,Electorate TollFree,Electorate Postal Address,Electorate Postal
    // Suburb,Electorate Postal State,Electorate Postal Postcode,Label Address,Label Suburb,Label
    // State,Label Postcode,Parliamentary Title,Ministerial Title
    @CsvBindByName(column = "Title")
    private String honorific;

    @CsvBindByName(column = "Surname")
    private String surname;

    @CsvBindByName(column = "First Name")
    private String firstname;

    @CsvBindByName(column = "Other Name")
    private String othername;

    @CsvBindByName(column = "Gender")
    private String gender;

//    @CsvBindByName(column = "Electorate")
//    private String electorate;
//
//    @CsvBindByName(column = "State")
//    private String state;
//
//    @CsvBindByName(column = "Political Party")
//    private String politicalParty;
//
//    @CsvBindByName(column = "Parliamentary Title")
//    private String parliamentaryTitle;
//
//    @CsvBindByName(column = "Ministerial Title")
//    private String ministerialTitle;

    /**
     * Override of toString method.
     * 
     */
    public String toString() {
      return new StringBuffer().append(honorific).append(" " + firstname).append(" " + surname)
          .append(" " + othername).append(" " + gender).toString();

    }
  }
}
