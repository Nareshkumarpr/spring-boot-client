package com.example;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableCircuitBreaker
public class SpringBootClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootClientApplication.class, args);
	}
}


@RestController
class GreetingController {

	@RequestMapping("/call")
	@HystrixCommand(fallbackMethod = "defaultHello")
	public String hello() {
		RestTemplate template = new RestTemplate();
		return template.getForEntity( "http://spring-boot-demo/hello", String.class)
				.getBody();
	}

	public String defaultHello(){
		return "We couldn't process now";
	}
}