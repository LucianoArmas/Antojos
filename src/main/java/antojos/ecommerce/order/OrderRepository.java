package antojos.ecommerce.order;

import java.util.List;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import antojos.ecommerce.user.User;


public interface OrderRepository extends JpaRepository<Order,Long>{
  List<Order> findByDateBetween(Date fromDate, Date toDate);

  List<Order> findByUser(User user);

  @Query("SELECT s FROM Order s WHERE s.user = :user AND s.date BETWEEN :fromDate AND :toDate")
  List<Order> findByUserAndDateBetween(
    @Param("user") User user,
    @Param("fromDate") Date fromDate,
    @Param("toDate") Date toDate
  );

  Order findByUserAndState(User user, String state);
}
