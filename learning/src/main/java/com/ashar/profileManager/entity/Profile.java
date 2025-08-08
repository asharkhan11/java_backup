package com.ashar.profileManager.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
        @Index(name = "username_index",columnList = "username")
})
public class Profile {

    @Id
    @SequenceGenerator(name = "seq_profile_id", allocationSize = 20, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_profile_id")
    private int id;

    @Column(/*nullable = false,*/ unique = true)
    private String username;

//    @Column(nullable = false)
    private String password;

    private String profilePicUrl;

    private String bio;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    @JsonBackReference
    private User user;

}
