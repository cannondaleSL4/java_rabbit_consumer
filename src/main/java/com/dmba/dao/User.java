package com.dmba.dao;

import javax.persistence.*;

import com.dmba.dao.proto.UsersOrderProto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_table")
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "UserName")
    private String userName;

    @Column(name = "age")
    private int age;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "role")
    private UsersOrderProto.UsersOrder.Role role;
}