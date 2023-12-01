package br.inatel.labs.labmqtt.client;

import java.util.Random;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SensorTemperaturaPublisher
{
	public static void main(String[] args) throws MqttException
	{
		String publisherId = UUID.randomUUID().toString();
		try (IMqttClient publisher = new MqttClient(MyConstants.URI_BROKER, publisherId))
		{
			MqttConnectOptions options = new MqttConnectOptions();
			options.setAutomaticReconnect(true);
			options.setCleanSession(true);
			options.setConnectionTimeout(10);
			publisher.connect(options);
			
			for (int i = 0; i < 10; i++)
			{
				MqttMessage msg = getTemperatureMessage();
				msg.setQos(0);
				msg.setRetained(true);
				
				publisher.publish(MyConstants.TOPIC_SENSOR, msg);
				
				Thread.sleep(2000);
			}
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	private static MqttMessage getTemperatureMessage()
	{
		Random r = new Random();
		double temperatura = 80 + r.nextDouble() * 20.00;
		byte[] payload = String.format("T:%04.2f", temperatura).getBytes();
		
		return new MqttMessage(payload);
	}
}
