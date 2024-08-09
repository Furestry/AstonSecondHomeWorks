package ru.furestry.astonhomework.entity.document;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
public class DriverLicense extends LimitedDocument {
    private List<CarCategory> categories;

    private enum CarCategory {
        A,
        B,
        C,
        D
    }
}
