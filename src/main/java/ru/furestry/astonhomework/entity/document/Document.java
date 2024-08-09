package ru.furestry.astonhomework.entity.document;

import jakarta.persistence.*;
import lombok.*;
import ru.furestry.astonhomework.entity.IEntity;
import ru.furestry.astonhomework.entity.User;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Document implements IEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String series;

    private String number;

    @OneToOne
    @JoinColumn(name = "account")
    private User user;

    private LocalDate issueDate;

    @Column(name = "document_type")
    private String documentType;
}
