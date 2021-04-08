package com.rmendes.provider;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;

public class KafkaEventListener implements EventListenerProvider{
	
	private static final String KAFKA_BOOTSTRAP_SERVERS = "10.74.179.105:9092,10.74.182.227:9092,10.74.177.21:9092";
	private static final String KAFKA_TOPIC_EVENTS = "keycloak.events";
	private static final String KAFKA_TOPIC_ADMIN_EVENTS = "keycloak.admin.events";
	
	public void close() {
		
	}

	public void onEvent(Event event) {
		sendRecordKafka(KAFKA_TOPIC_EVENTS, eventStringfier(event));
	}

	public void onEvent(AdminEvent event, boolean bool) {
		sendRecordKafka(KAFKA_TOPIC_ADMIN_EVENTS, adminEventStringfier(event));
		
	}
	
	private void sendRecordKafka(String topic, String value) {
		Thread.currentThread().setContextClassLoader(null);
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(kafkaProperties());
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, value);
		producer.send(record);
		producer.flush();
		producer.close();
	}
	
	private Properties kafkaProperties() {
		Properties p = new Properties();
		p.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVERS);
		p.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		p.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		return p;
	}
	
	private String eventStringfier(Event e) {
		return "clientID: "+e.getClientId()+" Type: "+e.getType().toString()+" UserID: "+e.getUserId();
	}
	
	private String adminEventStringfier(AdminEvent e) {
		return "OpType: "+e.getOperationType().toString()+" auth_details: "+e.getAuthDetails().getUserId();
	}

}
