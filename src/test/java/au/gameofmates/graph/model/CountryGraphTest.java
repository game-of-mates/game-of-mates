package au.gameofmates.graph.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.tinkerpop.gremlin.process.traversal.Path;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;
import org.junit.Test;

public class CountryGraphTest {


  @Test
  public void testCreateGraph() throws IOException, ParseException {
    TinkerGraph tg;
    GraphTraversalSource g;

    // Create Graph
    tg = TinkerGraph.open();
    g = tg.traversal();

    FileReader fileReader = new FileReader(
        "./src/main/resources/locations/iso-3166-countries.csv");

    BufferedReader bufferedReader = new BufferedReader(fileReader);
    String line;
    String[] values;


    SimpleDateFormat yyyyMMDDDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    // Add Countries (Vertexes)
    bufferedReader.readLine();
    while ((line = bufferedReader.readLine()) != null) {
      // System.out.println(line);
      values = line.split(",");
      // addElements(g,values[0],values[1],values[2], values[3], null);
      tg.addVertex("label", "country", "id", values[0], "name", values[1], "shortName", values[2],
          "effectiveFromDate", yyyyMMDDDateFormat.parse(values[3]));
    }

    bufferedReader.close();

    // Provinces (Vertexes)
    fileReader = new FileReader(
        "./src/main/resources/locations/iso-3166-2-provinces.csv");
    bufferedReader = new BufferedReader(fileReader);
    bufferedReader.readLine();
    while ((line = bufferedReader.readLine()) != null) {
      // System.out.println(line);
      values = line.split(",");
      // id,name,shortName,abbreviation,category
      tg.addVertex("label", "province", "id", values[0], "name", values[1], "shortName", values[2],
          "abbreviation", values[3], "category", values[4]);
    }
    bufferedReader.close();
    // Provinces to Countries
    fileReader = new FileReader(
        "./src/main/resources/locations/edges/australia-countries-to-provinces.csv");
    bufferedReader = new BufferedReader(fileReader);
    bufferedReader.readLine();
    while ((line = bufferedReader.readLine()) != null) {
      // System.out.println(line);
      values = line.split(",");
      // province-id,relation,country-id,effectiveFromDate

      Vertex country = g.V().has("id", values[2]).next();
      Vertex province = g.V().has("id", values[0]).next();

      province.addEdge(values[1], country, "effectiveDate", yyyyMMDDDateFormat.parse(values[3]));

    }
    bufferedReader.close();

    // Divisions to Provinces
    fileReader = new FileReader(
        "./src/main/resources/parliament/AU-electorates.csv");
    bufferedReader = new BufferedReader(fileReader);
    bufferedReader.readLine();
    while ((line = bufferedReader.readLine()) != null) {
      // System.out.println(line);
      values = line.split(",");
      // id,name,province,profileURL,lastDistributionURL,lastDistributionYear

      Vertex division =
          tg.addVertex("label", "electoralDivision", "id", values[0], "name", values[1]);

      try {
        Vertex province = g.V().has("id", values[2]).next();

        division.addEdge("divisionOf", province);
        
      } catch (NoSuchElementException nse) {
        System.out.println("Couldn't find province for " + values[2]);
        throw nse;
      }


    }

    displayGraph(g);
    bufferedReader.close();

  }

  public void displayGraph(GraphTraversalSource g) {
    Long c;
    c = g.V().count().next();
    System.out.println("Graph contains " + c + " vertices");
    c = g.E().count().next();
    System.out.println("Graph contains " + c + " edges");

    List<Path> edges = g.V().outE().inV().path().by("label").by().toList();

    for (Path p : edges) {
      System.out.println(p);
    }
  }

  // Add the specified vertices and edge. Do not add anything that
  // already exists.
  public boolean addElements(GraphTraversalSource g, String id, String name, String shortName,
      String effectiveFromDate, String effectiveToDate) {
    if (g == null) {
      return false;
    }


    g.addV(id).property("id", id).property("name", name).property("shortName", shortName);



    return true;
  }
}
