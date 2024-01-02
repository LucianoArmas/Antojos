package antojos.ecommerce.direction;

import antojos.ecommerce.user.User;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity(name = "Direction")
public class Dir {

  @Embeddable
  public static class  DirId implements Serializable{
    private String street;
    private int number;
    private String postalCode;
    @Column(name = "user_dni")
    private String userDni;


    public DirId() {
    }

    public DirId(String street, int number, String postalCode, String userDni) {
      this.street = street;
      this.number = number;
      this.postalCode = postalCode;
      this.userDni = userDni;
    }



    public String getStreet() {
      return street;
    }
    public void setStreet(String street) {
      this.street = street;
    }
    public int getNumber() {
      return number;
    }
    public void setNumber(int number) {
      this.number = number;
    }
    public String getPostalCode() {
      return postalCode;
    }
    public void setPostalCode(String postalCode) {
      this.postalCode = postalCode;
    }
    public String getUserDni() {
      return userDni;
    }
    public void setUserDni(String userDni) {
      this.userDni = userDni;
    }
  }

  @EmbeddedId
  private DirId id;

  @ManyToOne
  @MapsId("userDni")
  @JoinColumn(name = "user_dni")
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
