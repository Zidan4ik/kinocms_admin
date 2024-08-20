package com.example.kinocms_admin.repository;

import com.example.kinocms_admin.entity.User;
import com.example.kinocms_admin.model.CityCountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    List<User> getAllByIsSelected(boolean isSelected);
    @Query(value = "SELECT city, COUNT(city) FROM users GROUP BY city", nativeQuery = true)
    List<Object[]> findAllCitiesWithCountNative();

    List<User> getAllByIsMan(boolean isMan);
}
