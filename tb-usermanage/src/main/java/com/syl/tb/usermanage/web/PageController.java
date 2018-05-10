package com.syl.tb.usermanage.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("page")
public class PageController {
    @RequestMapping(value = "{pageName}",method = RequestMethod.GET)
    public String toPage(@PathVariable("pageName")String pageName){
        System.out.println("---5464----");
        return pageName;
    }
}
