package bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.util.SocketUtils;

/**
 * @author Rowell Belen
 */
@Component
public class CustomizationBean implements EmbeddedServletContainerCustomizer {

  private static final Logger log = LoggerFactory.getLogger(CustomizationBean.class);
  private int defaultPort = SocketUtils.findAvailableTcpPort();

  @Override
  public void customize(ConfigurableEmbeddedServletContainer container) {
    try {
      // Use PORT provided in the System Environment
      int port = Integer.parseInt(System.getenv("PORT"));
      container.setPort(port);
      log.info("Using port: " + port);
      defaultPort = port;
    }
    catch (Exception e) {
      // Use default port
      log.info("Using port: " + defaultPort);
      container.setPort(defaultPort);
    }
  }

  public int getPort() {
    return defaultPort;
  }
}
