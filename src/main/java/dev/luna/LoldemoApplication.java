package dev.luna;

import dev.luna.mongo.MorphiaDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoldemoApplication {

	public static void main(String[] args) {
        MorphiaDatabase.getInstance();
		SpringApplication.run(LoldemoApplication.class, args);
	}
}
