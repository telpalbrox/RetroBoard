package dev.luna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoldemoApplication {

	public static void main(String[] args) {
//		RethinkConnection connection = RethinkConnection.getInstance();
//		RethinkDB r = RethinkDB.r;
//		Map result = r.table("boards").insert(r.hashMap("name", "testsasdf")).run(connection.conn);
//		List generatedKeys = (List) result.get("generated_keys");
//		System.out.println(generatedKeys.get(0));
		SpringApplication.run(LoldemoApplication.class, args);
	}
}
