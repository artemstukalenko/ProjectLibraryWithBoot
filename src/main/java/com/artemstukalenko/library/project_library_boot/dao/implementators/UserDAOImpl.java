package com.artemstukalenko.library.project_library_boot.dao.implementators;

import com.artemstukalenko.library.project_library_boot.dao.UserDAO;
import com.artemstukalenko.library.project_library_boot.entity.Authority;
import com.artemstukalenko.library.project_library_boot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        Query queryForGettingAllUsers = entityManager.createQuery("from User where username not in (from Authority where authority = 'ROLE_ADMIN')");

        return queryForGettingAllUsers.getResultList();
    }

    @Override
    @Transactional
    public boolean blockUser(String username) {
        Query queryForBlockingUser = entityManager.createQuery("update User set enabled = 0 where username =: userUsername");

        queryForBlockingUser.setParameter("userUsername", username);
        queryForBlockingUser.executeUpdate();
        return true;
    }

    @Override
    @Transactional
    public boolean unblockUser(String username) {
        Query queryForUnblockingUser = entityManager.createQuery("update User set enabled = 1 where username =: userUsername");

        queryForUnblockingUser.setParameter("userUsername", username);
        queryForUnblockingUser.executeUpdate();
        return true;
    }

    @Override
    public String getUserRole(String username) {
        Query queryForGettingUserRole = entityManager.createQuery("select authority from Authority where username in " +
                "(select username from User where username =: usernameForSearch)");

        queryForGettingUserRole.setParameter("usernameForSearch", username);
        return (String) queryForGettingUserRole.getSingleResult();

    }

    @Override
    public User findUserByUsername(String username) {
        return entityManager.find(User.class, username);
    }

    @Override
    @Transactional
    public boolean updateUser(User user) {
        entityManager.merge(user);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteUser(String username) {
        deleteAuthority(username);

        deleteUserDetails(username);

        deleteUserSubscriptions(username);

        Query queryForDeletingUser = entityManager.createQuery("delete from User where username =: username");
        queryForDeletingUser.setParameter("username", username);
        queryForDeletingUser.executeUpdate();

        return true;
    }

    protected void deleteAuthority(String username) {
        Query queryForDeletingAuthority = entityManager.createQuery("delete from Authority where username =: username");
        queryForDeletingAuthority.setParameter("username", username);
        queryForDeletingAuthority.executeUpdate();
    }

    protected void deleteUserDetails(String username) {
        Query queryForDeletingUserDetails = entityManager.createQuery("delete from UserDetails where username =: username");
        queryForDeletingUserDetails.setParameter("username", username);
        queryForDeletingUserDetails.executeUpdate();
    }

    protected void deleteUserSubscriptions(String username) {
        Query queryForUpdatingBookInfo = entityManager.createQuery("update Book set taken = false " +
                "where bookId in (select bookId from Subscription where username =: username)");
        queryForUpdatingBookInfo.setParameter("username", username);
        queryForUpdatingBookInfo.executeUpdate();

        Query queryForDeletingUserSubscriptions = entityManager.createQuery("delete from Subscription where username =: username");
        queryForDeletingUserSubscriptions.setParameter("username", username);
        queryForDeletingUserSubscriptions.executeUpdate();
    }

    @Override
    @Transactional
    public boolean makeUserLibrarian(String username) {
        Query queryForMakingUserLibrarian = entityManager.createQuery("update Authority set authority = 'ROLE_LIBRARIAN'" +
                "where username =: username");
        queryForMakingUserLibrarian.setParameter("username", username);
        queryForMakingUserLibrarian.executeUpdate();

        return true;
    }

    @Override
    @Transactional
    public boolean depriveLibrarianPrivileges(String username) {
        Query queryForMakingUserLibrarian = entityManager.createQuery("update Authority set authority = 'ROLE_USER'" +
                "where username =: username");
        queryForMakingUserLibrarian.setParameter("username", username);
        queryForMakingUserLibrarian.executeUpdate();

        return true;
    }

    @Override
    @Transactional
    public void updatePenaltyInfo(String username, int updateSum) {
        Query queryForUpdatingPenaltyInfo = entityManager.createQuery("update UserDetails " +
                "set userPenalty =: updateSum where username =: username");
        queryForUpdatingPenaltyInfo.setParameter("updateSum", updateSum);
        queryForUpdatingPenaltyInfo.setParameter("username", username);

        queryForUpdatingPenaltyInfo.executeUpdate();
    }

    @Override
    @Transactional
    public boolean registerUser(User user) throws SQLIntegrityConstraintViolationException {
        Authority newUserAuthority = new Authority(user.getUsername(), "ROLE_USER");

        user.setPassword(getEncodedPassword(user.getPassword()));

        entityManager.persist(user);

        entityManager.persist(newUserAuthority);

        return true;
    }

    private String getEncodedPassword(String rawPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

        return "{bcrypt}" + encoder.encode(rawPassword);
    }
}
