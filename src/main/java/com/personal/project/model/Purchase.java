package com.personal.project.model;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.GeneratedValue;
import lombok.*;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int purchase_id;
    private int user_id;
    private String purchase_name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate purcahse_date;
    private String decription = null;
    private int purchase_cost;

}
