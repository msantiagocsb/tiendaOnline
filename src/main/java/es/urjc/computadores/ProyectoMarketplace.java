package es.urjc.computadores;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;

@SpringBootApplication
@EnableHazelcastHttpSession
public class ProyectoMarketplace {

	private static final Log LOG = LogFactory.getLog(ProyectoMarketplace.class);

	public static void main(String[] args) {
		SpringApplication.run(ProyectoMarketplace.class, args);
	}
	
	@Bean
	public Config config() {
		Config config = new Config();
		JoinConfig joinConfig = config.getNetworkConfig().getJoin();
		joinConfig.getMulticastConfig().setEnabled(false);
		
		
		List<String> serversList = new ArrayList<>(); 
		serversList.add("web");
		serversList.add("web2");
		
		joinConfig.getTcpIpConfig().setEnabled(true).setMembers(serversList);
		return config;
	}
	
	@Bean
	public CacheManager cacheManager() {
		LOG.info("Activando cache...");
		return new ConcurrentMapCacheManager("cache_marketplace");
		
	}
	
}
