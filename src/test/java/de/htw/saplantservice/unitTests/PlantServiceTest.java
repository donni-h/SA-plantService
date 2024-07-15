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
import java.util.UUID;

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
        UUID expectedResultId = UUID.fromString("fd05631a-22d3-2376-b511-48fdd123b22c");
        UUID plant1Id = UUID.fromString("ad05631a-21d3-2376-b511-48fdd123b22c");
        final Plant expectedResult = new Plant(expectedResultId, "name", "latinName", 0.0f, 0,
                Category.GARTENPFLANZE, Height.S, WaterDemand.LOW, "description", "imageLink");
        final Plant plant1 = new Plant(plant1Id, "name", "latinName", 0.0f, 0,
                Category.GARTENPFLANZE, Height.S, WaterDemand.LOW, "description", "imageLink");

        // Configure IPlantRepository.findById(...).
        final Optional<Plant> plant = Optional.of(plant1);
        when(mockPlantRepository.findById(plant1Id)).thenReturn(plant);

        // Run the test
        final Plant result = plantServiceUnderTest.getPlantById(plant1Id);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetPlantById_IPlantRepositoryReturnsAbsent() {
        UUID plantId = UUID.fromString("fd05631a-22d3-2376-b511-48fdd123b22c");
        // Setup
        when(mockPlantRepository.findById(plantId)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> plantServiceUnderTest.getPlantById(plantId)).isInstanceOf(PlantIdNotFoundException.class);
    }

    @Test
    void testUpdatePlantAmount() {
        UUID expectedResultId = UUID.fromString("fd05631a-22d3-2376-b511-48fdd123b22c");
        UUID plant1Id = UUID.fromString("ad05631a-21d3-2376-b511-48fdd123b22c");
        // Setup
        final Plant expectedResult = new Plant(expectedResultId, "name", "latinName", 0.0f, 5,
                Category.GARTENPFLANZE, Height.S, WaterDemand.LOW, "description", "imageLink");
        final Plant plant1 = new Plant(plant1Id, "name", "latinName", 0.0f, 0,
                Category.GARTENPFLANZE, Height.S, WaterDemand.LOW, "description", "imageLink");

        // Configure IPlantRepository.findById(...).
        final Optional<Plant> plant = Optional.of(plant1);
        when(mockPlantRepository.findById(plant1Id)).thenReturn(plant);

        // Run the test
        final Plant result = plantServiceUnderTest.updatePlantAmount(plant1Id, 5);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testUpdatePlantAmount_IPlantRepositoryReturnsAbsent() {
        UUID plantId = UUID.fromString("fd05631a-22d3-2376-b511-48fdd123b22c");
        // Setup
        when(mockPlantRepository.findById(plantId)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> plantServiceUnderTest.updatePlantAmount(plantId, 0))
                .isInstanceOf(PlantIdNotFoundException.class);
    }

    @Test
    void testDeletePlant() {
        UUID plantId = UUID.fromString("fd05631a-22d3-2376-b511-48fdd123b22c");
        // Setup
        when(mockPlantRepository.existsById(plantId)).thenReturn(true);

        // Run the test
        plantServiceUnderTest.deletePlant(plantId);

        // Verify the results
        verify(mockPlantRepository).deleteById(plantId);
    }

    @Test
    void testDeletePlant_IPlantRepositoryExistsByIdReturnsFalse() {
        UUID plantId = UUID.fromString("fd05631a-22d3-2376-b511-48fdd123b22c");
        // Setup
        when(mockPlantRepository.existsById(plantId)).thenReturn(false);

        // Run the test
        assertThatThrownBy(() -> plantServiceUnderTest.deletePlant(plantId)).isInstanceOf(PlantIdNotFoundException.class);
    }
}
