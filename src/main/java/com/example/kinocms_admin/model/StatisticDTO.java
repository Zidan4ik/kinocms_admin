package com.example.kinocms_admin.model;

import com.example.kinocms_admin.auth.UserDetailsImp;
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
    private int onlineUsers;
}
