package hello.controllers;

import hello.Application;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sergey on 28.04.16.
 */
@RestController
public class ServoController {
    @RequestMapping("/servo/{servoId}/{steps}")
    public void stepperLeft(@PathVariable(value="servoId") Integer servoId,
                            @PathVariable(value="steps") Integer steps) {
        Application.stepper.send(servoId.toString()+steps.toString());
    }
}
