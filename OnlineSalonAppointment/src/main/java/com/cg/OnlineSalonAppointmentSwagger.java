package com.cg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger Configuration
 * 
 * @author :Peddoju Teja Pranavi
 * @version :1.0
 * @since :2020-12-01
 */
@Configuration
@EnableSwagger2
@Import({ SpringDataRestConfiguration.class, BeanValidatorPluginsConfiguration.class })
public class OnlineSalonAppointmentSwagger {

	@Bean
	public Docket rtoApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.cg.controller")).paths(PathSelectors.any()).build()
				.apiInfo(getApiInfo());
	}

	// create api metadata that goes at the top of the generated page
	private ApiInfo getApiInfo() {
		return new ApiInfoBuilder().title("Online Salon Appointment API").version("1.0")
				.description("API for online salon appointment.").contact(new Contact("TEAM 1",
						"http://localhost:8100/OnlineSalonAppointment/swagger-ui/", "salonservice.office@gmail.com"))
				.license("Apache License Version 2.0").build();
	}
}
