package de.htw.saplantservice.unitTests;

import de.htw.saplantservice.core.domain.service.interfaces.IPlantService;
import de.htw.saplantservice.port.dto.PlantChangeDto;
import de.htw.saplantservice.port.user.consumer.PlantConsumer;
import de.htw.saplantservice.port.user.exception.PlantIdNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlantConsumerTest {

    @Mock
    private IPlantService mockPlantService;

    @InjectMocks
    private PlantConsumer plantConsumerUnderTest;

    @Test
    void testConsumeMessage() {
        // Setup
        final PlantChangeDto dto = new PlantChangeDto();
        dto.setChangeAmount(0);
        dto.setPlantId(UUID.fromString("e7030ad9-7995-4961-8ddf-0de5d57b4bf6"));

        // Run the test
        plantConsumerUnderTest.consumeMessage(dto);

        // Verify the results
        verify(mockPlantService).updatePlantAmountWithDifference(
                UUID.fromString("e7030ad9-7995-4961-8ddf-0de5d57b4bf6"), 0);
    }

    @Test
    void testConsumeMessage_IPlantServiceThrowsPlantIdNotFoundException() {
        // Setup
        final PlantChangeDto dto = new PlantChangeDto();
        dto.setChangeAmount(0);
        dto.setPlantId(UUID.fromString("e7030ad9-7995-4961-8ddf-0de5d57b4bf6"));

        when(mockPlantService.updatePlantAmountWithDifference(UUID.fromString("e7030ad9-7995-4961-8ddf-0de5d57b4bf6"),
                0)).thenThrow(PlantIdNotFoundException.class);

        // Run the test
        assertThatThrownBy(() -> plantConsumerUnderTest.consumeMessage(dto))
                .isInstanceOf(PlantIdNotFoundException.class);
    }
}
