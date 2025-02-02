package com.api.shop.demo.service.user;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.shop.demo.exception.CreatedResourceError;
import com.api.shop.demo.exception.GetResourceError;
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
    public Optional<User> add(User user) {
        try {
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

}
