package energizeglobalservices.bankservice.config.feign;

import feign.Logger;
import feign.Request;
import feign.codec.ErrorDecoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    @Value("${feign.client.config.default.connectTimeout}")
    private int connectTimeOut;

    @Value("${feign.client.config.default.responseTimeout}")
    private int responseTimeout;

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }

    @Bean
    public Logger.Level loggerLevel() {
        return Logger.Level.FULL;

    }

    @Bean
    public Request.Options options() {
        return new Request.Options(
                connectTimeOut, responseTimeout
        );
    }

    @Bean
    public Logger feignLogger() {
        return new Slf4jLogger(FeignConfiguration.class);
    }
}
