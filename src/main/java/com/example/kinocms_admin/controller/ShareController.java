package com.example.kinocms_admin.controller;

import com.example.kinocms_admin.entity.CeoBlock;
import com.example.kinocms_admin.entity.PageTranslation;
import com.example.kinocms_admin.entity.Share;
import com.example.kinocms_admin.entity.unifier.ShareUnifier;
import com.example.kinocms_admin.enums.LanguageCode;
import com.example.kinocms_admin.mapper.ShareMapper;
import com.example.kinocms_admin.model.ShareDTOAdd;
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
public class ShareController {
    private final ShareServiceImp shareServiceImp;
    private final PageTranslationServiceImp pageTranslationServiceImp;
    private final CeoBlockServiceImp ceoBlockServiceImp;
    private final MarkServiceImp markServiceImp;

    @GetMapping("/shares")
    public ModelAndView viewNews() {
        ModelAndView model = new ModelAndView("shares/view-shares");
        List<Share> shareBD = shareServiceImp.getAll();
        List<ShareUnifier> unifiers = new ArrayList<>();
        for (Share n : shareBD) {
            Optional<PageTranslation> translator = pageTranslationServiceImp.getByShareAndLanguageCode(n, LanguageCode.Ukr);
            translator.ifPresent(t->unifiers.add(new ShareUnifier(n,t)));
        }
        model.addObject("shares", ShareMapper.toListDtoView(unifiers));
        return model;
    }

    @GetMapping("/share/add")
    public ModelAndView addNew(@ModelAttribute(name = "newEntity") ShareDTOAdd dtoAdd) {
        ModelAndView model = new ModelAndView("shares/share-add");
        return model;
    }
    @GetMapping("/share/{id}/edit")
    public ModelAndView editNew(@PathVariable Long id){
        ModelAndView model = new ModelAndView("shares/share-edit");
        model.addObject("shareId",id);
        return model;
    }
    @GetMapping("/share/{id}")
    @ResponseBody
    public ShareDTOAdd getNewData(@PathVariable Long id){
        ShareUnifier unifier = new ShareUnifier();
        Optional<Share> shareBD = shareServiceImp.getById(id);
        if(shareBD.isPresent()){
            unifier.setShare(shareBD.get());
            unifier.setMarks(markServiceImp.getAllByNew(shareBD.get()));

            Optional<CeoBlock> ceoBlockUkr = ceoBlockServiceImp.getByShareAndLanguageCode(shareBD.get(), LanguageCode.Ukr);
            Optional<CeoBlock> ceoBlockEng = ceoBlockServiceImp.getByShareAndLanguageCode(shareBD.get(), LanguageCode.Eng);
            ceoBlockUkr.ifPresent(unifier::setCeoBlockUkr);
            ceoBlockEng.ifPresent(unifier::setCeoBlockEng);

            Optional<PageTranslation> pageTranslationUkr = pageTranslationServiceImp.getByShareAndLanguageCode(shareBD.get(),LanguageCode.Ukr);
            Optional<PageTranslation> pageTranslationEng = pageTranslationServiceImp.getByShareAndLanguageCode(shareBD.get(),LanguageCode.Eng);

            pageTranslationUkr.ifPresent(unifier::setPageTranslationUkr);
            pageTranslationEng.ifPresent(unifier::setPageTranslationEng);

        }
        return ShareMapper.toDTOAdd(unifier);
    }
    @GetMapping("/share/{id}/delete")
    public ModelAndView deleteNew(@PathVariable Long id){
        ModelAndView model = new ModelAndView("redirect:/admin/shares");
        shareServiceImp.deleteById(id);
        return model;
    }
}
