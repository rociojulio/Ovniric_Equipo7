package com.ovniric.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
public class ReservationDTO {


    private Long id;
    private String startHour;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long productId;
    private Long userId;

    public ReservationDTO() {
    }

}