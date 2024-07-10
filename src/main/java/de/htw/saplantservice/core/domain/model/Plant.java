package de.htw.saplantservice.core.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity //Marks class as database Entity
@Table  //Table in Data Base (here you can name the table)
@Getter
@Setter
@NoArgsConstructor
public class Plant {
    @Id
    @SequenceGenerator(
            name = "plant_sequence",
            sequenceName = "plant_sequence",
            allocationSize = 1

    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "plant_sequence"
    )
    private Long plantId;

    @NotNull(message = "name cannot be null")
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    private String name;

    @NotNull(message = "latinName cannot be null")
    @Size(min = 1, max = 50, message = "latinName must be between 1 and 50 characters")
    private String latinName;

    @NotNull (message = "Price cannot be null")
    @PositiveOrZero (message = "price has to be positive")
    private Float price;

    @NotNull (message = "Amount cannot be null")
    @PositiveOrZero (message = "amount hast to be positive")
    private Integer amount;

    @NotNull (message = "Category cannot be null")
    @Enumerated(EnumType.STRING)
    private Category category;

    @NotNull (message = "Height cannot be null")
    @Enumerated(EnumType.STRING)
    private Height height;

    @NotNull (message = "Waterdemand cannot be null")
    @Enumerated(EnumType.STRING)
    private WaterDemand waterDemand;

    @NotNull (message = "Description cannot be null")
    private String description;

    @NotNull (message = "Image link cannot be null")
    private String imageLink;

    public Plant(Long plantId, String name, String latinName, Float price, Integer amount, Category category,
                 Height height, WaterDemand waterDemand, String description, String imageLink) {
        this.plantId = plantId;
        this.name = name;
        this.latinName = latinName;
        this.price = price;
        this.amount = amount;
        this.category = category;
        this.height = height;
        this.waterDemand = waterDemand;
        this.description = description;
        this.imageLink = imageLink;
    }

    public Plant(String name, String latinName, Float price, Integer amount, Category category, Height height,
                 WaterDemand waterDemand, String description, String imageLink) {
        this.name = name;
        this.latinName = latinName;
        this.price = price;
        this.amount = amount;
        this.category = category;
        this.height = height;
        this.waterDemand = waterDemand;
        this.description = description;
        this.imageLink = imageLink;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "plantId=" + plantId +
                ", name='" + name + '\'' +
                ", latinName='" + latinName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", height=" + height +
                ", waterDemand=" + waterDemand +
                ", imageLink='" + imageLink + '\'' +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Plant plant = (Plant) o;
        return this.getName().equals(plant.getName())
                && this.getLatinName().equals(plant.getLatinName())
                && this.getPrice().equals(plant.getPrice())
                && this.getAmount().equals(plant.getAmount())
                && this.getCategory().equals(plant.getCategory())
                && this.getHeight().equals(plant.getHeight())
                && this.getWaterDemand().equals(plant.getWaterDemand())
                && this.getDescription().equals(plant.getDescription())
                && this.getImageLink().equals(plant.getImageLink());
    }
}
