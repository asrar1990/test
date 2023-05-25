package com.bn.device;

import com.bn.common.dao.impl.CommonDaoImpl;
import com.bn.common.service.impl.ConfigServiceImpl;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(title = "Device API", version = "1.0.0"),
		servers = {@Server(url = "http://localhost:9002"), @Server(url = "https://nook-apim-dev-eastus.azure-api" +
				".net")},
tags = {@Tag(name="REST APIs", description = "Device related REST APIs"),
		@Tag(name="GPB APIs", description = "Device related GPB APIs")})
@ComponentScan(basePackageClasses = {DeviceServiceApplication.class, ConfigServiceImpl.class, CommonDaoImpl.class})
public class DeviceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeviceServiceApplication.class, args);
	}
}
