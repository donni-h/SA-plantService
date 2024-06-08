package de.htw.saplantservice.core.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

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
    private Long id;

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    @Size(max = 50)
    private String latinName;

    @NotNull
    private String description;

    @NotNull
    private float price;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Height height;

    @Enumerated(EnumType.STRING)
    private WaterDemand waterDemand;

    private String imageLink;

    private int amount;

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
                "id=" + id +
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
