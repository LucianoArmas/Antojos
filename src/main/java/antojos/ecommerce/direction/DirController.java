package antojos.ecommerce.direction;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/directions")
public class DirController {
//  private final DirService dirService;

//  public DirController(DirService dirService) {
//    this.dirService = dirService;
//  }

  
//  @GetMapping("/list")
//  public String listDirs(Model model){
//    List<Dir> dirs = dirService.getAllDirs();
//    model.addAttribute("dirs", dirs);
//    return "dirs/list";
//  }
//
//
//  @GetMapping("/add")
//  public String addDirsForm(Model model){
//    model.addAttribute("dirs", new Dir());
//    return "dirs/add";
//  }
//  @PostMapping("/add")
//  public String addDirs(@ModelAttribute Dir dir){
//    dirService.addDir(dir);
//    return "redirect:/dirs/list";
//  }
//
//
//  @GetMapping("/edit/{id}")
//  public String editDirForm(@PathVariable Dir.DirId id, Model model){
//    Dir dir = dirService.getDirById(id);
//    model.addAttribute("dir", dir);
//    return "dirs/edit";
//  }
//  @PostMapping("/edit/{id}")
//  public String editDir(@PathVariable Dir.DirId id, @ModelAttribute Dir dir){
//    dirService.updateDir(dir);
//    return "redirect:/users/list";
//  }
//
//
//  @GetMapping("/delete/{id}")
//  public String deleteDir(@PathVariable Dir.DirId id){
//    dirService.deleteDir(id);
//    return "redirect:/users/list";
//  }

}
