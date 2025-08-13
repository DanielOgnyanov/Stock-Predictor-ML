package com.daniel.stockpredictorml.models.entities;


import com.daniel.stockpredictorml.models.enums.Role;
import jakarta.persistence.*;
import lombok.*;



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
