package com.revature.SpringWithLambda;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@SpringBootApplication
public class SpringWithLambdaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWithLambdaApplication.class, args);
	}

	//Below, I'll be defining functions that we will package and deploy onto Lambdas

	//I'll be commenting functions out to get unique jar files
	//in the real world, these functions will typically be separate apps

	@Bean //These functions are considered FUNCTIONAL BEANS - they're executed by lambda functions
	//this is a FUNCTION type method. takes input, returns output. in this case, Strings.
	public Function<String, String> uppercase(){

		return userInput -> userInput.toUpperCase(); //return the inputted String in uppercase

	}


	//get all items
	@Bean //Remember, we need these functions to be "functional beans" to be noticed by lambda
	public Supplier<List<String>> getItems(){
	
		//this is a supplier function, which will return data to the user
		
		//realistically, this is where we'd defer to the service (which would get data from the DAO etc)
		//but here, we'll just an ArrayList of Strings to represent items

		ArrayList<String> items = new ArrayList<>();
		items.add("soup");
		items.add("goop");
		items.add("a chicken coop");

		return () -> items; //remember, Java lambdas either need to take in some value(s) or just ()

	}

	//insert item
	@Bean
	public Consumer<String> insertItem(){

		//since this is a Consumer type function, we won't return anything
		//but we CAN see this inputted value printed in the logs for our Lambda

		return value -> System.out.println(value);

	}


	//get item by id (path param example)
	@Bean
	public Function<APIGatewayProxyRequestEvent, String> getItemById() {
		return input -> {
			//Extract path parameters from the event
			String itemId = input.getPathParameters().get("itemId");

			//The rest of the function logic here
			String response = "Received itemId: " + itemId;

			return response;
		};
	}

}
