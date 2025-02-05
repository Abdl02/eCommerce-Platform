package com.ecommerce_platform.repository.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a user's role in the system (e.g., ADMIN, CUSTOMER).
 * <p>
 *
 * Annotations:
 * @Entity marks this class as a persistent entity.
 * @Enumerated(EnumType.STRING) stores the enum value as a string in the database.
 * <p>
 *
 * Why use an Enum for RoleType?
 * - Ensures consistent and readable role names across the application and database.
 * - Enhances maintainability by preventing the use of arbitrary role names.
 */
@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleType name;
}