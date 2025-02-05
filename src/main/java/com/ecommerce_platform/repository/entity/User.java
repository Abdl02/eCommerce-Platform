package com.ecommerce_platform.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Represents a user in the e-commerce platform.
 * <p>
 * Relationships:
 * - Many-to-Many with Role: A user can have multiple roles.
 * <p>
 * Annotations:
 * @Entity marks this as a persistent entity.
 * @Table(name = "users") specifies the table name.
 * @ManyToMany uses EAGER fetching because roles are critical for security purposes.
 * <p>
 * Why EAGER fetching?
 * - User roles are essential for authorization and security checks, so they are fetched immediately.
 * <p>
 *  Why @JoinTable?
 * - Manages the relationship between users and roles using a join table named `user_roles`.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User(Long id, String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;
    }
}