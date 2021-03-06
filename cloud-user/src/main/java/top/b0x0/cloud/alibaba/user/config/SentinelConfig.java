package top.b0x0.cloud.alibaba.user.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * sentinel config
 *
 * @author TANG
 * @since 2021-07-18
 * @since JDK1.8
 */
@Configuration
public class SentinelConfig {
    /**
     * spring-cloud-starter-alibaba-sentinel
     *
     * @return /
     */
    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

    @Bean
    public MyBlockExceptionHandler webCallbackManager() {
        MyBlockExceptionHandler myBlockExceptionHandler = new MyBlockExceptionHandler();
        return myBlockExceptionHandler;
    }

}
