package com.example.kinocms_admin.controller;

import com.example.kinocms_admin.entity.Banner;
import com.example.kinocms_admin.mapper.BannerMapper;
import com.example.kinocms_admin.model.BannerDTO;
import com.example.kinocms_admin.model.BannerPageDTO;
import com.example.kinocms_admin.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class BannerController {
    private final BannerService bannerService;
    @GetMapping("/banners")
    public String pageBanners(){
        return "banner/banners-view";
    }
    @GetMapping("/info-banners")
    @ResponseBody
    public BannerPageDTO getDataBanners(){
        List<Banner> bannersComponents = bannerService.getAll();
        List<BannerDTO> dtoBannersComponents = BannerMapper.toDTOBannersList(bannersComponents);
        return new BannerPageDTO(dtoBannersComponents,"/path/to/file");
    }
}
