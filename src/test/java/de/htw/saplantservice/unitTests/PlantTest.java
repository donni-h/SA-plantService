package de.htw.saplantservice.unitTests;

import de.htw.saplantservice.core.domain.model.Category;
import de.htw.saplantservice.core.domain.model.Height;
import de.htw.saplantservice.core.domain.model.Plant;
import de.htw.saplantservice.core.domain.model.WaterDemand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlantTest {

    private Plant plantUnderTest0;
    private Plant plantUnderTest1;

    @BeforeEach
    void setUp() {
        plantUnderTest0 = new Plant(1L, "name", "latinName", 0.0f, 0, Category.GARTENPFLANZE, Height.S, WaterDemand.LOW,
                "description", "imageLink");
        plantUnderTest1 = new Plant(2L, "name", "latinName", 0.0f, 0, Category.GARTENPFLANZE, Height.S, WaterDemand.LOW,
                "description", "imageLink");
    }

    @Test
    void testToString() {
        assertThat(plantUnderTest0.toString()).isEqualTo("Plant{plantId=1, name='name', latinName='latinName', " +
                "description='description', price=0.0, category=GARTENPFLANZE, height=S, waterDemand=LOW," +
                " imageLink='imageLink', amount=0}");
    }

    @Test
    void testEquals() {
        // Run the test
        final boolean resultFalse = plantUnderTest0.equals("o");
        final boolean resultTrue = plantUnderTest1.equals(plantUnderTest1);

        // Verify the results
        assertThat(resultFalse).isFalse();
        assertThat(resultTrue).isTrue();
    }
}
