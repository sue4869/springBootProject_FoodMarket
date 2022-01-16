package com.psc.sample.oauthlogin.repository;

import com.psc.sample.oauthlogin.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Long> {

    List<Address> findAddressByBigAddressAndDetailAddress(String BigAddress, String DetailAddress);

    List<Address> findAllByBigAddressAndAndDetailAddress(String BigAddress, String DetailAddress);
}
