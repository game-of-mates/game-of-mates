package au.gameofmates.model;

import com.github.jsonldjava.shaded.com.google.common.collect.Lists;
import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;



@Data
public abstract class AbstractVertexLoader implements VertexLoader {

  Logger logger = LoggerFactory.getLogger(AbstractVertexLoader.class);

  String id;
  // String urn_prefix;
  Number relative_load_priority;
  String label;
  String data_location;
  String data_format;
  Boolean temporal;
  String schema_ref;

  List<String> keys = new ArrayList<String>();


  @Override
  public List<String> keys() {

    return keys;
  }

  @Override
  public List<List<Object>> keyValueList() {
    if (data_format.equals("text/csv")) {
      ResourceLoader resourceLoader = new DefaultResourceLoader();
      Resource resource = resourceLoader.getResource(data_location);

      if (!resource.exists()) {
        logger.error("Resource 'data_location:' " + data_location + " not found");
        throw new RuntimeException("Resource " + data_location + " not found");
      }

      List<List<Object>> arrayOfLines = new ArrayList<List<Object>>();

      try {
        CSVReader csvReader = new CSVReader(new InputStreamReader(resource.getInputStream()));

        // firstline - keys
        keys = Lists.newArrayList(csvReader.readNext());

        int keysLength = keys.size();

        String[] valueLine;

        while ((valueLine = csvReader.readNext()) != null) {
          List<Object> objList = new ArrayList<Object>();
          for (int i = 0; i < keysLength; i++) {
            objList.add(keys.get(i));
            objList.add(valueLine[i]);
          }
          objList.add("label");
          objList.add(this.label);

          arrayOfLines.add(objList);
        }

        csvReader.close();
        return arrayOfLines;

      } catch (IOException ioe) {
        logger.error("CSV Read error reading " + data_location, ioe);
        throw new RuntimeException(ioe);
      }

    }
    return null;
  }


}
