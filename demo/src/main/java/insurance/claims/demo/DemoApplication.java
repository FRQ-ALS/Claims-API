package insurance.claims.demo;

import insurance.claims.demo.common.DVLARegApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
	static DVLARegApi dvla = new DVLARegApi();
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		dvla.getVehicleDetails("BL17UNV");
	}
}
