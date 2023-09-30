package com.jpm.moviesystem;

import com.jpm.moviesystem.command.Command;
import com.jpm.moviesystem.command.CommandFactory;
import com.jpm.moviesystem.command.CommandFactoryImpl;
import com.jpm.moviesystem.utils.UserControUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import java.io.BufferedReader;
import java.io.InputStreamReader;


@SpringBootApplication
@ComponentScan({"com.jpm.moviesystem.*"})
public class DemoApplication implements CommandLineRunner {


	private static String help = "" +
			"Command \t\tParameters\n" +
			"Setup          Admin only : <Show Number> <Number of Rows> <Number of seats per row>  <Cancellation window in minutes> \n" +
			"View           Admin only : <Show Number> \n" +

			"Availability    <Show Number>\n" +
			"BOOKSHOW        <Show Number> <Phone#> <Comma separated list of seats>  \n" +
			"CANCELBOOKING   <BookingId#>  <Phone#>\n" +
			"Quit            ";


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

	}


	@Override
	public void run(String... args) throws Exception {

		// Get user login details


		boolean isExit = false;
		CommandFactory factory = new CommandFactoryImpl();
		UserControUtils.getUserLoginDetails();
		try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));) {
			System.out.println(help);

			do {
				System.out.println("Enter command : ");
				String input = bufferedReader.readLine().trim();
				if (input != null && !input.isEmpty()) {
					if (input.equals("Quit")) {
						isExit = true;
						continue;
					}							}
				String[] inputs = input.split(" ");

				try {
					Command command = factory.buildCommand(inputs);
					command.execute();
				} catch (Exception e) {
					System.out.println(e.getMessage());
					continue;
				}

			} while (!isExit);
		}
		System.out.println("Thank you for using the system");

	}




	public void printhelp() {
		System.out.println("Sorry Invalid Command : Please use any of the below valid commands:");
		System.out.println(help);

	}
}


