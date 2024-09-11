package com.example.kinocms_admin.service.serviceimp;

import com.example.kinocms_admin.entity.Page;
import com.example.kinocms_admin.entity.PageTranslation;
import com.example.kinocms_admin.entity.User;
import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.enums.PageType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseLoader implements CommandLineRunner {
    private final PageServiceImp pageServiceImp;

    private final UserServiceImp userServiceImp;

    public DatabaseLoader(PageServiceImp pageServiceImp, UserServiceImp userServiceImp) {
        this.pageServiceImp = pageServiceImp;
        this.userServiceImp = userServiceImp;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Page> all = pageServiceImp.getAll();
        List<User> users = userServiceImp.getAll();
        if (all.isEmpty()) {
            Page pageMain = new Page(true, PageType.main, LocalDate.now());
            Page pageMobile = new Page(true, PageType.mobile, LocalDate.now());
            Page pageAbout = new Page(true, PageType.about, LocalDate.now());
            Page pageCaffe = new Page(true, PageType.caffe, LocalDate.now());
            Page pageVip = new Page(true, PageType.vip, LocalDate.now());
            Page pageAd = new Page(true, PageType.advertisement, LocalDate.now());
            Page pageChild = new Page(true, PageType.child, LocalDate.now());
            Page pageContact = new Page(true, PageType.contacts, LocalDate.now());
            pageMain.setPageTranslations(new ArrayList<>() {{
                add(new PageTranslation(LanguageCode.Ukr, PageType.page, "Головна сторінка", null, pageMain));
            }});
            pageAbout.setPageTranslations(new ArrayList<>() {{
                add(new PageTranslation(LanguageCode.Ukr, PageType.page, "Про кінотеатр", null, pageAbout));
            }});
            pageCaffe.setPageTranslations(new ArrayList<>() {{
                add(new PageTranslation(LanguageCode.Ukr, PageType.page, "Кафе-Бар", null, pageCaffe));
            }});
            pageVip.setPageTranslations(new ArrayList<>() {{
                add(new PageTranslation(LanguageCode.Ukr, PageType.page, "Vip-зал", null, pageVip));
            }});
            pageAd.setPageTranslations(new ArrayList<>() {{
                add(new PageTranslation(LanguageCode.Ukr, PageType.page, "Реклама", null, pageAd));
            }});
            pageChild.setPageTranslations(new ArrayList<>() {{
                add(new PageTranslation(LanguageCode.Ukr, PageType.page, "Дитяча кімната", null, pageChild));
            }});
            pageContact.setPageTranslations(new ArrayList<>() {{
                add(new PageTranslation(LanguageCode.Ukr, PageType.page, "Контакти", null, pageContact));
            }});
            pageMobile.setPageTranslations(new ArrayList<>() {{
                add(new PageTranslation(LanguageCode.Ukr, PageType.page, "Мобільна програма", null, pageMobile));
            }});
            pageServiceImp.save(pageMain);
            pageServiceImp.save(pageAbout);
            pageServiceImp.save(pageCaffe);
            pageServiceImp.save(pageVip);
            pageServiceImp.save(pageAd);
            pageServiceImp.save(pageChild);
            pageServiceImp.save(pageContact);
            pageServiceImp.save(pageMobile);
        }
        if (users.isEmpty()) {
            User user1 = new User("roma", "pravnyk", "zidan", "0985545991", "roomich@gmail.com", "Lviv", "1234", "-", LanguageCode.Ukr, true, LocalDate.of(2003, 12, 28), LocalDate.now(), false, "Шкільна 108/2");
            User user2 = new User("andriy", "fedorovych", "n1oit", "0666790166", "programlearn2023@gmail.com", "Kyiv", "1111", "-", LanguageCode.Ukr, true, LocalDate.of(2003, 12, 7), LocalDate.now(), false, "Костопільська 2");
            User user3 = new User("masha", "melnyk", "marka", "0888123466", "englandnew2023@gmail.com", "Zhytomyr", "2222", "-", LanguageCode.Eng, false, LocalDate.of(2002, 5, 15), LocalDate.now(), false, "Шаршарова 2в");
            User user4 = new User("dasha", "levina", "povstanets", "0985386941", "dashaLevina@gmail.com", "Lviv", "3333", "-", LanguageCode.Eng, false, LocalDate.of(2004, 3, 2), LocalDate.now(), false, "Гетьман 2/1");
            User user5 = new User("rostyslave", "dub", "marsianin", "0866785670", "rost@gmail.com", "Kyiv", "4455", "-", LanguageCode.Eng, true, LocalDate.of(2004, 7, 6), LocalDate.now(), false, "Дружня 22d");
            User user6 = new User("katya", "gorodchuk", "katyagorodchuk", "0889765891", "katka@gmail.com", "Lviv", "6554", "-", LanguageCode.Eng, false, LocalDate.of(2004, 7, 6), LocalDate.now(), false, "Патріот 52");
            User user7 = new User("andriy", "fedorovych", "n1oit", "0666790166", "andrios@gmail.com", "Kharkiv", "1111", "-", LanguageCode.Ukr, true, LocalDate.of(2003, 12, 7), LocalDate.now(), false, "Костопільська 2");
            User user8 = new User("katya", "gorodchuk", "katyagorodchuk", "0889765891", "katka@gmail.com", "Kyiv", "6554", "-", LanguageCode.Eng, false, LocalDate.of(2004, 7, 6), LocalDate.now(), false, "Патріот 52");
            User user9 = new User("dasha", "levina", "povstanets", "0985386941", "dashaLevina@gmail.com", "Rivne", "3333", "-", LanguageCode.Eng, false, LocalDate.of(2004, 3, 2), LocalDate.now(), false, "Гетьман 2/1");
            User user10 = new User("rostyslave", "dub", "marsianin", "0866785670", "rost@gmail.com", "Kyiv", "4455", "-", LanguageCode.Eng, true, LocalDate.of(2004, 7, 6), LocalDate.now(), false, "Дружня 22d");

            userServiceImp.save(user1);
            userServiceImp.save(user2);
            userServiceImp.save(user3);
            userServiceImp.save(user4);
            userServiceImp.save(user5);
            userServiceImp.save(user6);
            userServiceImp.save(user7);
            userServiceImp.save(user8);
            userServiceImp.save(user9);
            userServiceImp.save(user10);
        }
    }
}
