package main.java.models.entities;


import jakarta.persistence.*;
import lombok.*;
import main.java.models.enums.Role;


@Entity
@Table(name = "roles", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleEntity extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private Role name;
}
