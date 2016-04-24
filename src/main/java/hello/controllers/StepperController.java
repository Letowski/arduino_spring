package hello.controllers;

import hello.Application;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.lang.Integer;

@RestController
public class StepperController {


    @RequestMapping("/stepper/{stepperId}/left/{steps}")
    public void stepperLeft(@PathVariable(value="stepperId") Integer stepperId,
                            @PathVariable(value="steps") Integer steps) {
        Application.stepper.send(stepperId.toString()+"l"+steps.toString());
    }

    @RequestMapping("/stepper/{stepperId}/right/{steps}")
    public void stepperRight(@PathVariable(value="stepperId") Integer stepperId,
                            @PathVariable(value="steps") Integer steps) {
        Application.stepper.send(stepperId.toString()+"r"+steps.toString());
    }

}
