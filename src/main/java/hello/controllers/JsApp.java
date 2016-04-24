package hello.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;

@Controller
public class JsApp {
//    @RequestMapping(value = "/jsapp/{path}", method = RequestMethod.GET)
//    public String redirect(@PathVariable("path") String path)
//    {
//        return "redirect:/#/jsapp/" + path;
//    }

//    @RequestMapping(value={"/**"}, method=RequestMethod.GET)
//    @ResponseBody
//    public String spa(HttpServletRequest request){
//        return "redirect:/#" + request.getRequestURI();
//
////        return "redirect:/index.html#";
//        //return path;
//    }
}
