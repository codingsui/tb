package com.syl.tb.web.controller;

import com.syl.tb.web.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("index")
public class IndexController {

    @Autowired
    private IndexService indexService;
    /**
     * 首页
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("index");
        String indexAD1 = indexService.queryIndexAD1();
        mv.addObject("indexAD1",indexAD1);

        String indexAD2 = indexService.queryIndexAD2();
        mv.addObject("indexAD2",indexAD1);
        return mv;
    }
}
