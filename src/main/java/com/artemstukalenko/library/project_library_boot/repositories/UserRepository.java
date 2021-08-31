package com.artemstukalenko.library.project_library_boot.repositories;

import com.artemstukalenko.library.project_library_boot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,String> {

    @Modifying
    @Query("delete from Authority where username = :username")
    public void deleteAuthorityByUsername(@Param("username") String username);

    @Modifying
    @Query("delete from UserDetails where username = :username")
    public void deleteUserDetailsByUsername(@Param("username") String username);

    @Modifying
    @Query("update Book set taken = false " +
            "where bookId in (select bookId from Subscription where username = :username)")
    public void updateSubscriptionBooksInfo(@Param("username") String username);

    @Modifying
    @Query("delete from Subscription where username = :username")
    public void deleteUserSubscriptionsByUsername(@Param("username") String username);

    @Modifying
    @Query("update User set enabled = 0 where username = :username")
    public void blockUser(@Param("username") String username);

    @Modifying
    @Query("update User set enabled = 1 where username = :username")
    public void unblockUser(@Param("username") String username);

    @Query("select authority from Authority where username in " +
            "(select username from User where username = :username)")
    public String getUserRole(@Param("username") String username);

    @Modifying
    @Query("update Authority set authority = 'ROLE_LIBRARIAN'" +
            "where username = :username")
    public void makeUserLibrarian(@Param("username") String username);

    @Modifying
    @Query("update Authority set authority = 'ROLE_USER'" +
            "where username = :username")
    public void depriveLibrarianPrivileges(@Param("username") String username);

    @Modifying
    @Query("update UserDetails " +
            "set userPenalty = :updateSum where username = :username")
    public void updatePenaltyInfo(String username, int updateSum);
}
