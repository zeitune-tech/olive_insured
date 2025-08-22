package sn.zeitune.oliveinsuranceinsured;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class OliveInsuranceInsuredApplication {

    public static void main(String[] args) {
        SpringApplication.run(OliveInsuranceInsuredApplication.class, args);
    }

}
