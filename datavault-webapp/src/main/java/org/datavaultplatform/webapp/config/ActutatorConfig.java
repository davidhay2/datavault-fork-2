package org.datavaultplatform.webapp.config;

import java.time.Clock;
import org.datavaultplatform.webapp.actuator.BrokerStatusEndpoint;
import org.datavaultplatform.webapp.actuator.CurrentTimeEndpoint;
import org.datavaultplatform.webapp.actuator.MemoryInfoEndpoint;
import org.datavaultplatform.webapp.services.RestService;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ActutatorConfig {

  @Bean
  Clock clock() {
    return Clock.systemDefaultZone();
  }

  @Bean
  CurrentTimeEndpoint currentTime(Clock clock){
      return new CurrentTimeEndpoint(clock);
  }

  @Bean
  MemoryInfoEndpoint memoryInfo(Clock clock) {
    return new MemoryInfoEndpoint(clock);
  }

  @Profile("!standalone")
  @Bean
  BrokerStatusEndpoint brokerStatus(RestService restService) {
    return new BrokerStatusEndpoint(restService);
  }

  @Bean
  public InfoContributor springBootVersionInfoContributor() {
    return builder -> builder.withDetail("spring-boot.version", SpringBootVersion.getVersion());
  }
}
