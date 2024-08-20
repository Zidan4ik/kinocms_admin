package com.example.kinocms_admin.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class StatisticDTO {
    private List<CityCountDTO> citiesDTO;
    private Integer amountMen;
    private Integer amountWomen;
    private Integer amountCinemas;
    private Integer amountHalls;
    private Integer amountNews;
    private Integer amountShares;
}
