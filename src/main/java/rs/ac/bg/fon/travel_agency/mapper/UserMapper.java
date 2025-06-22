package rs.ac.bg.fon.travel_agency.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rs.ac.bg.fon.travel_agency.domain.User;
import rs.ac.bg.fon.travel_agency.dto.UserDto;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    public static User toEntity(UserDto userDto) {
        return User.builder()
                .id(userDto.id())
                .email(userDto.email())
                .password(userDto.password())
                .age(userDto.age())
                .firstName(userDto.firstName())
                .lastName(userDto.lastName())
                .build();
    }

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .age(user.getAge())
                .build();
    }
}
