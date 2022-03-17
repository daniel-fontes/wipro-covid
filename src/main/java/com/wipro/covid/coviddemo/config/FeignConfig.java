package com.wipro.covid.coviddemo.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import feign.Client;
import feign.Contract;
import feign.Feign;
import feign.Logger;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.form.spring.SpringFormEncoder;
import feign.httpclient.ApacheHttpClient;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;


public class FeignConfig {

	@Bean
	public Feign.Builder feignBuilder() {
		// default feignBuilder constructed here
		return Feign.builder().encoder(feignEncoder()).decoder(feignDecoder()).retryer(feignRetryer());
	}

	@Bean
	@ConditionalOnProperty(value = "aws.x-ray.enabled", havingValue = "true")
	public Client client() {
		return new ApacheHttpClient(HttpClientBuilder.create().build());
	}


	/**
	 * Defining the Feign {@link Contract} for the application.
	 * @return {@link Contract}
	 */
	@Bean
	public Contract feignContract() {
		return new SpringMvcContract();
	}

	/**
	 * This bean will log the headers, body, and metadata for both requests and responses,
	 * from feign client.
	 * @return {@link Logger.Level} as logging level for feign client.
	 */
	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}

	/**
	 * Creates an {@link ObjectMapper} for de/serialization.
	 * @return {@link ObjectMapper}. as Object mapper.
	 */
	public ObjectMapper objectMapper() {
		var om = new ObjectMapper();
		List<Module> modules = ObjectMapper.findModules();
		modules.stream().filter(JavaTimeModule.class::isInstance).map(JavaTimeModule.class::cast)
				.forEach((m) -> m.addSerializer(ZonedDateTime.class, getZonedDateTimeJsonSerializer()));
		om.registerModules(modules);
		om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		om.configure(WRITE_DATES_AS_TIMESTAMPS, false);
		om.disable(FAIL_ON_EMPTY_BEANS);
		om.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		return om;
	}

	/**
	 * JSON provider to be used for JSON processing, this used customized
	 * {@link ObjectMapper} from previous <code>clientJsonObjectMapper()</code>.
	 * @return {@link JacksonJsonProvider} with customized {@link ObjectMapper}
	 */
	@Bean
	@ConditionalOnMissingBean
	public JacksonJsonProvider clientJsonProvider() {
		return new JacksonJsonProvider(objectMapper());
	}

	/**
	 * {@link Encoder} with customized {@link ObjectMapper} for JSON Processing.
	 *
	 * <p>
	 * @return {@link JacksonEncoder} using customized {@link ObjectMapper}
	 */
	@Bean
	@ConditionalOnMissingBean
	public Encoder feignEncoder() {
		return new JacksonEncoder(objectMapper());
	}

	@Bean
	Encoder feignFormEncoder(ObjectFactory<HttpMessageConverters> converters) {
		return new SpringFormEncoder(new SpringEncoder(converters));
	}

	@Bean
	@ConditionalOnMissingBean
	public Decoder feignDecoder() {
		return new JacksonDecoder(objectMapper());
	}

	/**
	 * {@link Retryer} to be used by client APIs.
	 * @return default {@link Retryer}
	 */
	@Bean
	@ConditionalOnMissingBean
	public Retryer feignRetryer() {
		return Retryer.NEVER_RETRY;
	}



	private JsonSerializer<ZonedDateTime> getZonedDateTimeJsonSerializer() {
		return new JsonSerializer<ZonedDateTime>() {
			private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssX");

			@Override
			public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider serializers)
					throws IOException {
				gen.writeString(value.format(this.dtf));
			}
		};
	}

}
