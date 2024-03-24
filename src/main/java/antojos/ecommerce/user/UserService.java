package antojos.ecommerce.user;


// import org.hibernate.mapping.List;
import java.util.*;

import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository){
    this.userRepository = userRepository;
  }

  public List<User> getAllUsersExceptAdmin(String dniAdmin){
    List<User> userList = userRepository.findAll();
    for (Iterator<User> iterator = userList.iterator(); iterator.hasNext();){
      User user = iterator.next();
      if(user.getDni().equals(dniAdmin)){
        iterator.remove();
        break;
      }
    }
    return userList;
  }

  public User getUserByDni(String dni){
    return userRepository.findById(dni).orElse(null);
  }

  public User findByDniAndUserPass(String dni, String pass){ return  userRepository.findByDniAndUserPass(dni, pass);}
  public void addUser(User user){
    userRepository.save(user);
  }

  public List<User> findByDniOrName(String dniOrName, String dniAdmin){
    Set<User> userSet = new HashSet<>();

    if (dniOrName.isBlank()){
      List<User> userList;
      userList = getAllUsersExceptAdmin(dniAdmin);
      return userList;

    }else {
      List<User> usersByName = userRepository.findByNameContainingIgnoreCase(dniOrName);
      List<User> usersByLastName = userRepository.findByLastNameContainingIgnoreCase(dniOrName);
      List<User> usersByDni = userRepository.findByDniContainingIgnoreCase(dniOrName);

      userSet.addAll(usersByName);
      userSet.addAll(usersByDni);
      userSet.addAll(usersByLastName);

      return new ArrayList<>(userSet);

    }
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