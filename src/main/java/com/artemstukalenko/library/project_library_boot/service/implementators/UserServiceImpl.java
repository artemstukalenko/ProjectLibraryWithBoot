package com.artemstukalenko.library.project_library_boot.service.implementators;

import com.artemstukalenko.library.project_library_boot.dao.UserDAO;
import com.artemstukalenko.library.project_library_boot.entity.User;
import com.artemstukalenko.library.project_library_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDAO;

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public boolean blockUser(String username) {
        return userDAO.blockUser(username);
    }

    @Override
    public boolean unblockUser(String username) {
        return userDAO.unblockUser(username);
    }

    @Override
    public String getUserRole(String username) {
        return userDAO.getUserRole(username);
    }

    @Override
    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userDAO.findUserByUsername(username);
    }

    @Override
    public boolean registerUser(User user) {

        try {
            return userDAO.registerUser(user);
        } catch (Exception e) {
            
        }
        return false;
    }

    @Override
    public boolean deleteUser(String username) {
        return userDAO.deleteUser(username);
    }

    @Override
    public boolean makeUserLibrarian(String username) {
        return userDAO.makeUserLibrarian(username);
    }

    @Override
    public void updatePenaltyInfo(String username, int updateSum) {
        userDAO.updatePenaltyInfo(username, updateSum);
    }

    @Override
    public boolean depriveLibrarianPrivileges(String username) {
        return userDAO.depriveLibrarianPrivileges(username);
    }
}
