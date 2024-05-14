package com.project.hometechhub.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.hometechhub.entities.Payment;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long>{

}
