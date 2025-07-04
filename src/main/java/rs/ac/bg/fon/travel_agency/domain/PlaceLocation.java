package rs.ac.bg.fon.travel_agency.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "place_location")
public class PlaceLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "static_map_image_url")
    private String staticMapImageUrl;

    @NotNull
    @Column(name = "lat")
    private String lat;

    @NotNull
    @Column(name = "lng")
    private String lng;

    @OneToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;
}
