package com.todo.note.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*************************************************************************************************************
 *
 * purpose:user configuration
 * 
 * @author sowjanya467
 * @version 1.0
 * @since 19-07-18
 *
 **************************************************************************************************/

@Configuration
public class UserConfiguration {
	/**
	 * Password encoder
	 * 
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * modelMapper configuration
	 * 
	 * @return
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@SuppressWarnings("deprecation")
	@Bean
	public JedisConnectionFactory connectionFactory() {
		JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
		connectionFactory.setHostName("localhost");
		connectionFactory.setPort(6379);
		return connectionFactory;
	}

	@Bean
	public RedisTemplate redisTemplate() {
		RedisTemplate redisTemplate = new RedisTemplate();
		redisTemplate.setConnectionFactory(connectionFactory());
		return redisTemplate;
	}

	/*
	 * @Bean public JwtTokens getTokens() {
	 * 
	 * return new JwtTokens();
	 * 
	 * }
	 * 
	 * @Value("") String queueName;
	 * 
	 * @Value("") String exchanger;
	 * 
	 * @Value("") private String routingKey;
	 * 
	 * @Bean Queue queue() { return new Queue(queueName, false); }
	 * 
	 * @Bean DirectExchange exchange() { return new DirectExchange(exchanger); }
	 * 
	 * @Bean Binding binding(Queue queue, DirectExchange exchange) { return
	 * BindingBuilder.bind(queue).to(exchange).with(routingKey); }
	 * 
	 * @Bean public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory)
	 * { final RabbitTemplate rabbitTemplate = new
	 * RabbitTemplate(connectionFactory);
	 * rabbitTemplate.setMessageConverter(jsonMessageConverter()); return
	 * rabbitTemplate; }
	 * 
	 * @Bean public MessageConverter jsonMessageConverter() { return new
	 * Jackson2JsonMessageConverter(); }
	 * 
	 * @Bean public SimpleRabbitListenerContainerFactory
	 * jsaFactory(ConnectionFactory connectionFactory,
	 * SimpleRabbitListenerContainerFactoryConfigurer configurer) {
	 * SimpleRabbitListenerContainerFactory factory = new
	 * SimpleRabbitListenerContainerFactory(); configurer.configure(factory,
	 * connectionFactory); factory.setMessageConverter(jsonMessageConverter());
	 * return factory; }
	 */

	/*
	 * @Bean public static PropertySourcesPlaceholderConfigurer propertyConfigurer()
	 * { Resource resource; String activeProfile;
	 * 
	 * PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer =
	 * new PropertySourcesPlaceholderConfigurer();
	 * 
	 * // get active profile activeProfile =
	 * System.getProperty("spring.profiles.active");
	 * 
	 * // choose different property files for different active profile if
	 * ("development".equals(activeProfile)) { resource = new
	 * ClassPathResource("/static/files/applicationdevelopment.properties");
	 * System.out.println("currently in " + activeProfile + " profile"); } else if
	 * ("test".equals(activeProfile)) { resource = new
	 * ClassPathResource("/static/files/applicationtest.properties");
	 * System.out.println("currently in " + activeProfile + " profile"); } else {
	 * resource = new
	 * ClassPathResource("static/files/applicationproduction.properties");
	 * System.out.println("currently in " + activeProfile + " profile"); }
	 * 
	 * // load the property file
	 * propertySourcesPlaceholderConfigurer.setLocation(resource);
	 * 
	 * return propertySourcesPlaceholderConfigurer; }
	 */
}
