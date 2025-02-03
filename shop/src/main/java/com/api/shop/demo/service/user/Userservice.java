package com.api.shop.demo.service.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.shop.demo.enums.RolesTypes;
import com.api.shop.demo.exception.CreatedResourceError;
import com.api.shop.demo.exception.GetResourceError;
import com.api.shop.demo.exception.ResourceNotFound;
import com.api.shop.demo.model.Permitions;
import com.api.shop.demo.model.Roles;
import com.api.shop.demo.model.User;
import com.api.shop.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Userservice implements UserServiceInter{

    private final UserRepository userRepository;

    @Override
    public Optional<List<User>> getAll() {
        try {
            List<User> users = this.userRepository.findAll();
            return Optional.of(users);
        } catch (Exception error) {
            throw new GetResourceError("Error to get users: " + error.getMessage());
        }
    }

    @Override
    public Optional<User> addCustomer(User user) {
        try {
            user.setEnable(true);
            user.setAccountNotExpired(true);
            user.setAccountNotLocked(true);
            user.setCredencialNotExpired(true);
            user.setRoles(createCustomerRole());

            String passwordEncode = new BCryptPasswordEncoder().encode(user.getPassword());
            user.setPassword(passwordEncode);

            User userAdded = this.userRepository.save(user);
            return Optional.of(userAdded);
        } catch (Exception error) {
            throw new CreatedResourceError("Error to create user: " + error.getMessage());
        }
    }

    @Override
    public Optional<User> update(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Optional<User> getById(Long id) {
        try {
            Optional<User> user = this.userRepository.findById(id);
            return user;
        } catch (Exception error) {
            throw new GetResourceError("Error to create user: " + error.getMessage());
        }
    }

    @Override
    public Optional<User> getuserByEmailAndPassword(String email, String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getuserByEmailAndPassword'");
    }

    @Override
    public void deleteById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }


    private Set<Roles> createCustomerRole(){
        Roles role = new Roles();
        Permitions permitionToRead = new Permitions();
        permitionToRead.setName("READ");
        Permitions permitionToCreate = new Permitions();
        permitionToCreate.setName("CREATE");

        Set<Roles> roles = new HashSet<>();
        Set<Permitions> permitions = new HashSet<>();

        permitions.addAll(List.of(permitionToCreate, permitionToRead));
        role.setRole(RolesTypes.CUSTOMER);
        role.setPermitions(permitions);
        roles.add(role);
        return roles;
    }

 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         try {
            User user = this.userRepository.findUserByName(username)
                .orElseThrow( ()-> new ResourceNotFound("Order not found id " ) );
           
            List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();

            user.getRoles().forEach(role -> {
                simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRole().name())));
            });

            user.getRoles().stream()
                .flatMap(role -> role.getPermitions().stream())
                .forEach(permirion -> {
                    simpleGrantedAuthorities.add(new SimpleGrantedAuthority(permirion.getName()));
                });


            org.springframework.security.core.userdetails.User userDetails = 
                new org.springframework.security.core.userdetails.User(
                    user.getName(),
                        user.getPassword(),
                        user.isEnable(),
                        user.isAccountNotExpired(),
                        user.isCredencialNotExpired(),
                        user.isAccountNotLocked(),
                        simpleGrantedAuthorities);
                
            return userDetails; 
        } catch (Exception error) {
            throw new ResourceNotFound("Error to get user by name " + username  + " Error: " + error.getMessage() );
        }
    }

}
