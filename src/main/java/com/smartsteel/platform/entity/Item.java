package com.smartsteel.platform.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // HR
    @Column(nullable = false, unique = true)
    private String code;

    // 열연강판
    @Column(nullable = false)
    private String name;
}