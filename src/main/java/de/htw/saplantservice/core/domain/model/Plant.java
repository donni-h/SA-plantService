package de.htw.saplantservice.core.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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

    @Column(nullable = false)
    @NotNull (message = "Name cannot be null")
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    private String name;

    @Column(nullable = false)
    @NotNull (message = "Latin name cannot be null")
    @NotBlank(message = "Latin name cannot be blank")
    @Size(min = 1, max = 50)
    private String latinName;

    @Column(nullable = false)
    @NotNull (message = "Price cannot be null")
    @PositiveOrZero
    private Float price;

    @Column(nullable = false)
    @NotNull (message = "Amount cannot be null")
    @PositiveOrZero
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
