package edu.step.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class User {
    @Id
    private int id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;

    public User(User user) {
        this(user.id, user.firstName, user.lastName, user.password, user.email);
    }
}
