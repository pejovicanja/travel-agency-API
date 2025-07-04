package rs.ac.bg.fon.travel_agency.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import rs.ac.bg.fon.travel_agency.constraint.DateOrder;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Accessors(chain = true)
@DateOrder(
        dateFromField = "availableFrom",
        dateToField = "availableTo",
        message = "Available from date must be less or equal to available to date")
@Entity
@Table(name = "place")
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @NotNull
    @Column(name = "price")
    private Double price;

    @NotNull
    @FutureOrPresent
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "available_from")
    private LocalDate availableFrom;

    @NotNull
    @FutureOrPresent
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "available_to")
    private LocalDate availableTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(mappedBy = "place", cascade = CascadeType.ALL)
    private PlaceLocation placeLocation;

    public void setPlaceLocation(PlaceLocation placeLocation) {
        if (placeLocation != null) {
            placeLocation.setPlace(this);
        }
        this.placeLocation = placeLocation;
    }
}
