package br.edu.fatecpg.deolhonolixo.infrastructure.config.mqtt;

import br.edu.fatecpg.deolhonolixo.core.usecase.truck.TruckHistorySaveCase;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import java.util.UUID;

@EnableIntegration
@Slf4j
@Configuration
public class MqttBrokerConfig {
    private final TruckHistorySaveCase truckHistorySaveCase;
    @Value("${deolhonolixo.mqtt.url}")
    private String mqttUrl;
    @Value("${deolhonolixo.mqtt.topic}")
    private String[] mqttTopic;
    @Value("${deolhonolixo.mqtt.qos}")
    private int mqttQos;

    public MqttBrokerConfig(TruckHistorySaveCase truckHistorySaveCase) {
        this.truckHistorySaveCase = truckHistorySaveCase;
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();

        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{mqttUrl});
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);

        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("deolhonolixo-" + UUID.randomUUID(), mqttClientFactory(), mqttTopic);
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(mqttQos);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return message -> {
            String payload = message.getPayload().toString();
            String topic = (String) message.getHeaders().getOrDefault("mqtt_receivedTopic", message.getHeaders().get(MqttHeaders.TOPIC));

            if (topic != null && payload != null) {
                String[] splitTopic = topic.split("/");
                String[] coords = payload.split(",");

                if (splitTopic.length >= 2 && coords.length >= 2) {
                    String licensePlate = splitTopic[1];
                    Double lat = Double.valueOf(coords[0]);
                    Double lon = Double.valueOf(coords[1]);

                    truckHistorySaveCase.execute(licensePlate, lon, lat);

                    log.info("Geolocalização processada para a placa: {}", licensePlate);
                }
            } else {
                throw new MessagingException("Erro ao processar mensagem de geolocalização");
            }
        };
    }
}
