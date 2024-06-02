package antojos.ecommerce.direction;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class DirService {
//  private final DirRepository dirRepository;
//
//  public DirService(DirRepository dirRepository) {
//    this.dirRepository = dirRepository;
//  }
//
//
//  public List<Dir> getAllDirs(){
//    return dirRepository.findAll();
//  }
//
//  public Dir getDirById(Dir.DirId id){
//    return dirRepository.findById(id).orElse(null);
//  }

//  public void addDir(Dir dir){
    //VALIDAR SI EXISTE EL OBJETO
//    dirRepository.save(dir);
//  }

//  public void updateDir(Dir dir){
//    Dir.DirId id = dir.getId();
//    Optional<Dir> existingDir = dirRepository.findById(id);
//    if(existingDir.isPresent()){
//      Dir updatedDir = existingDir.get();
//      updatedDir.setId(id);
//      updatedDir.setUser(dir.getUser());
//
//      dirRepository.save(updatedDir);
//    }else{
      //MANEJAR EL CASO DONDE EL OBJ NO EXISTE
//    }
//  }

//  public void deleteDir(Dir.DirId id){
//    if(dirRepository.existsById(id)){
//      dirRepository.deleteById(id);
//    }else{
      //MANEJAR EL CASO DONDE EL OBJ NO EXISTE
//    }
//  }
}
