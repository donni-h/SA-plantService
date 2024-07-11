package de.htw.saplantservice.integrationTests;

import de.htw.saplantservice.core.domain.model.Category;
import de.htw.saplantservice.core.domain.model.Height;
import de.htw.saplantservice.core.domain.model.Plant;
import de.htw.saplantservice.core.domain.model.WaterDemand;
import de.htw.saplantservice.core.domain.service.interfaces.IPlantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static junit.framework.TestCase.*;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PlantRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>( "postgres:latest");

    @Autowired
    private IPlantRepository iPlantRepository;

    @Test
    void connectionEstablished(){
        assertTrue(postgres.isCreated());
        assertTrue(postgres.isRunning());
    }

    @BeforeEach
    void setUp(){
        Plant plant = new Plant("Pflanzus Longus", "Pflanzisimus Longimus", 5.40f,
                15, Category.ZIMMERPFLANZE, Height.L, WaterDemand.MEDIUM, "coole Pflanze",
                "cooles Bild");
        iPlantRepository.save(plant);
    }

    @Test
    void findByNameTest_PlantExists(){
        List<Plant> plants = iPlantRepository.findByName("Pflanzus Longus");

        assertTrue(plants.size() == 1);

        Plant plantByName = plants.get(0);
        assertNotNull(plantByName);
        assertEquals("Pflanzus Longus", plantByName.getName());
        assertEquals("Pflanzisimus Longimus", plantByName.getLatinName());
        assertEquals("coole Pflanze", plantByName.getDescription());
        assertEquals(5.40f, plantByName.getPrice());
        assertEquals(Category.ZIMMERPFLANZE, plantByName.getCategory());
        assertEquals(Height.L, plantByName.getHeight());
        assertEquals(WaterDemand.MEDIUM, plantByName.getWaterDemand());
        assertEquals("cooles Bild", plantByName.getImageLink());
        assertEquals(Integer.valueOf(15), plantByName.getAmount());
    }

    @Test
    void findByNameTest_PlantDoesNotExist(){
        List<Plant> plants = iPlantRepository.findByName("Kebabpflanze");

        assertTrue(plants.isEmpty());
    }

    @Test
    void findByCategoryTest_PlantExists(){
        List<Plant> plants = iPlantRepository.findByCategory(Category.ZIMMERPFLANZE);

        assertTrue(plants.size() == 1);

        Plant plantByName = plants.get(0);
        assertNotNull(plantByName);
        assertEquals("Pflanzus Longus", plantByName.getName());
        assertEquals("Pflanzisimus Longimus", plantByName.getLatinName());
        assertEquals("coole Pflanze", plantByName.getDescription());
        assertEquals(5.40f, plantByName.getPrice());
        assertEquals(Category.ZIMMERPFLANZE, plantByName.getCategory());
        assertEquals(Height.L, plantByName.getHeight());
        assertEquals(WaterDemand.MEDIUM, plantByName.getWaterDemand());
        assertEquals("cooles Bild", plantByName.getImageLink());
        assertEquals(Integer.valueOf(15), plantByName.getAmount());
    }

    @Test
    void findByCategoryTest_PlantDoesNotExists(){
        List<Plant> plants = iPlantRepository.findByCategory(Category.BAUM);

        assertTrue(plants.isEmpty());
    }
}
