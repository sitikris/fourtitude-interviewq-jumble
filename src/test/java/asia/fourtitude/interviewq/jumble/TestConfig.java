package asia.fourtitude.interviewq.jumble;

import asia.fourtitude.interviewq.jumble.core.JumbleEngine;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public JumbleEngine jumbleEngine() {
        return new JumbleEngine();
    }

}
