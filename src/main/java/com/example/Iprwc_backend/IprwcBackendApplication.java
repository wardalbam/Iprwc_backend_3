package com.example.Iprwc_backend;

import com.example.Iprwc_backend.DAO.AddressRepo;
import com.example.Iprwc_backend.DAO.OrderLineRepo;
import com.example.Iprwc_backend.DAO.OrderRepo;
import com.example.Iprwc_backend.DAO.ProductRepo;
import com.example.Iprwc_backend.Model.Address;
import com.example.Iprwc_backend.Model.OrderDetails;
import com.example.Iprwc_backend.Model.OrderLine;
import com.example.Iprwc_backend.Model.OrderStatus;
import com.example.Iprwc_backend.Model.Product;
import com.example.Iprwc_backend.Model.ProductStatus;
import com.example.Iprwc_backend.Model.Role;
import com.example.Iprwc_backend.Model.User;
import com.example.Iprwc_backend.Service.OrderService;
import com.example.Iprwc_backend.Service.ProductService;
import com.example.Iprwc_backend.Service.UserService;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class IprwcBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(IprwcBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner run(
		UserService userService, 
		ProductRepo productRepo, 
		OrderRepo orderRepo,
		AddressRepo addressRepo,
		OrderLineRepo orderLineRepo,
		OrderService orderService){
		return args -> {
			userService.saveRole(new Role("ROLE_USER"));
			userService.saveRole(new Role("ROLE_MANAGER"));
			userService.saveRole(new Role("ROLE_ADMIN"));

			Address addressToUser = new Address();
			addressToUser.setLand("Nederland");
			addressToUser.setZipcode("2544kn");
			addressToUser.setCity("DenHaag");
			addressRepo.save(addressToUser);

			userService.saveUser(new User(null, "Ward albam", "ward", "1234",addressToUser.getId(), "email.test@gmail" , new ArrayList<>()  ));
			userService.saveUser(new User(null, "khaled jad", "khaledjad", "54321",addressToUser.getId(), "email.test@gmail", new ArrayList<>()));
			userService.saveUser(new User(null, "ibrahim", "ibra", "090909",null,"email.test@gmail" , new ArrayList<>() ));
			userService.saveUser(new User(null, "sam", "sam", "1234",null, "email.test@gmail" , new ArrayList<>() ));


			userService.addRoleToUser("ward", "ROLE_ADMIN");
			userService.addRoleToUser("ward", "ROLE_MANAGER");
			userService.addRoleToUser("khaledjad", "ROLE_MANAGER");
			userService.addRoleToUser("ibra", "ROLE_USER");
			userService.addRoleToUser("sam", "ROLE_USER");

			Product newProduct1 = new Product();
			newProduct1.setName("electrical Guitar");
			newProduct1.setPrice(260.99);
			newProduct1.setImagePath(new URL("https://images.musicstore.de/images/0960/prs-se-standard-24-08-translucent-blue_1_GIT0058305-000.jpg"));
			newProduct1.setDescription(" Elektrische gitaar met gelijmde Wide Thin esdoorn hals, TCI S pickups en PRS Designed Tremolo Uit de PRS SE serie Transparant blauwe hoogglans finish");
			newProduct1.setProductStatus(ProductStatus.HIDDEN);
			productRepo.save(newProduct1);

			Product newProduct2 = new Product();
			newProduct2.setName("electrical Guitar 2");
			newProduct2.setPrice(230.99);
			newProduct2.setImagePath(new URL("https://images.musicstore.de/images/0960/prs-se-standard-24-08-translucent-blue_1_GIT0058305-000.jpg"));
			newProduct2.setDescription(" Elektrische gitaar met gelijmde Wide Thin esdoorn hals, TCI S pickups en PRS Designed Tremolo Uit de PRS SE serie Transparant blauwe hoogglans finish");
			productRepo.save(newProduct2);

			Product newProduct3 = new Product(
				null,
				"electrical Guitar 2",
				410.99,
				new URL("https://static.bax-shop.nl/image/product/987298/3731630/a9c62b9e/1658841321VSA500CR_1024x1024.jpg"),
				" Elektrische gitaar met gelijmde Wide Thin esdoorn hals, TCI S pickups en PRS Designed Tremolo Uit de PRS SE serie Transparant blauwe hoogglans finish",
				ProductStatus.ACTIVE
			);
			productRepo.save(newProduct3);

			Product newProduct4 = new Product(
				null,
				"Vintage VSA500 ReIssued Cherry Red",
				130.00,
				new URL("https://static.bax-shop.nl/image/product/681831/2642625/edd27103/1611928817_MG_6512.jpg"),
				" Elektrische gitaar met gelijmde Wide Thin esdoorn hals, TCI S pickups en PRS Designed Tremolo Uit de PRS SE serie Transparant blauwe hoogglans finish",
				ProductStatus.ACTIVE
			);
			productRepo.save(newProduct4);

			Product newProduct5 = new Product(
				null,
				"Fazley FHM618BK Black elektrische gitaar met skull & crossbones positiemarkeringen",
				190.99,
				new URL("https://static.bax-shop.nl/image/product/781052/3406177/c9201e14/164561432120220223-Fazley%20FHM618BK_7.jpg"),
				" Elektrische gitaar met gelijmde Wide Thin esdoorn hals, TCI S pickups en PRS Designed Tremolo Uit de PRS SE serie Transparant blauwe hoogglans finish",
				ProductStatus.ACTIVE
			);
			productRepo.save(newProduct5);
		

			Address address = new Address();
			address.setLand("Nederland");
			address.setZipcode("2544kn");
			address.setCity("DenHaag");
			addressRepo.save(address);


			OrderDetails order = new OrderDetails(
				new ArrayList<>(),
				userService.getUser("ibra").getUsername(),
				address,
				0,
				OrderStatus.PROCESSING
			);

			OrderDetails order_2 = new OrderDetails(
				new ArrayList<>(),
				userService.getUser("ibra").getUsername(),
				address,
				0,
				OrderStatus.PROCESSING
			);
			

			// line 1
			OrderLine Line_1 = new OrderLine();
			Line_1.setAmount(1);
			Line_1.setProductId(newProduct1.getId());
			Line_1.setOrderId(order.getId());
			Line_1.setProduct_name(newProduct1.getName());
			Line_1.setProduct_price(newProduct1.getPrice());
			Line_1.setProduct_image(newProduct1.getImagePath());
			orderLineRepo.save(Line_1);

			// line 2
			OrderLine Line_2 = new OrderLine();
			Line_2.setAmount(1);
			Line_2.setProductId(newProduct2.getId());
			Line_2.setOrderId(order_2.getId());
			Line_2.setProduct_name(newProduct2.getName());
			Line_2.setProduct_price(newProduct2.getPrice());
			Line_2.setProduct_image(newProduct2.getImagePath());
			orderLineRepo.save(Line_2);

			// line e
			OrderLine Line_3 = new OrderLine();
			Line_3.setAmount(1);
			Line_3.setProductId(newProduct3.getId());
			Line_3.setOrderId(order_2.getId());
			Line_3.setProduct_name(newProduct3.getName());
			Line_3.setProduct_price(newProduct3.getPrice());
			Line_3.setProduct_image(newProduct3.getImagePath());
			orderLineRepo.save(Line_3);


			// add line1 to order to database
			Collection<OrderLine> list = new ArrayList<>();
			list.add(Line_1);
			order.setOrederLines(list);
			double totalPrice = 0;
			for (OrderLine orderLine : list) {
				totalPrice += orderLine.getAmount() * productRepo.findById(orderLine.getProductId()).get().getPrice();
			}
			order.setTotalPrice(totalPrice);
			orderRepo.save(order);

			// add order_2 to database
			Collection<OrderLine> list_2 = new ArrayList<>();
			list_2.add(Line_2);
			list_2.add(Line_3);
			order_2.setOrederLines(list_2);
			double totalPrice_2 = 0;
			for (OrderLine orderLine : list_2) {
				totalPrice_2 += orderLine.getAmount() * productRepo.findById(orderLine.getProductId()).get().getPrice();
			}
			order_2.setTotalPrice(totalPrice_2);
			orderRepo.save(order_2);

			
			

			
		};
	}
	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
}

