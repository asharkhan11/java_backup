package com.ashar.profileManager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    @SequenceGenerator(name = "id", allocationSize = 20, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "id")
    private int id;
    private String name;
    private String profilePicUrl;
    private String bio;
    private Date dateOfBirth;
}
