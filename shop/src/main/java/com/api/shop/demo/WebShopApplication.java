package com.api.shop.demo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.api.shop.demo.enums.RolesTypes;
import com.api.shop.demo.model.Permitions;
import com.api.shop.demo.model.Roles;
import com.api.shop.demo.model.User;
import com.api.shop.demo.repository.UserRepository;

@SpringBootApplication
public class WebShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebShopApplication.class, args);
	}

    //Create user adm - only use in local
	@Bean 
	CommandLineRunner inti(UserRepository uRepository){
		return args ->{
			User userAdm = new User();
			userAdm.setName("ShopAdm");
			userAdm.setEmail("shop_adm@shop.com");
			userAdm.setEnable(true);
            userAdm.setAccountNotExpired(true);
            userAdm.setAccountNotLocked(true);
            userAdm.setCredencialNotExpired(true);
			userAdm.setRoles(this.createAdmRole());

            String passwordEncode = new BCryptPasswordEncoder().encode("12345_test");
            userAdm.setPassword(passwordEncode);

			uRepository.save(userAdm);
		};
	}

	private Set<Roles> createAdmRole(){
        Roles role = new Roles();
        Permitions permitionToRead = new Permitions();
        permitionToRead.setName("READ_ADM");
        Permitions permitionToCreate = new Permitions();
        permitionToCreate.setName("CREATE_ADM");
		Permitions permitionToUpdate = new Permitions();
        permitionToUpdate.setName("UPDATE_ADM");

        Set<Roles> roles = new HashSet<>();
        Set<Permitions> permitions = new HashSet<>();

        permitions.addAll(List.of(permitionToCreate, permitionToRead, permitionToUpdate));
        role.setRole(RolesTypes.ADM);
        role.setPermitions(permitions);
        roles.add(role);
        return roles;
    }

}
	