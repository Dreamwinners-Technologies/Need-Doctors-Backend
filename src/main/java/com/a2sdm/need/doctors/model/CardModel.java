package com.a2sdm.need.doctors.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "card_info")
public class CardModel {
    @Id
    private String id;

    private String addedBy;

    private String name;

    @Size(min = 11, max = 11)
    private String appointmentNo;

    private String specialization;

    private String thana;

    private String district;

    private String cardImageUrl;

    @Column(columnDefinition="TEXT")
    private String cardOcrData;

}
