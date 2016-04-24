package hello;

import hello.models.Stepper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;


@SpringBootApplication
public class Application implements CommandLineRunner {
    public static Stepper stepper;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        stepper = new Stepper();
        System.out.println("ok");
    }

    @Override
    public void run(String... strings) throws Exception {

    }


}
