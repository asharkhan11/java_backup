package com.ashar.profileManager.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;


//@Table(name = "user_table",uniqueConstraints = {
//        @UniqueConstraint(name = "uc_username",columnNames = {"username"})
//},indexes = {
//        @Index(name = "index_username",columnList = "username",unique = true)
//})
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_table2")
public class User {

    @Id
    @SequenceGenerator(name = "seq_user_id", allocationSize = 20, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user_id")
    private int id;

//    @Column(nullable = false)
    private String name;

//    @Column(nullable = false)
    private Date dateOfBirth;

//    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonManagedReference
    @ToString.Exclude
    private List<Profile> profiles;
}
