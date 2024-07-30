package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Page;
import com.example.kinocms_admin.entity.PageTranslation;
import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.enums.PageType;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseLoader implements CommandLineRunner {
    private final PageServiceImp pageServiceImp;

    public DatabaseLoader(PageServiceImp pageServiceImp) {
        this.pageServiceImp = pageServiceImp;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Page> all = pageServiceImp.getAll();
        if (all.isEmpty()) {
            Page pageMain = new Page(true, PageType.main, LocalDate.now());
            Page pageAbout = new Page(true, PageType.about, LocalDate.now());
            Page pageCaffe = new Page(true, PageType.caffe, LocalDate.now());
            Page pageVip = new Page(true, PageType.vip, LocalDate.now());
            Page pageAd = new Page(true, PageType.ad, LocalDate.now());
            Page pageChild = new Page(true, PageType.child, LocalDate.now());
            Page pageContact = new Page(true, PageType.contact, LocalDate.now());
            pageMain.setPageTranslations(new ArrayList<>(){{add(new PageTranslation(LanguageCode.Ukr,PageType.page,"Головна сторінка",null,pageMain));}});
            pageAbout.setPageTranslations(new ArrayList<>(){{add(new PageTranslation(LanguageCode.Ukr,PageType.page,"Про кінотеатр",null,pageAbout));}});
            pageCaffe.setPageTranslations(new ArrayList<>(){{add(new PageTranslation(LanguageCode.Ukr,PageType.page,"Кафе-Бар",null,pageCaffe));}});
            pageVip.setPageTranslations(new ArrayList<>(){{add(new PageTranslation(LanguageCode.Ukr,PageType.page,"Vip-зал",null,pageVip));}});
            pageAd.setPageTranslations(new ArrayList<>(){{add(new PageTranslation(LanguageCode.Ukr,PageType.page,"Реклама",null,pageAd));}});
            pageChild.setPageTranslations(new ArrayList<>(){{add(new PageTranslation(LanguageCode.Ukr,PageType.page,"Дитяча кімната",null,pageChild));}});
            pageContact.setPageTranslations(new ArrayList<>(){{add(new PageTranslation(LanguageCode.Ukr,PageType.page,"Контакти",null,pageContact));}});

            pageServiceImp.save(pageMain);
            pageServiceImp.save(pageAbout);
            pageServiceImp.save(pageCaffe);
            pageServiceImp.save(pageVip);
            pageServiceImp.save(pageAd);
            pageServiceImp.save(pageChild);
            pageServiceImp.save(pageContact);
        }
    }
}
