package com.example.kinocms_admin.controller;

import com.example.kinocms_admin.entity.CeoBlock;
import com.example.kinocms_admin.entity.New;
import com.example.kinocms_admin.entity.PageTranslation;
import com.example.kinocms_admin.entity.unifier.NewUnifier;
import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.mapper.NewMapper;
import com.example.kinocms_admin.model.NewDtoAdd;
import com.example.kinocms_admin.service.serviceimp.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class NewController {
    private final NewServiceImp newServiceImp;
    private final PageTranslationServiceImp pageTranslationServiceImp;
    private final CeoBlockServiceImp ceoBlockServiceImp;
    private final MarkServiceImp markServiceImp;

    @GetMapping("/news")
    public ModelAndView viewNews() {
        ModelAndView model = new ModelAndView("news/view-news");
        List<New> newsBD = newServiceImp.getAll();
        List<NewUnifier> unifiers = new ArrayList<>();
        for (New n : newsBD) {
            Optional<PageTranslation> translator = pageTranslationServiceImp.getByNewAndLanguageCode(n, LanguageCode.Ukr);
            unifiers.add(new NewUnifier(n,translator.get()));
        }
        model.addObject("news", NewMapper.toListDtoView(unifiers));
        return model;
    }

    @GetMapping("/new/add")
    public ModelAndView addNew(@ModelAttribute(name = "newEntity") NewDtoAdd dtoAdd) {
        ModelAndView model = new ModelAndView("news/new-add");
        return model;
    }
    @GetMapping("/new/{id}/edit")
    public ModelAndView editNew(@PathVariable Long id){
        ModelAndView model = new ModelAndView("news/new-edit");
        model.addObject("newId",id);
        return model;
    }
    @GetMapping("/new/{id}")
    @ResponseBody
    public NewDtoAdd getNewData(@PathVariable Long id){
        NewUnifier unifier = new NewUnifier();
        Optional<New> newBD = newServiceImp.getById(id);
        if(newBD.isPresent()){
            unifier.setNewEntity(newBD.get());
            unifier.setMarks(markServiceImp.getAllByNew(newBD.get()));

            Optional<CeoBlock> ceoBlockUkr = ceoBlockServiceImp.getByNewEntityAndLanguageCode(newBD.get(), LanguageCode.Ukr);
            Optional<CeoBlock> ceoBlockEng = ceoBlockServiceImp.getByNewEntityAndLanguageCode(newBD.get(), LanguageCode.Eng);
            ceoBlockUkr.ifPresent(unifier::setCeoBlockUkr);
            ceoBlockEng.ifPresent(unifier::setCeoBlockEng);

            Optional<PageTranslation> pageTranslationUkr = pageTranslationServiceImp.getByNewAndLanguageCode(newBD.get(),LanguageCode.Ukr);
            Optional<PageTranslation> pageTranslationEng = pageTranslationServiceImp.getByNewAndLanguageCode(newBD.get(),LanguageCode.Eng);

            pageTranslationUkr.ifPresent(unifier::setPageTranslationUkr);
            pageTranslationEng.ifPresent(unifier::setPageTranslationEng);

        }
        return NewMapper.toDtoAdd(unifier);
    }
    @GetMapping("/new/{id}/delete")
    public ModelAndView deleteNew(@PathVariable Long id){
        ModelAndView model = new ModelAndView("redirect:/admin/news");
        newServiceImp.deleteById(id);
        return model;
    }
}
