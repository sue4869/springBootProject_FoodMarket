package com.psc.sample.oauthlogin.service;

import com.psc.sample.oauthlogin.domain.Address;
import com.psc.sample.oauthlogin.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public void insertAddress(Address address){

        addressRepository.save(address);

    }

    public List<Address> findByAddress(String bigAddress, String detailAddress){

        return addressRepository.findAddressByBigAddressAndDetailAddress(bigAddress, detailAddress);

    }

    public Long userCount() {

        Long num = addressRepository.count();

        System.out.println("총 user 수 : " + num );
        return num;

    }

    public List<Address> findAllByAddress(String bigAddress, String detailAddress) {

        return addressRepository.findAllByBigAddressAndAndDetailAddress(bigAddress, detailAddress);

    }
}
