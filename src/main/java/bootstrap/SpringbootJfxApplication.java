package bootstrap;

import com.aquafx_project.AquaFx;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringbootJfxApplication extends javafx.application.Application {

  private static final Logger log = LoggerFactory.getLogger(SpringbootJfxApplication.class);
  private static ApplicationContext applicationContext;

  public static void main(String[] args) {
    applicationContext = SpringApplication.run(SpringbootJfxApplication.class, args);

    launch(args); // Launch JavaFX app
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    CustomizationBean customizationBean = applicationContext.getBean(CustomizationBean.class);
    String url = "http://localhost:" + customizationBean.getPort() + "/angularjs/index.html";
    log.info(url);

    // create WebView with specified local content
    final javafx.scene.web.WebView root = new javafx.scene.web.WebView();
    root.getEngine().setJavaScriptEnabled(true);
    root.getEngine().load(url);

    primaryStage.setTitle("JFXApp");
    primaryStage.setScene(new javafx.scene.Scene(root, 1100, 820));
    primaryStage.show();

    // Shut down web app when window is closed
    primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
      @Override
      public void handle(WindowEvent event) {
        SpringApplication.exit(applicationContext, new ExitCodeGenerator() {
          @Override
          public int getExitCode() {
            return 0;
          }
        });
      }
    });

    AquaFx.style();
  }
}
