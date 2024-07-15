package de.htw.saplantservice.integrationTests;

import de.htw.saplantservice.core.domain.model.Category;
import de.htw.saplantservice.core.domain.model.Height;
import de.htw.saplantservice.core.domain.model.Plant;
import de.htw.saplantservice.core.domain.model.WaterDemand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class PlantControllerTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>( "postgres:latest");

    @Autowired
    TestRestTemplate restTemplate;

    Plant plant0;
    Plant plant1;
    Plant plant2;
    Plant plant3;
    Plant plant4;
    Plant plant5;
    Plant plant6;
    Plant plant7;
    Plant plant8;
    Plant plant9;

    @BeforeEach
    void setUp(){
        restTemplate.exchange("/plants", HttpMethod.DELETE, null, Void.class);

        plant0 = new Plant("Rose", "Rosa", 5.40f,
                15, Category.ZIMMERPFLANZE, Height.S, WaterDemand.MEDIUM, "coole Pflanze",
                "cooles Bild");
        plant1 = new Plant("Sonnenblume", "Helianthus annuus", 7f,
                3, Category.ZIMMERPFLANZE, Height.M, WaterDemand.LOW, "coole Pflanze",
                "cooles Bild");
        plant2 = new Plant("Tulpe", "Tulipa", 10f,
                20, Category.ZIMMERPFLANZE, Height.S, WaterDemand.HIGH, "coole Pflanze",
                "cooles Bild");
        plant3 = new Plant("Narzisse", "Narcissus", 4f,
                12, Category.ZIMMERPFLANZE, Height.M, WaterDemand.MEDIUM, "coole Pflanze",
                "cooles Bild");
        plant4 = new Plant("Lavendel", "Lavandula", 2f,
                11, Category.ZIMMERPFLANZE, Height.M, WaterDemand.HIGH, "coole Pflanze",
                "cooles Bild");
        plant5 = new Plant("Bambus", "Bambusoideae", 50f,
                18, Category.ZIMMERPFLANZE, Height.L, WaterDemand.MEDIUM, "coole Pflanze",
                "cooles Bild");
        plant6 = new Plant("Ahorn", "Acer", 100f,
                30, Category.BAUM, Height.L, WaterDemand.HIGH, "coole Pflanze",
                "cooles Bild");
        plant7 = new Plant("Birke", "Betula", 120f,
                320, Category.BAUM, Height.L, WaterDemand.HIGH, "coole Pflanze",
                "cooles Bild");
        plant8 = new Plant("Liguster", "Ligustrum", 80f,
                67, Category.HECKENPFLANZE, Height.M, WaterDemand.HIGH, "coole Pflanze",
                "cooles Bild");
        plant9 = new Plant("Eibe", "Taxus baccata", 23f,
                74, Category.HECKENPFLANZE, Height.M, WaterDemand.LOW, "coole Pflanze",
                "cooles Bild");

        restTemplate.exchange("/plant", HttpMethod.POST, new HttpEntity<>(plant0), Plant.class);
        restTemplate.exchange("/plant", HttpMethod.POST, new HttpEntity<>(plant1), Plant.class);
        restTemplate.exchange("/plant", HttpMethod.POST, new HttpEntity<>(plant2), Plant.class);
        restTemplate.exchange("/plant", HttpMethod.POST, new HttpEntity<>(plant3), Plant.class);
        restTemplate.exchange("/plant", HttpMethod.POST, new HttpEntity<>(plant4), Plant.class);
        restTemplate.exchange("/plant", HttpMethod.POST, new HttpEntity<>(plant5), Plant.class);
        restTemplate.exchange("/plant", HttpMethod.POST, new HttpEntity<>(plant6), Plant.class);
        restTemplate.exchange("/plant", HttpMethod.POST, new HttpEntity<>(plant7), Plant.class);
        restTemplate.exchange("/plant", HttpMethod.POST, new HttpEntity<>(plant8), Plant.class);
        restTemplate.exchange("/plant", HttpMethod.POST, new HttpEntity<>(plant9), Plant.class);
    }

    @Test
    void connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    //-------------------------------------------------------------------------
    //CREATEPLANT() TESTS:
    //-------------------------------------------------------------------------

    @Test
    @Rollback
    void createPlantTest_ValidPlant(){
        Plant createdPlant = new Plant("Rose", "Rosa", 5.40f,
                15, Category.ZIMMERPFLANZE, Height.S, WaterDemand.MEDIUM, "coole Pflanze",
                "cooles Bild");

        ResponseEntity<Plant> response = restTemplate.exchange("/plant", HttpMethod.POST,
                new HttpEntity<>(createdPlant), Plant.class);
        Plant responsePlant = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertTrue(responsePlant.equals(createdPlant));

        //check with getAll()

        ResponseEntity<List<Plant>> getAllResponse = restTemplate.exchange("/plants", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> plants = getAllResponse.getBody();
        assertTrue(plants.size() == 11);
    }

    @Test
    @Rollback
    void createPlantTest_NullPlant(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Plant> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange("/plant", HttpMethod.POST,
                request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        //check with getAll()

        ResponseEntity<List<Plant>> getAllResponse = restTemplate.exchange("/plants", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> plants = getAllResponse.getBody();
        assertTrue(plants.size() == 10);
    }

    @Test
    @Rollback
    void createPlantTest_GivenPlantID(){ //ID should not be given
        Plant plant = new Plant(UUID.randomUUID(), "Pflanzus Longus", "Pflanzisimus Longimus", 5.40f,
                15, Category.ZIMMERPFLANZE, Height.L, WaterDemand.MEDIUM, "coole Pflanze",
                "cooles Bild");

        ResponseEntity<String> response = restTemplate.exchange("/plant", HttpMethod.POST,
                new HttpEntity<>(plant), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertEquals("Plant ID is created automatically and should not be given.", response.getBody());

        //check with getAll()

        ResponseEntity<List<Plant>> getAllResponse = restTemplate.exchange("/plants", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> plants = getAllResponse.getBody();
        assertTrue(plants.size() == 10);
    }

    @Test
    @Rollback
    void createPlantTest_NullPlantName(){
        Plant plant = new Plant(null, "Pflanzisimus Longimus", 5.40f,
                15, Category.ZIMMERPFLANZE, Height.L, WaterDemand.MEDIUM, "coole Pflanze",
                "cooles Bild");

        ResponseEntity<String> response = restTemplate.exchange("/plant", HttpMethod.POST,
                new HttpEntity<>(plant), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        //check with getAll()

        ResponseEntity<List<Plant>> getAllResponse = restTemplate.exchange("/plants", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> plants = getAllResponse.getBody();
        assertTrue(plants.size() == 10);
    }

    @Test
    @Rollback
    void createPlantTest_BlankPlantName(){
        Plant plant = new Plant("", "Pflanzisimus Longimus", 5.40f,
                15, Category.ZIMMERPFLANZE, Height.L, WaterDemand.MEDIUM, "coole Pflanze",
                "cooles Bild");

        ResponseEntity<String> response = restTemplate.exchange("/plant", HttpMethod.POST,
                new HttpEntity<>(plant), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        //check with getAll()

        ResponseEntity<List<Plant>> getAllResponse = restTemplate.exchange("/plants", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> plants = getAllResponse.getBody();
        assertTrue(plants.size() == 10);
    }

    @Test
    @Rollback
    void createPlantTest_TooLongPlantName(){
        Plant plant = new Plant("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                "Pflanzisimus Longimus", 5.40f, 15, Category.ZIMMERPFLANZE, Height.L,
                WaterDemand.MEDIUM, "coole Pflanze", "cooles Bild");

        ResponseEntity<String> response = restTemplate.exchange("/plant", HttpMethod.POST,
                new HttpEntity<>(plant), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        //check with getAll()

        ResponseEntity<List<Plant>> getAllResponse = restTemplate.exchange("/plants", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> plants = getAllResponse.getBody();
        assertTrue(plants.size() == 10);
    }

    @Test
    @Rollback
    void createPlantTest_NullLatinName(){
        Plant plant = new Plant("Rose", null, 5.40f, 15, Category.ZIMMERPFLANZE,
                Height.S, WaterDemand.MEDIUM, "coole Pflanze", "cooles Bild");

        ResponseEntity<String> response = restTemplate.exchange("/plant", HttpMethod.POST,
                new HttpEntity<>(plant), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        //check with getAll()

        ResponseEntity<List<Plant>> getAllResponse = restTemplate.exchange("/plants", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> plants = getAllResponse.getBody();
        assertTrue(plants.size() == 10);
    }

    @Test
    @Rollback
    void createPlantTest_BlankLatinName(){
        Plant plant = new Plant("Rose", "", 5.40f, 15, Category.ZIMMERPFLANZE,
                Height.S, WaterDemand.MEDIUM, "coole Pflanze", "cooles Bild");

        ResponseEntity<String> response = restTemplate.exchange("/plant", HttpMethod.POST,
                new HttpEntity<>(plant), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        //check with getAll()

        ResponseEntity<List<Plant>> getAllResponse = restTemplate.exchange("/plants", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> plants = getAllResponse.getBody();
        assertTrue(plants.size() == 10);
    }

    @Test
    @Rollback
    void createPlantTest_TooLongLatinName(){
        Plant plant = new Plant("Rose", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                5.40f, 15, Category.ZIMMERPFLANZE, Height.S, WaterDemand.MEDIUM, "coole Pflanze",
                "cooles Bild");

        ResponseEntity<String> response = restTemplate.exchange("/plant", HttpMethod.POST,
                new HttpEntity<>(plant), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        //check with getAll()

        ResponseEntity<List<Plant>> getAllResponse = restTemplate.exchange("/plants", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> plants = getAllResponse.getBody();
        assertTrue(plants.size() == 10);
    }

    @Test
    @Rollback
    void createPlantTest_NullPrice(){
        Plant plant = new Plant("Rose", "Rosa", null,
                15, Category.ZIMMERPFLANZE, Height.S, WaterDemand.MEDIUM, "coole Pflanze",
                "cooles Bild");

        ResponseEntity<String> response = restTemplate.exchange("/plant", HttpMethod.POST,
                new HttpEntity<>(plant), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        //check with getAll()

        ResponseEntity<List<Plant>> getAllResponse = restTemplate.exchange("/plants", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> plants = getAllResponse.getBody();
        assertTrue(plants.size() == 10);
    }

    @Test
    @Rollback
    void createPlantTest_NegativePrice(){
        Plant plant = new Plant("Rose", "Rosa", -1f,
                15, Category.ZIMMERPFLANZE, Height.S, WaterDemand.MEDIUM, "coole Pflanze",
                "cooles Bild");

        ResponseEntity<String> response = restTemplate.exchange("/plant", HttpMethod.POST,
                new HttpEntity<>(plant), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        //check with getAll()

        ResponseEntity<List<Plant>> getAllResponse = restTemplate.exchange("/plants", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> plants = getAllResponse.getBody();
        assertTrue(plants.size() == 10);
    }

    @Test
    @Rollback
    void createPlantTest_NullAmount(){
        Plant plant = new Plant("Rose", "Rosa", 5.40f,
                null, Category.ZIMMERPFLANZE, Height.S, WaterDemand.MEDIUM, "coole Pflanze",
                "cooles Bild");

        ResponseEntity<String> response = restTemplate.exchange("/plant", HttpMethod.POST,
                new HttpEntity<>(plant), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        //check with getAll()

        ResponseEntity<List<Plant>> getAllResponse = restTemplate.exchange("/plants", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> plants = getAllResponse.getBody();
        assertTrue(plants.size() == 10);
    }

    @Test
    @Rollback
    void createPlantTest_NegativeAmount(){
        Plant plant = new Plant("Rose", "Rosa", 5.40f,
                -1, Category.ZIMMERPFLANZE, Height.S, WaterDemand.MEDIUM, "coole Pflanze",
                "cooles Bild");

        ResponseEntity<String> response = restTemplate.exchange("/plant", HttpMethod.POST,
                new HttpEntity<>(plant), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        //check with getAll()

        ResponseEntity<List<Plant>> getAllResponse = restTemplate.exchange("/plants", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> plants = getAllResponse.getBody();
        assertTrue(plants.size() == 10);
    }

    @Test
    @Rollback
    void createPlantTest_NonExistentCategory(){
        String invalidPlantJson = """
            {
                "name": "Pflanze",
                "latinName": "Pflanzus Longus",
                "price": 1.3,
                "amount": 5,
                "category": "LAUCH",
                "height": "M",
                "waterDemand": "LOW",
                "description": "Super tolle Pflanze echt schick",
                "imageLink": "Nices Foto"
            }
            """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(invalidPlantJson, headers);

        ResponseEntity<String> response = restTemplate.exchange("/plant", HttpMethod.POST, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        //check with getAll()

        ResponseEntity<List<Plant>> getAllResponse = restTemplate.exchange("/plants", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> plants = getAllResponse.getBody();
        assertTrue(plants.size() == 10);
    }

    @Test
    @Rollback
    void createPlantTest_NonExistentHeight(){
        String invalidPlantJson = """
            {
                "name": "Pflanze",
                "latinName": "Pflanzus Longus",
                "price": 1.3,
                "amount": 5,
                "category": "ZIMMERPFLANZE",
                "height": "XL",
                "waterDemand": "LOW",
                "description": "Super tolle Pflanze echt schick",
                "imageLink": "Nices Foto"
            }
            """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(invalidPlantJson, headers);

        ResponseEntity<String> response = restTemplate.exchange("/plant", HttpMethod.POST, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        //check with getAll()

        ResponseEntity<List<Plant>> getAllResponse = restTemplate.exchange("/plants", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> plants = getAllResponse.getBody();
        assertTrue(plants.size() == 10);
    }

    @Test
    @Rollback
    void createPlantTest_NonExistentWaterDemand(){
        String invalidPlantJson = """
            {
                "name": "Pflanze",
                "latinName": "Pflanzus Longus",
                "price": 1.3,
                "amount": 5,
                "category": "ZIMMERPFLANZE",
                "height": "M",
                "waterDemand": "SUPER HIGH",
                "description": "Super tolle Pflanze echt schick",
                "imageLink": "Nices Foto"
            }
            """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(invalidPlantJson, headers);

        ResponseEntity<String> response = restTemplate.exchange("/plant", HttpMethod.POST, request, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        //check with getAll()

        ResponseEntity<List<Plant>> getAllResponse = restTemplate.exchange("/plants", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> plants = getAllResponse.getBody();
        assertTrue(plants.size() == 10);
    }

    //-------------------------------------------------------------------------
    //GETPLANTBYID() TESTS:
    //-------------------------------------------------------------------------

    @Test
    void getPlantByIdTest_ValidId(){
        //getAll() to find Ids
        ResponseEntity<List<Plant>> getAllResponse = restTemplate.exchange("/plants", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> plants = getAllResponse.getBody();
        Plant randomplant = plants.get(0);
        UUID randomPlantId = randomplant.getPlantId();

        //getPlantById()
        ResponseEntity<Plant> response = restTemplate.exchange("/plant/" + randomPlantId.toString(), HttpMethod.GET,
                null, Plant.class);
        Plant responsePlant = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertTrue(responsePlant.equals(randomplant));
        assertEquals(responsePlant.getPlantId(), randomPlantId);
    }

    @Test
    void getPlantByIdTest_0Id(){
        ResponseEntity<String> response = restTemplate.exchange("/plant/0", HttpMethod.GET, null,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void getPlantByIdTest_NegativeId(){
        ResponseEntity<String> response = restTemplate.exchange("/plant/-1", HttpMethod.GET, null,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void getPlantByIdTest_StringId(){
        ResponseEntity<String> response = restTemplate.exchange("/plant/dieseidisteinString", HttpMethod.GET, null,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void getPlantByIdTest_NonExistendId(){
        ResponseEntity<String> response = restTemplate.exchange("/plant/41723741", HttpMethod.GET, null,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    //-------------------------------------------------------------------------
    //GETALLPLANTS() TESTS:
    //-------------------------------------------------------------------------

    @Test
    void getAllPlantsTest_ListNotEmpty(){
        List<Plant> testPlants = new ArrayList<>(Arrays.asList(plant0, plant1, plant2, plant3, plant4, plant5, plant6,
                plant7, plant8, plant9));
        ResponseEntity<List<Plant>> getAllResponse = restTemplate.exchange("/plants", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> responsePlants = getAllResponse.getBody();

        assertEquals(testPlants.size(), responsePlants.size());
        for (Plant responsePlant: responsePlants){
            assertTrue(testPlants.contains(responsePlant));
        }
        assertThat(getAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getAllPlantsTest_ListEmpty(){
        restTemplate.exchange("/plants", HttpMethod.DELETE, null, Void.class);
        ResponseEntity<List<Plant>> getAllResponse = restTemplate.exchange("/plants", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> responsePlants = getAllResponse.getBody();

        assertTrue(responsePlants.isEmpty());
        assertThat(getAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    //-------------------------------------------------------------------------
    //GETPLANTSBYNAME() TESTS:
    //-------------------------------------------------------------------------

    @Test
    void getPlantsByNameTest_NameExists(){
        ResponseEntity<List<Plant>> response = restTemplate.exchange("/plants/name/Sonnenblume",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> responsePlants = response.getBody();
        Plant responsePlant = responsePlants.getFirst();

        assertTrue(responsePlants.size() == 1);
        assertTrue(responsePlant.equals(plant1));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getPlantsByNameTest_NameDoesNotExists(){
        ResponseEntity<List<Plant>> response = restTemplate.exchange("/plants/name/Existiertnicht",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> responsePlants = response.getBody();

        assertTrue(responsePlants.isEmpty());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getPlantsByNameTest_NameAsInteger(){
        ResponseEntity<List<Plant>> response = restTemplate.exchange("/plants/name/1",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> responsePlants = response.getBody();

        assertTrue(responsePlants.isEmpty());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    //-------------------------------------------------------------------------
    //GETPLANTSBYCATEGORY() TESTS:
    //-------------------------------------------------------------------------

    @Test
    void getPlantsByCategoryTest_CategoryExists(){
        List<Plant> testPlants = new ArrayList<>(Arrays.asList(plant0, plant1, plant2, plant3, plant4, plant5));
        ResponseEntity<List<Plant>> response = restTemplate.exchange("/plants/category/ZIMMERPFLANZE",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> responsePlants = response.getBody();

        assertEquals(testPlants.size(), responsePlants.size());
        for (Plant responsePlant: responsePlants){
            assertTrue(testPlants.contains(responsePlant));
        }
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getPlantsByCategoryTest_CategoryDoesNotExists(){
        ResponseEntity<List<Plant>> response = restTemplate.exchange("/plants/category/GARTENPFLANZE",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> responsePlants = response.getBody();

        assertTrue(responsePlants.isEmpty());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getPlantsByCategoryTest_CategoryIsNotDefined(){
        ResponseEntity<String> response = restTemplate.exchange("/plants/category/FLEISCHFRESSENDEPFLANZE",
                HttpMethod.GET, null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void getPlantsByCategoryTest_CategoryAsInteger(){
        ResponseEntity<String> response = restTemplate.exchange("/plants/category/11423",
                HttpMethod.GET, null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    //-------------------------------------------------------------------------
    //UPDATEPLANTAMOUNT() TESTS:
    //-------------------------------------------------------------------------

    @Test
    void updatePlantAmountTest_PlantExistsAndValidAmount(){
        //getAll() to find Ids
        ResponseEntity<List<Plant>> getAllResponse = restTemplate.exchange("/plants", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> plants = getAllResponse.getBody();
        Plant randomplant = plants.get(0);
        UUID randomPlantId = randomplant.getPlantId();

        //updatePlantById()
        Integer newAmount = 12;
        ResponseEntity<Plant> response = restTemplate.exchange("/plant/"+ randomPlantId +
                        "?newAmount=" + newAmount, HttpMethod.PUT, null, Plant.class);
        Plant responsePlant = response.getBody();

        assertTrue(responsePlant.getAmount().equals(newAmount));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void updatePlantAmountTest_PlantDoesNotExist(){
        int newAmount = 12;
        ResponseEntity<String> response = restTemplate.exchange("/plant/34252340625?newAmount=" + newAmount,
                HttpMethod.PUT, null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void updatePlantAmountTest_NegativePlantId(){
        int newAmount = 12;
        ResponseEntity<String> response = restTemplate.exchange("/plant/-1?newAmount=" + newAmount,
                HttpMethod.PUT, null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void updatePlantAmountTest_ZeroPlantId(){
        int newAmount = 12;
        ResponseEntity<String> response = restTemplate.exchange("/plant/0?newAmount=" + newAmount,
                HttpMethod.PUT, null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void updatePlantAmountTest_NonIntegerPlantId(){
        int newAmount = 12;
        ResponseEntity<String> response = restTemplate.exchange("/plant/adwdd?newAmount=" + newAmount,
                HttpMethod.PUT, null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void updatePlantAmountTest_NoAmountGiven(){
        //getAll() to find Ids
        ResponseEntity<List<Plant>> getAllResponse = restTemplate.exchange("/plants", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> plants = getAllResponse.getBody();
        Plant randomplant = plants.get(0);
        UUID randomPlantId = randomplant.getPlantId();

        //updatePlantAmount()
        ResponseEntity<String> response = restTemplate.exchange("/plant/" + randomPlantId +"?newAmount=",
                HttpMethod.PUT, null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void updatePlantAmountTest_NegativeAmount(){
        //getAll() to find Ids
        ResponseEntity<List<Plant>> getAllResponse = restTemplate.exchange("/plants", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> plants = getAllResponse.getBody();
        Plant randomplant = plants.get(0);
        UUID randomPlantId = randomplant.getPlantId();

        //updatePlantAmount()
        ResponseEntity<String> response = restTemplate.exchange("/plant/" + randomPlantId +"?newAmount=-1",
                HttpMethod.PUT, null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void updatePlantAmountTest_AmountNotInteger(){
        //getAll() to find Ids
        ResponseEntity<List<Plant>> getAllResponse = restTemplate.exchange("/plants", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> plants = getAllResponse.getBody();
        Plant randomplant = plants.get(0);
        UUID randomPlantId = randomplant.getPlantId();

        //updatePlantAmount()
        ResponseEntity<String> response = restTemplate.exchange("/plant/" + randomPlantId +"?newAmount=adwd",
                HttpMethod.PUT, null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    //-------------------------------------------------------------------------
    //DELETEPLANT() TESTS:
    //-------------------------------------------------------------------------

    @Test
    void deletePlantTest_PlantExists(){
        //getAll() to find Id
        ResponseEntity<List<Plant>> getAllResponse = restTemplate.exchange("/plants", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> plants = getAllResponse.getBody();
        Plant randomplant = plants.get(0);
        UUID randomPlantId = randomplant.getPlantId();

        //execute deletePlant()
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/plant/" + randomPlantId, HttpMethod.DELETE,
                null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void deletePlantTest_PlantDoesNotExist(){
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/plant/152354224", HttpMethod.DELETE,
                null, Void.class);

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    //-------------------------------------------------------------------------
    //DELETEALLPLANTS() TESTS:
    //-------------------------------------------------------------------------

    @Test
    void deleteAllPlantsTest(){
        //execute deleteAll()
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/plants", HttpMethod.DELETE, null,
                Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        //check with getAll()
        ResponseEntity<List<Plant>> getAllResponse = restTemplate.exchange("/plants", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Plant>>() {});
        List<Plant> plants = getAllResponse.getBody();

        assertThat(getAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertTrue(plants.isEmpty());
    }
}
