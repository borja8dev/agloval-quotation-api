package com.agloval.infrastructure.output.persistence;

import com.agloval.domain.entity.Quotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuotationJpaRepository extends JpaRepository<Quotation, Long> {

    List<Quotation> findByUserId(Long userId);
}
