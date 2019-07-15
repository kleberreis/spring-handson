package br.com.eng.handson2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.eng.handson2.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer>{

	Order findOneByTid(String tid);
	
	@Query("from Order o WHERE o.tid=:tid")
	Order minhaBuscaPorTid(@Param("tid") String tid);
}
