package antojos.ecommerce.user;


// import org.hibernate.mapping.List;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository){
    this.userRepository = userRepository;
  }

  public List<User> getAllUsers(){
    return userRepository.findAll();
  }

  public User getUserByDni(String dni){
    return userRepository.findById(dni).orElse(null);
  }

  public User findByDniAndUserPass(String dni, String pass){ return  userRepository.findByDniAndUserPass(dni, pass);}
  public void addUser(User user){
    userRepository.save(user);
  }

  public void updateUser(User newUser, String dni, boolean flag_editFromAdmin){
    User user = getUserByDni(dni);
    if(user != null){
      user.setDni(dni);
      user.setEmail(newUser.getEmail());
      user.setName(newUser.getName());
      user.setLastName(newUser.getLastName());
      user.setOrders(newUser.getOrders());

      if((newUser.getUserPass() == null) || (newUser.getUserPass().isBlank())){
        user.setUserPass(user.getUserPass());
      }else {user.setUserPass(newUser.getUserPass());}

      if (flag_editFromAdmin){
        user.setAccessLvl(newUser.getAccessLvl());
      }else{user.setAccessLvl("client");}

      userRepository.save(user);
    }
  }

  public void deleteUser(String dni){
    //AGREGAR VALIDACION DE EXISTENCIA
    userRepository.deleteById(dni);
  }

}