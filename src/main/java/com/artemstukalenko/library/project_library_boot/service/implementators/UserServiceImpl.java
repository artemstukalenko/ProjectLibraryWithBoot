package com.artemstukalenko.library.project_library_boot.service.implementators;

import com.artemstukalenko.library.project_library_boot.entity.Authority;
import com.artemstukalenko.library.project_library_boot.entity.User;
import com.artemstukalenko.library.project_library_boot.repositories.UserRepository;
import com.artemstukalenko.library.project_library_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public boolean blockUser(String username) {
        userRepository.blockUser(username);
        return true;
    }

    @Override
    @Transactional
    public boolean unblockUser(String username) {
        userRepository.unblockUser(username);
        return true;
    }

    @Override
    public String getUserRole(String username) {
        return userRepository.getUserRole(username);
    }

    @Override
    public boolean updateUser(User user) {
        userRepository.save(user);
        return true;
    }

    @Override
    public User findUserByUsername(String username) {
        Optional<User> foundUser = userRepository.findById(username);
        return Optional.ofNullable(foundUser.get()).orElse(new User());
    }

    @Override
    @Transactional
    public boolean registerUser(User user) {
        Authority newUserAuthority = new Authority(user.getUsername(), "ROLE_USER");
        user.setPassword(getEncodedPassword(user.getPassword()));

        userRepository.save(user);
        entityManager.persist(newUserAuthority);

        return true;
    }

    @Override
    @Transactional
    public boolean deleteUser(String username) {
        userRepository.deleteAuthorityByUsername(username);
        userRepository.deleteUserDetailsByUsername(username);
        userRepository.updateSubscriptionBooksInfo(username);
        userRepository.deleteUserSubscriptionsByUsername(username);
        userRepository.deleteById(username);

        return true;
    }

    @Override
    @Transactional
    public boolean makeUserLibrarian(String username) {
        userRepository.makeUserLibrarian(username);
        return true;
    }

    @Override
    @Transactional
    public boolean depriveLibrarianPrivileges(String username) {
        userRepository.depriveLibrarianPrivileges(username);
        return true;
    }

    @Override
    @Transactional
    public void updatePenaltyInfo(String username, int updateSum) {
        userRepository.updatePenaltyInfo(username, updateSum);
    }

    private String getEncodedPassword(String rawPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

        return "{bcrypt}" + encoder.encode(rawPassword);
    }
}
