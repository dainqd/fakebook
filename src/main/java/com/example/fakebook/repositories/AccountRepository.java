package com.example.fakebook.repositories;

import com.example.fakebook.entities.Accounts;
import com.example.fakebook.entities.Friendships;
import com.example.fakebook.utils.Enums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Accounts, Long> {
    Page<Accounts> findAll(Pageable pageable);

    Page<Accounts> findAllByStatus(Enums.AccountStatus status, Pageable pageable);

    Optional<Accounts> findById(Long id);

    Optional<Accounts> findByIdAndStatus(Long id, Enums.AccountStatus status);

    Optional<Accounts> findByUsername(String username);

    Optional<Accounts> findByUsernameAndStatus(String username, Enums.AccountStatus status);

    Optional<Accounts> findByEmail(String email);

    Optional<Accounts> findByEmailAndStatus(String email, Enums.AccountStatus status);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    List<Accounts> findByIdNotIn(List<Long> listId);

    @Query("SELECT u FROM Accounts u WHERE u.id != :userId AND u.id NOT IN (SELECT f.receiver.id FROM Friendships f WHERE f.sender.id = :userId) AND u.id NOT IN (SELECT f.sender.id FROM Friendships f WHERE f.receiver.id = :userId) AND u.status = 'ACTIVE'")
    List<Accounts> findNonFriendActiveUsers(@Param("userId") Long userId);

    @Query("SELECT u FROM Accounts u JOIN Friendships f ON u.id = f.receiver.id OR u.id = f.sender.id WHERE (f.receiver.id = :userId OR f.sender.id = :userId) AND f.status = 'APPROVED'")
    List<Accounts> findApprovedFriends(@Param("userId") Long userId);

    @Query("SELECT u FROM Accounts u JOIN Friendships f ON u.id = f.receiver.id OR u.id = f.sender.id WHERE (f.receiver.id = :userId OR f.sender.id = :userId) AND f.status = 'PENDING'")
    List<Accounts> findPendingFriends(@Param("userId") Long userId);

    @Query("SELECT u FROM Accounts u JOIN Friendships f ON u.id = f.sender.id WHERE f.receiver.id = :userId AND f.status = 'PENDING'")
    List<Accounts> findUsersWithPendingFriendship(@Param("userId") Long userId);

}
