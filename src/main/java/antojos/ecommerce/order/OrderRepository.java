package antojos.ecommerce.shopping;

import java.util.List;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import antojos.ecommerce.user.User;


public interface ShoppingRepository extends JpaRepository<Shopping,Long>{
  List<Shopping> findByDateBetween(Date fromDate, Date toDate);

  List<Shopping> findByUser(User user);

  @Query("SELECT s FROM Shopping s WHERE s.user = :user AND s.date BETWEEN :fromDate AND :toDate")
  List<Shopping> findByUserAndDateBetween(
    @Param("user") User user,
    @Param("fromDate") Date fromDate,
    @Param("toDate") Date toDate
  );
}
