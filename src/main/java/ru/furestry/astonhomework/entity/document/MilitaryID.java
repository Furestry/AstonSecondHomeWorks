package ru.furestry.astonhomework.entity.document;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@ToString
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
public class MilitaryID extends Document {
    private String series;

    private String number;

    private String specialization;

    @Column(name = "rec_office")
    private String recruitmentOffice;
}
