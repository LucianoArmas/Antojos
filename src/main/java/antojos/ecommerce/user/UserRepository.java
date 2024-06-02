package antojos.ecommerce.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

  User findByDniAndUserPass (String dni, String pass);


  List<User> findByNameContainingIgnoreCase(String name);

  List<User> findByDniContainingIgnoreCase(String dni);

  List<User> findByLastNameContainingIgnoreCase(String lastName);

}
