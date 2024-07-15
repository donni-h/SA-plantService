package de.htw.saplantservice.port.user.consumer;

import de.htw.saplantservice.core.domain.model.Plant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PlantConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlantConsumer.class);

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consumeMessage(Plant plant) {
        LOGGER.info(String.format("Received message -> %s", plant.toString()));
    }
}
