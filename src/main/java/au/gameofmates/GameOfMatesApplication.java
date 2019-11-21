package au.gameofmates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Core Application class for GameOfMates.
 * 
 * @author neilpiper
 *
 */
@EnableAutoConfiguration
@SpringBootApplication
public class GameOfMatesApplication {


  /**
   * main method.
   * 
   * @param args startup arguments.
   */
  public static void main(String[] args) {
    SpringApplication.run(GameOfMatesApplication.class, args);

  }
}
