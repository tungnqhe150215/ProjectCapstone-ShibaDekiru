package com.sep490.g49.shibadekiru.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "class_test_assign")
public class ClassTestAssign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "assigned_class",referencedColumnName = "class_id")
    private Class assignedClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "test",referencedColumnName = "test_id")
    private Test test;


    private LocalDateTime accessExpirationDate;

    public boolean isAccessExpired() {
        if (accessExpirationDate != null) {
            return LocalDateTime.now().isAfter(accessExpirationDate);
        }
        return false; // Nếu accessExpirationDate là null, không hết hạn truy cập
    }
}
