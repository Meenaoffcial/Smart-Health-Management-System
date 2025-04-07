package com.example.smarthealthmanagementsystem.service.impl;

import com.example.smarthealthmanagementsystem.entity.User;
import com.example.smarthealthmanagementsystem.repository.UserRepository;
import com.example.smarthealthmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the UserService interface.
 * Provides methods for managing user accounts.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Constructor to inject UserRepository.
     *
     * @param userRepository Repository for User entities.
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates a new user account.
     *
     * @param user The User object to be created.
     * @return The created User object.
     */
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Retrieves a list of all user accounts.
     *
     * @return A list of User objects.
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user account by its ID.
     *
     * @param id The ID of the user account.
     * @return An Optional containing the User object, or an empty Optional if not found.
     */
    @Override
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    /**
     * Updates an existing user account.
     *
     * @param user The User object with updated information.
     * @return The updated User object.
     */
    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Deletes a user account by its ID.
     *
     * @param id The ID of the user account to be deleted.
     */
    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    /**
     * Retrieves a user account by its username.
     *
     * @param username The username of the user account.
     * @return An Optional containing the User object, or an empty Optional if not found.
     */
    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Checks if a username is already taken.
     *
     * @param username The username to check.
     * @return true if the username is taken, false otherwise.
     */
    @Override
    public boolean isUsernameTaken(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Checks if an email is already taken.
     *
     * @param email The email to check.
     * @return true if the email is taken, false otherwise.
     */
    @Override
    public boolean isEmailTaken(String email) {
        return userRepository.existsByEmail(email);
    }
}