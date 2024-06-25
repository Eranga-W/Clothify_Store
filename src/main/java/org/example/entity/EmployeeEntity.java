package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class EmployeeEntity {
    @Id
    private String id;
    private String empTitle;
    private String empName;
    @Temporal(TemporalType.DATE)
    private Date dob;
    private Double salary;
    private String address;
    private String role;
    private String email;
    private String password;

}
