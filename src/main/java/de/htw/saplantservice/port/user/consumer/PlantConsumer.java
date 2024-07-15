package de.htw.saplantservice.port.user.consumer;

import de.htw.saplantservice.core.domain.service.interfaces.IPlantService;
import de.htw.saplantservice.port.dto.PlantChangeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlantConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlantConsumer.class);

    @Autowired
    private IPlantService plantService;

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consumeMessage(PlantChangeDto dto) {
        LOGGER.info(String.format("Received message -> %s", dto.toString()));
        plantService.updatePlantAmountWithDifference(dto.getPlantId(), dto.getChangeAmount());
    }
}
