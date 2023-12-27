package antojos.ecommerce.user;


// import org.hibernate.mapping.List;
import java.util.List;
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

  public void addUser(User user){
    userRepository.save(user);
  }

  public void updateUser(User user){
    //AGREGAR MODIFICACIONES DE LOS ATRIBUTOS
    userRepository.save(user);
  }

  public void deleteUser(String dni){
    //AGREGAR VALIDACION DE EXISTENCIA
    userRepository.deleteById(dni);
  }

}