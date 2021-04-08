package com.rmendes.provider;

import org.keycloak.Config.Scope;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class KafkaCustomProviderFactory implements EventListenerProviderFactory{

	public void close() {
		// TODO Auto-generated method stub
		
	}

	public EventListenerProvider create(KeycloakSession arg0) {

		return new KafkaEventListener();
	}

	public String getId() {
		return "kafka-custom-listener";
	}

	public void init(Scope arg0) {
		// TODO Auto-generated method stub
		
	}

	public void postInit(KeycloakSessionFactory arg0) {
		// TODO Auto-generated method stub
		
	}
	

}
