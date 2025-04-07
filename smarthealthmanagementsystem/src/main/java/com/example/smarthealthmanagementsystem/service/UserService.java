package com.example.smarthealthmanagementsystem.service;

import com.example.smarthealthmanagementsystem.entity.User;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing user accounts.
 * Defines methods for creating, retrieving, updating, and deleting user records.
 */
public interface UserService {

    /**
     * Creates a new user account.
     *
     * @param user The User object to be created.
     * @return The created User object.
     */
    User createUser(User user);

    /**
     * Retrieves a list of all user accounts.
     *
     * @return A list of User objects.
     */
    List<User> getAllUsers();

    /**
     * Retrieves a user account by its ID.
     *
     * @param id The ID of the user account.
     * @return An Optional containing the User object, or an empty Optional if not found.
     */
    Optional<User> getUserById(Integer id);

    /**
     * Updates an existing user account.
     *
     * @param user The User object with updated information.
     * @return The updated User object.
     */
    User updateUser(User user);

    /**
     * Deletes a user account by its ID.
     *
     * @param id The ID of the user account to be deleted.
     */
    void deleteUser(Integer id);

    /**
     * Retrieves a user account by its username.
     *
     * @param username The username of the user account.
     * @return An Optional containing the User object, or an empty Optional if not found.
     */
    Optional<User> getUserByUsername(String username);

    /**
     * Checks if a username is already taken.
     *
     * @param username The username to check.
     * @return true if the username is taken, false otherwise.
     */
    boolean isUsernameTaken(String username);

    /**
     * Checks if an email is already taken.
     *
     * @param email The email to check.
     * @return true if the email is taken, false otherwise.
     */
    boolean isEmailTaken(String email);
}