package com.SAM2018;

import java.util.logging.Logger;

import com.SAM2018.appl.PaperManager;
import spark.TemplateEngine;
import spark.template.freemarker.FreeMarkerEngine;

import com.SAM2018.ui.WebServer;


/**
 * The entry point for the SAM2018 web application.
 *
 * @author <a href='mailto:add5980@rit.edu'>Andrew DiStasi</a>
 */
public final class Application {
  private static final Logger LOG = Logger.getLogger(Application.class.getName());

  //
  // Application Launch method
  //

  /**
   * Entry point for the SAM2018 web application.
   *
   * <p>
   * It wires the application components together.  This is an example
   * of <a href='https://en.wikipedia.org/wiki/Dependency_injection'>Dependency Injection</a>
   * </p>
   *
   * @param args
   *    Command line arguments; none expected.
   */
  public static void main(String[] args) {

    //Create PaperManager
    final PaperManager paperManager = new PaperManager();

    paperManager.loadApplication();
    paperManager.printUsersData();
    paperManager.printPapersData();

    // The application uses FreeMarker templates to generate the HTML
    // responses sent back to the client. This will be the engine processing
    // the templates and associated data.
    final TemplateEngine templateEngine = new FreeMarkerEngine();

    // inject the game center and freemarker engine into web server
    final WebServer webServer = new WebServer(paperManager, templateEngine);

    // inject web server into application
    final Application app = new Application(webServer);

    // start the application up
    app.initialize();
  }

  //
  // Attributes
  //

  private final WebServer webServer;

  //
  // Constructor
  //

  private Application(final WebServer webServer) {
    this.webServer = webServer;
  }

  //
  // Private methods
  //

  private void initialize() {
    LOG.fine("SAM2018 is initializing.");

    // configure Spark and startup the Jetty web server
    webServer.initialize();

    // other applications might have additional services to configure

    LOG.fine("SAM2018 initialization complete.");
  }

}