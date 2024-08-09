package ru.furestry.astonhomework.entity.document;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
public class Passport extends LimitedDocument {
    private String registration;
}
