package com.example.kinocms_admin.controller;

import com.example.kinocms_admin.model.CityCountDTO;
import com.example.kinocms_admin.model.StatisticDTO;
import com.example.kinocms_admin.service.serviceimp.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class StatisticController {
    private final UserServiceImp userServiceImpl;
    private final CinemaServiceImp cinemaServiceImp;
    private final HallServiceImp hallServiceImp;
    private final NewServiceImp newServiceImp;
    private final ShareServiceImp shareServiceImp;
    @GetMapping("/statistics")
    public String viewStatistics(){
        return "statistic/statistics-view";
    }

    @GetMapping("/statistic-data")
    @ResponseBody
    public StatisticDTO get(){
        List<CityCountDTO> cityWithCounts = userServiceImpl.findCityWithCounts();
        Integer men = userServiceImpl.getCountGenders(true);
        Integer women = userServiceImpl.getCountGenders(false);
        Integer cinemas = cinemaServiceImp.getAmountCinemas();
        Integer halls  = hallServiceImp.getAmountHalls();
        Integer news = newServiceImp.getAmountNews();
        Integer shares = shareServiceImp.getAmountShares();

        return new StatisticDTO(cityWithCounts,men,women,cinemas,halls,news,shares);
    }
}