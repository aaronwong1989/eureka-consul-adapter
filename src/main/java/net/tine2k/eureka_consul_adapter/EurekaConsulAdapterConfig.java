package net.tine2k.eureka_consul_adapter;

import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import net.tine2k.eureka_consul_adapter.controller.AgentController;
import net.tine2k.eureka_consul_adapter.controller.ServiceController;
import net.tine2k.eureka_consul_adapter.event.EurekaInstanceEventHandler;
import net.tine2k.eureka_consul_adapter.event.ServiceChangeDetector;
import net.tine2k.eureka_consul_adapter.mapper.ServiceMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EurekaConsulAdapterConfig {

    @Bean
    @ConditionalOnMissingBean
    public AgentController agentController() {
        return new AgentController();
    }

    @Bean
    @ConditionalOnMissingBean
    public ServiceController serviceController(PeerAwareInstanceRegistry peerAwareInstanceRegistry) {
        return new ServiceController(peerAwareInstanceRegistry, serviceChangeDetector(), serviceMapper());
    }

    @Bean
    @ConditionalOnMissingBean
    public ServiceMapper serviceMapper() {
        return new ServiceMapper();
    }

    @Bean
    @ConditionalOnMissingBean
    public ServiceChangeDetector serviceChangeDetector() {
        return new ServiceChangeDetector();
    }

    @Bean
    public EurekaInstanceEventHandler EurekaInstanceEventHandler() {
        return new EurekaInstanceEventHandler(serviceChangeDetector());
    }
}