package edu.sharif.simplecalc.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleCalcController {


    @RequestMapping(value = "/calc/{operator}/{x}/{y}", method = RequestMethod.GET)
    public String getFoosBySimplePath(@PathVariable(value="operator") String operator, @PathVariable(value="x") int x, @PathVariable(value="y") int y) {
        switch (operator){
            case "add":
                return x + " " + "+" + " " + y + " = " + (x+y);
            case "sub":
                return x + " " + "-" + " " + y + " = " + (x-y);
            case "mult":
                return x + " " + "*" + " " + y + " = " + (x*y);
            case "divide":
                return x + " " + "÷" + " " + y + " = " + ((long) x/(long) y);
        }
        return "";
    }

}
