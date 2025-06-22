package rs.ac.bg.fon.travel_agency.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    @Enumerated(value = EnumType.STRING)
    private RoleCode name;

    @Getter
    public enum RoleCode {
        ADMIN(Code.ADMIN),
        USER_BASIC(Code.USER_BASIC),
        USER_PRO(Code.USER_PRO);

        private final String authority;

        RoleCode(String authority) {
            this.authority = authority;
        }

        public static class Code {
            public static final String ADMIN = "ADMIN";
            public static final String USER_BASIC = "USER_BASIC";
            public static final String USER_PRO = "USER_PRO";
        }
    }
}
