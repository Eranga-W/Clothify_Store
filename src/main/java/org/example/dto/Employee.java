package org.example.dto;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {
    private String empId;
    private String empTitle;
    private String empName;
    private Date dob;
    private Double salary;
    private String address;
    private String role;
    private String email;
    private String password;

}
