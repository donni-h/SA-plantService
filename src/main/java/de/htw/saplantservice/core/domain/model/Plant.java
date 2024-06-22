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
    @Positive(message = "plantId must be positive")
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

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Height height;

    @Enumerated(EnumType.STRING)
    private WaterDemand waterDemand;

    private String description;

    private String imageLink;

    public Plant(String name, String latinName, String description, float price, Category category, Height height,
                 WaterDemand waterDemand, String imageLink, int amount) {
        this.name = name;
        this.latinName = latinName;
        this.description = description;
        this.price = price;
        this.category = category;
        this.height = height;
        this.waterDemand = waterDemand;
        this.imageLink = imageLink;
        this.amount = amount;
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
}
