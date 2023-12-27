package antojos.ecommerce.direction;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class DirId implements Serializable {
  private String street;
  private int number;
  private String postalCode;
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

