package de.htw.saplantservice.unitTests;

import de.htw.saplantservice.core.domain.model.Category;
import de.htw.saplantservice.core.domain.model.Height;
import de.htw.saplantservice.core.domain.model.Plant;
import de.htw.saplantservice.core.domain.model.WaterDemand;
import de.htw.saplantservice.core.domain.service.impl.PlantService;
import de.htw.saplantservice.core.domain.service.interfaces.IPlantRepository;
import de.htw.saplantservice.port.user.exception.PlantIdNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlantServiceTest {

    @Mock
    private IPlantRepository mockPlantRepository;

    private PlantService plantServiceUnderTest;

    @BeforeEach
    void setUp() {
        plantServiceUnderTest = new PlantService(mockPlantRepository);
    }

    @Test
    void testGetPlantById() {
        // Setup
        final Plant expectedResult = new Plant(1L, "name", "latinName", 0.0f, 0,
                Category.GARTENPFLANZE, Height.S, WaterDemand.LOW, "description", "imageLink");
        final Plant plant1 = new Plant(2L, "name", "latinName", 0.0f, 0,
                Category.GARTENPFLANZE, Height.S, WaterDemand.LOW, "description", "imageLink");

        // Configure IPlantRepository.findById(...).
        final Optional<Plant> plant = Optional.of(plant1);
        when(mockPlantRepository.findById(2L)).thenReturn(plant);

        // Run the test
        final Plant result = plantServiceUnderTest.getPlantById(2L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetPlantById_IPlantRepositoryReturnsAbsent() {
        // Setup
        when(mockPlantRepository.findById(523425L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> plantServiceUnderTest.getPlantById(523425L)).isInstanceOf(PlantIdNotFoundException.class);
    }

    @Test
    void testUpdatePlantAmount() {
        // Setup
        final Plant expectedResult = new Plant(1L, "name", "latinName", 0.0f, 5,
                Category.GARTENPFLANZE, Height.S, WaterDemand.LOW, "description", "imageLink");
        final Plant plant1 = new Plant(2L, "name", "latinName", 0.0f, 0,
                Category.GARTENPFLANZE, Height.S, WaterDemand.LOW, "description", "imageLink");

        // Configure IPlantRepository.findById(...).
        final Optional<Plant> plant = Optional.of(plant1);
        when(mockPlantRepository.findById(2L)).thenReturn(plant);

        // Run the test
        final Plant result = plantServiceUnderTest.updatePlantAmount(2L, 5);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testUpdatePlantAmount_IPlantRepositoryReturnsAbsent() {
        // Setup
        when(mockPlantRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> plantServiceUnderTest.updatePlantAmount(0L, 0))
                .isInstanceOf(PlantIdNotFoundException.class);
    }

    @Test
    void testDeletePlant() {
        // Setup
        when(mockPlantRepository.existsById(0L)).thenReturn(true);

        // Run the test
        plantServiceUnderTest.deletePlant(0L);

        // Verify the results
        verify(mockPlantRepository).deleteById(0L);
    }

    @Test
    void testDeletePlant_IPlantRepositoryExistsByIdReturnsFalse() {
        // Setup
        when(mockPlantRepository.existsById(0L)).thenReturn(false);

        // Run the test
        assertThatThrownBy(() -> plantServiceUnderTest.deletePlant(0L)).isInstanceOf(PlantIdNotFoundException.class);
    }
}
