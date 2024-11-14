package com.RajputFinance.Rajput.Finance.Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;
    private String userName;
    private boolean isAdmin;
    private String profileImage;
}
