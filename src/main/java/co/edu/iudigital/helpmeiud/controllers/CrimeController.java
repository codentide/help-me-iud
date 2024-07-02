package co.edu.iudigital.helpmeiud.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crimes")
public class CrimeController {

    @GetMapping
    public void test(){

    }

}
