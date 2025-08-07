package org.scoula.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpaController {

    // 1. 루트("/") 요청도 index.html로 forward
    @RequestMapping(value = {"/", "/{path:^(?!api|resources|assets|webjars|swagger).*}"})
    public String forward() {
        return "forward:/resources/index.html";
    }
}