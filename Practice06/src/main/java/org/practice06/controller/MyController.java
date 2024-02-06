package org.practice06.controller;

import org.practice06.exception.CustomException;
import org.practice06.exception.MyException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {



    @GetMapping("/throwCustomException")
    public void throwException(){
            throw new CustomException("This is a custom exception .");
    }
    @GetMapping("/throwMyException")
    public void throwException2(){
        throw new MyException("This is my  exception .");
    }


}
