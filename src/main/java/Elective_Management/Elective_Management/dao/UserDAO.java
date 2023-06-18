package Elective_Management.Elective_Management.dao;

import Elective_Management.Elective_Management.Entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}
