package ru.matrosov.api;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class UserApi {

    String email;
    String password;
    String name;
}
