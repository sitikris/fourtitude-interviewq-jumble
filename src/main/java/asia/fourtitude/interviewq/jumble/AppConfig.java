package asia.fourtitude.interviewq.jumble;

import asia.fourtitude.interviewq.jumble.core.JumbleEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public JumbleEngine jumbleEngine() {
        return new JumbleEngine();
    }

}
