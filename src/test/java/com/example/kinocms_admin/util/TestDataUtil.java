package com.example.kinocms_admin.util;

import com.example.kinocms_admin.entity.*;
import com.example.kinocms_admin.enums.BannerType;
import com.example.kinocms_admin.enums.GalleriesType;
import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.enums.PageType;

import java.time.LocalDate;
import java.util.*;

public class TestDataUtil {
    public static List<Mark> loadMarks() {
        List<Mark> marks = new ArrayList<>();
        marks.add(new Mark(1L, "18+"));
        marks.add(new Mark(2L, "VIP"));
        marks.add(new Mark(3L, "NO SEX"));
        return marks;
    }

    public static <T> Set<T> getElements(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            return Collections.emptySet();
        }
        int size = collection.size();
        Random random = new Random();
        int randomCount = random.nextInt(size) + 1;
        List<T> list = new ArrayList<>(collection);
        Set<T> resultList = new HashSet<>();
        Set<Integer> chosenIndexes = new HashSet<>();
        while (resultList.size() < randomCount) {
            int randomIndex = random.nextInt(size);
            if (!chosenIndexes.contains(randomIndex)) {
                resultList.add(list.get(randomIndex));
                chosenIndexes.add(randomIndex);
            }
        }
        return resultList;
    }

    public static List<Film> loadFilms() {
        LocalDate today = LocalDate.now();
        Film film1 = new Film();
        film1.setId(1L);
        film1.setDateStart(today.minusDays(10));
        film1.setDateEnd(today.plusDays(10));
        film1.setPageTranslations(
                List.of(new PageTranslation(LanguageCode.Ukr, PageType.film, "Людина павук", "Опис...",
                        null, new Film())));
        film1.setNameImage("image-name.jpg");
        film1.setGalleries(loadGallery());

        Film film2 = new Film();
        film2.setId(2L);
        film2.setDateStart(today.plusDays(10));
        film2.setDateEnd(today.plusDays(10));

        Film film3 = new Film();
        film3.setId(3L);
        film3.setDateStart(today.minusDays(10));
        film3.setDateEnd(today.minusDays(10));

        Film film4 = new Film();
        film4.setId(4L);
        film4.setDateStart(today.plusDays(10));
        film4.setDateEnd(today.minusDays(10));

        Film film5 = new Film();
        film5.setId(5L);
        film5.setDateStart(today);
        film5.setDateEnd(today);
        return List.of(film1, film2, film3, film4, film5);
    }

    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "Roman", "Pravnyk", "zinda", "123456789", "roomich20031@gmail.com", "New York", "password123", "1111222233334444", LanguageCode.Ukr, true, LocalDate.of(1990, 5, 15), false));
        users.add(new User(2L, "Jane", "Smith", "jsmith", "987654321", "romich20031@gmail.com", "Los Angeles", "password456", "2222333344445555", LanguageCode.Ukr, false, LocalDate.of(1985, 8, 22), true));
        users.add(new User(3L, "Alex", "Johnson", "ajohnson", "456123789", "roman.pravnyk.TR.2021@lpnu.ua", "Chicago", "password789", "3333444455556666", LanguageCode.Eng, true, LocalDate.of(1995, 2, 10), false));
        users.add(new User(4L, "Emily", "Brown", "ebrown", "321654987", "emily.brown@gmail.com", "Houston", "password101", "4444555566667777", LanguageCode.Eng, false, LocalDate.of(1992, 11, 30), true));
        users.add(new User(5L, "Michael", "Davis", "mdavis", "789123456", "michael.davis@icloud.com", "Miami", "password202", "5555666677778888", LanguageCode.Eng, true, LocalDate.of(1988, 7, 5), true));
        return users;
    }

    public static List<Template> loadTemplates() {
        List<Template> templates = new ArrayList<>();
        templates.add(new Template(1L, "nameFile1"));
        templates.add(new Template(2L, "nameFile2"));
        templates.add(new Template(3L, "nameFile3"));
        return templates;
    }

    public static List<Share> loadShares() {
        Set<Mark> shareMarks = new HashSet<>(loadMarks());

        Share share1 = new Share(1L, "image1.jpg", "banner1.jpg", LocalDate.of(2022, 5, 1), true, "url1",
                List.of(new PageTranslation(1L, LanguageCode.Ukr, PageType.share, "Нічна п'ятниця", "бла бла", null)),
                List.of(new CeoBlock(1L, LanguageCode.Ukr, PageType.share, "title1", "keywords1", "description1", null)),
                shareMarks);
        Share share2 = new Share(2L, "image2.jpg", "banner2.jpg", LocalDate.of(2022, 6, 1), false, "url2",
                List.of(new PageTranslation(2L, LanguageCode.Ukr, PageType.share, "Дика неділя", "бла2 бла2", null)),
                List.of(new CeoBlock(2L, LanguageCode.Ukr, PageType.share, "title2", "keywords2", "description2", null)),
                getElements(loadMarks()));
        return Arrays.asList(share1, share2);
    }

    public static List<PageTranslation> loadTranslators() {
        PageTranslation p1 = new PageTranslation(1L, LanguageCode.Ukr, PageType.film, "Людина павук", "Опис...", null);
        PageTranslation p2 = new PageTranslation(2L, LanguageCode.Eng, PageType.cinema, "Cinema Ukraine", "desc...", null);
        return Arrays.asList(p1, p2);
    }

    public static List<Page> loadPages() {
        Page page1 = new Page(true, PageType.film, LocalDate.of(2023, 9, 23));
        page1.setId(1L);
        Page page2 = new Page(false, PageType.cinema, LocalDate.of(2023, 8, 15));
        page2.setId(2L);
        Page page3 = new Page(true, PageType.hall, LocalDate.of(2023, 7, 10));
        page3.setId(3L);
        return List.of(page1, page2, page3);
    }

    public static List<New> loadNews() {
        New new1 = new New(1L, "image1.jpg", LocalDate.now(), true, "http://example.com/1", null, null, new HashSet<>());
        New new2 = new New(null, "image2.jpg", LocalDate.now(), false, "http://example.com/2", null, null, new HashSet<>());
        New new3 = new New(3L, "image3.jpg", LocalDate.now(), true, "http://example.com/3", null, null, new HashSet<>());
        return List.of(new1, new2, new3);
    }

    public static List<Hall> loadHalls() {
        Hall hall1 = new Hall(1L, 1, "Schema 1", "Banner 1", "http://example.com/ceo1", LocalDate.now(), new Cinema(), null, null, null);
        Hall hall2 = new Hall(2L, 2, "Schema 2", "Banner 2", "http://example.com/ceo2", LocalDate.now(), new Cinema(), null, null, null);
        Hall hall3 = new Hall(3L, 3, "Schema 3", "Banner 3", "http://example.com/ceo3", LocalDate.now(), new Cinema(), null, null, null);
        return List.of(hall1, hall2, hall3);
    }

    public static List<Genre> loadGenres() {
        return List.of(new Genre("fantastic"), new Genre("horror"), new Genre("mystic"));
    }

    public static List<Gallery> loadGallery() {
        Gallery gallery1 = new Gallery(null, GalleriesType.pages, new Page());
        gallery1.setId(1L);
        Gallery gallery2 = new Gallery(null, GalleriesType.films, new Film());
        gallery2.setId(2L);
        Gallery gallery3 = new Gallery("image3.jpg", null, new Cinema());
        gallery3.setId(3L);
        return List.of(gallery1, gallery2, gallery3);
    }

    public static List<Contact> loadContact() {
        Contact contact1 = new Contact(1L, "John Doe", "123 Main Street, Springfield",
                "45.4215,-75.6972", "john_logo.png", new Page()
        );

        Contact contact2 = new Contact(
                2L, "Jane Smith", "456 Elm Street, Gotham",
                "40.7128,-74.0060", "jane_logo.png", new Page()
        );

        Contact contact3 = new Contact(
                3L, "Acme Corp", "789 Oak Street, Metropolis",
                "37.7749,-122.4194", "acme_logo.png", new Page()
        );
        return List.of(contact1, contact2, contact3);
    }

    public static List<Cinema> loadCinemas() {
        Cinema cinema1 = new Cinema(1L, "logo_cinema1.png", "banner_cinema1.png",
                "https://example.com/ceo/cinema1", LocalDate.of(2020, 5, 20),
                new ArrayList<>(), new ArrayList<>(), new HashSet<>(), new ArrayList<>()
        );

        Cinema cinema2 = new Cinema(2L, "logo_cinema2.png", "banner_cinema2.png",
                "https://example.com/ceo/cinema2", LocalDate.of(2018, 10, 15),
                new ArrayList<>(), new ArrayList<>(), new HashSet<>(), new ArrayList<>()
        );

        Cinema cinema3 = new Cinema(null, "logo_cinema3.png", "banner_cinema3.png",
                "https://example.com/ceo/cinema3", LocalDate.of(2022, 1, 5),
                new ArrayList<>(), new ArrayList<>(), new HashSet<>(), new ArrayList<>()
        );
        return List.of(cinema1, cinema2, cinema3);
    }

    public static List<CeoBlock> loadCeoBlock() {
        CeoBlock ceoBlock1 = new CeoBlock(1L, LanguageCode.Ukr,
                PageType.film, "Title 1", "Keywords 1",
                "Description for page 1", "SEO text for page 1"
        );

        CeoBlock ceoBlock2 = new CeoBlock(2L, LanguageCode.Eng,
                PageType.film, "Title 2", "Keywords 2",
                "Description for page 2", "SEO text for page 2"
        );

        CeoBlock ceoBlock3 = new CeoBlock(3L, LanguageCode.Ukr,
                PageType.cinema, "Title 3", "Keywords 3",
                "Description for page 3", "SEO text for page 3"
        );

        return List.of(ceoBlock1, ceoBlock2, ceoBlock3);
    }

    public static List<BannerImage> loadBannerImage() {
        BannerImage bannerImage1 = new BannerImage(1L, "Image1", "Sample text 1", "http://example.com/image1", new Banner());
        BannerImage bannerImage2 = new BannerImage(2L, "Image2", "Sample text 2", "http://example.com/image2", new Banner());
        BannerImage bannerImage3 = new BannerImage(3L, "Image3", "Sample text 3", "http://example.com/image3", new Banner());
        return List.of(bannerImage1, bannerImage2, bannerImage3);
    }
    public static List<Banner> loadBanner(){
        Banner banner1 = new Banner(1L, 5, true, BannerType.main, null);
        Banner banner2 = new Banner(2L, 0, false, BannerType.shareAndNew, null);
        Banner banner3 = new Banner(3L, 10, true, BannerType.main, null);
        return List.of(banner1,banner2,banner3);
    }
}
