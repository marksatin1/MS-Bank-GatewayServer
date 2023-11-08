package com.cloudbank.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator cloudbankRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder
				.routes()
				.route(p -> p
						.path("/cloudbank/accounts/**")
						.filters(f -> f.rewritePath("/cloudbank/accounts/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://ACCOUNTS"))
				.route(p -> p
						.path("/cloudbank/loans/**")
						.filters(f -> f.rewritePath("/cloudbank/loans/(?<segment>.*)","/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://LOANS"))
				.route(p -> p
						.path("/cloudbank/cards/**")
						.filters(f -> f.rewritePath("/cloudbank/cards/(?<segment>.*)","/${segment}")
						.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://CARDS"))
				.build();

	}
}
