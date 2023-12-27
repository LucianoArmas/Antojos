package antojos.ecommerce.direction;

import antojos.ecommerce.user.User;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class Dir {
  @EmbeddedId
  private DirId id;

  @ManyToOne
  @MapsId("userDni")
  private User user;



  public Dir(){}

  public Dir(DirId id, User user) {
    this.id = id;
    this.user = user;
  }



  public DirId getId() {
    return id;
  }

  public void setId(DirId id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }


  @Override
  public String toString() {
    return "Dir [id=" + id + ", user=" + user + "]";
  }

}
