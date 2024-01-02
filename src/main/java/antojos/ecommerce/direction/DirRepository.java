package antojos.ecommerce.direction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DirRepository extends JpaRepository<Dir, Dir.DirId>{
  
}
