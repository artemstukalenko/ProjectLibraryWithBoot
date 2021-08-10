package com.artemstukalenko.library.project_library_boot.dao;

import com.artemstukalenko.library.project_library_boot.entity.User;
import java.util.List;

public interface UserDAO {

    public List<User> getAllUsers();

    public boolean blockUser(String username);

    public boolean unblockUser(String username);

    public String getUserRole(String username);

    public boolean registerUser(User user);

    public User findUserByUsername(String username);

    public boolean updateUser(User user);

    public boolean deleteUser(String username);

    public boolean makeUserLibrarian(String username);

    public boolean depriveLibrarianPrivileges(String username);

    public void updatePenaltyInfo(String username, int updateSum);
}
