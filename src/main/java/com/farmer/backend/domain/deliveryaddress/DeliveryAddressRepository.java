package com.farmer.backend.domain.deliveryaddress;

import com.farmer.backend.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long>, DeliveryAddressQueryRepository {

    DeliveryAddress findByMember(Member findMember);
}
