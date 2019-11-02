package au.gameofmates.providers;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.File;
import java.util.Collections;
import java.util.List;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.ResIterator;
import org.apache.jena.rdf.model.Resource;
import org.springframework.core.io.ClassPathResource;

public class AustraliaParser {


  public static void main(String[] args) {
    Model model = ModelFactory.createDefaultModel();

    AustraliaParser parser = new AustraliaParser();
    List<String[]> listOfRows =
        parser.loadManyToManyRelationship("/locations/iso-3166-australia-raw.csv");

    // Iterate through the csv
    for (String[] strArry : listOfRows) {
      // Row == strArray , 0 = subject, 1 = predicate, 2 = Object
      // if '2' startsWith urn - need a relationship not a property

      // Does Resource exist?
      if (model.getResource(strArry[0]) == null) {
        model.createResource(strArry[0]).addProperty(model.createProperty(strArry[1]), strArry[2]);
      } else {
        Resource r1 = model.getResource(strArry[0]);
        if (!strArry[2].startsWith("urn:")) {
          
          r1.addProperty(model.createProperty(strArry[1]), model.getResource(strArry[2]));

        } else {
          r1.addProperty(model.createProperty(strArry[1]), strArry[2]);
        }
      }



    }

    Resource sa = model.getResource("urn:iso:std:iso:3166:-2:AU-SA");
    



    ResIterator iter = model.listSubjectsWithProperty(model.getProperty("name"),"");

    while (iter.hasNext()) {

      // Statement stmt = iter.nextStatement(); // get next statement
      Resource subject = iter.next(); // stmt.getSubject(); // get the subject
      // Property predicate = stmt.getPredicate(); // get the predicate
      // RDFNode object = stmt.getObject(); // get the object

      System.out.print(subject.toString());
      // System.out.print(" " + predicate.toString() + " ");
      // if (object instanceof Resource) {
      // System.out.print(object.toString());
      // } else {
      // object is a literal
      // System.out.print(" \"" + object.toString() + "\"");
      // }

      System.out.println(" .");
    }


  }

  public <T> List<T> loadObjectList(Class<T> type, String fileName) {
    try {
      CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
      CsvMapper mapper = new CsvMapper();
      File file = new ClassPathResource(fileName).getFile();
      MappingIterator<T> readValues = mapper.reader(type).with(bootstrapSchema).readValues(file);
      return readValues.readAll();
    } catch (Exception e) {
      // logger.error("Error occurred while loading object list from file " + fileName, e);
      return Collections.emptyList();
    }

  }


  public List<String[]> loadManyToManyRelationship(String fileName) {
    try {
      CsvMapper mapper = new CsvMapper();
      CsvSchema bootstrapSchema = CsvSchema.emptySchema().withSkipFirstDataRow(true);
      mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
      File file = new ClassPathResource(fileName).getFile();
      MappingIterator<String[]> readValues =
          mapper.reader(String[].class).with(bootstrapSchema).readValues(file);
      return readValues.readAll();
    } catch (Exception e) {
      return Collections.emptyList();
    }
  }
}

