package com.fintech.user.entities;

import com.fintech.user.enums.AvailableEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class UserEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "pin")
    String pin;

    @Column(name = "password")
    String password;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    AvailableEnum status;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Enumerated(EnumType.STRING)
    List<RoleEntity> roles;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false, nullable = false)
    LocalDateTime createdDate;

    @ColumnDefault(value = "1")
    Integer active;
}
