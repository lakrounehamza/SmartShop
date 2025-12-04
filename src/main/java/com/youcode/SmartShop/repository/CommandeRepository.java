package com.youcode.SmartShop.repository;

import com.youcode.SmartShop.dtos.response.CommandeResponseDto;
import com.youcode.SmartShop.entity.Commande;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public interface CommandeRepository extends JpaRepository<Commande,Long> {
    Page<Commande> findByClient_Id(Long id, Pageable pageable);
    @Query("select sum(c.total) from  Commande c where c.client.id =:id")
    Optional<BigDecimal> findCumuleByClient_Id(Long id);
    @Query("select min(c.date) from  Commande c where c.client.id =:id")
    Optional<LocalDate> findLastDateByClient_id(Long id);
    @Query("select max(c.date) from  Commande c where c.client.id =:id")
    Optional<LocalDate> findFirstDateByClient_id(Long id);
    @Query("select count(c.id) from  Commande c where c.client.id =:id")
    int findCountByClinet_id(long  id);
    @Query("select count(c.id) from  Commande c where c.statut=1 and c.client.id =:id")
    int findCountConfirmeByClinet_id(long  id);
    @Query("select count(c.id)  From  Commande c where c.statut =0 and c.client.id =:id")
    int getCountCommandePending(Long id);
    @Query("select c  from Commande c join OrderItem o  where o.product.id=:id")
    List<Commande> findByProduit_Id(Long  id);

}
