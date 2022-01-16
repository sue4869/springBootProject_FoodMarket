package com.psc.sample.oauthlogin.service;

import com.psc.sample.oauthlogin.domain.Address;
import com.psc.sample.oauthlogin.domain.User;
import com.psc.sample.oauthlogin.repository.AddressRepository;
import com.psc.sample.oauthlogin.repository.BasketRepository;
import com.psc.sample.oauthlogin.repository.LikeRepository;
import com.psc.sample.oauthlogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService /*implements UserDetailsService*/ {

    private final UserRepository userRepository;

    private final AddressRepository addressRepository;

    private final LikeRepository likeRepository;

    private final BasketRepository basketRepository;

    private final HttpSession httpSession;

    // 기본적인 반환 타입은 UserDetails, UserDetails를 상속받은 UserInfo로 반환 타입 지정 (자동으로 다운 캐스팅됨)
//    @Override
//    public User loadUserByUsername(String phone) throws UsernameNotFoundException { // 시큐리티에서 지정한 서비스이기 때문에 이 메소드를 필수로 구현
//        return userRepository.findByPhoneNumber(phone)
//                .orElseThrow(() -> new UsernameNotFoundException((phone)));
//    }


    public String selectAllUser(String userId, String password){

        Optional<User> user = userRepository.findByUserId(userId);

        if(user.isPresent ()){
            if(user.get().getPassword().equals(password)){
                httpSession.setAttribute("user" , user.get());
//                return "main";

                return "main";
            }else{
                return "redirect:/loginForm02";
            }
        }else if(user.isEmpty()){
            return "redirect:/loginForm02";
        }

        return null;
    }

    public String selectAllUserForRefer(String userId, String password, String referrer , List<String> referrerList){

        Optional<User> user = userRepository.findByUserId(userId);

        if(user.isPresent()){
            if(user.get().getPassword().equals(password)){
                httpSession.setAttribute("user" , user.get());
//                return "main";

                if(referrer.equals("http://localhost/loginForm02")){
//                    return "event";
                    return "main";
                }
//                for(int i = 0; i < referrerList.size(); i++){
//                    referrerList.remove(i);
//                }
                return referrer;
            }else{
                return "loginForm02";
            }
        }else if(user.isEmpty()){
            return "loginForm02";
        }

        return null;
    }

    public List<User> selectAllUserForIdCheck(){

        List<User> users = userRepository.findAll();

        return users;

    }

    public void insertUser(User user){



        userRepository.save(user);

    }

    public User getUserWhoSnsLogin(){

        return userRepository.findBySnsLoginIsTrue().get();

    }

    public Optional<User> duplicateUserCheck(String phoneNumber){


        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public void deleteUser(User user){
        userRepository.delete(user);
    }

    public Optional<User> findUser(String name, String inputEmail) {


        return userRepository.findByNameAndEmail(name, inputEmail);

    }

    public Optional<User> findByUserId(String userId) {

        return userRepository.findByUserId(userId);
    }

    public Optional<User> findUserByNameAndPhoneNum(String phoneNum, String name) {

        return userRepository.findByPhoneNumberAndName(phoneNum , name);
    }

    public void changePw(String changedPw, String phone) {

        User user = userRepository.findByPhoneNumber(phone).get();

        user.setPassword(changedPw);

        userRepository.save(user);

    }

    public void changeAddress(String phone, String bigAddress, String detailAddress) {
        User user = userRepository.findByPhoneNumber(phone).get();

        user.getAddress().setBigAddress(bigAddress);
        user.getAddress().setDetailAddress(detailAddress);

        userRepository.save(user);
    }

    @Transactional
    public void deleteUserByPhone(String phone) {

        User user = userRepository.findByPhoneNumber(phone).get();
//        Address address = user.getAddress();

        // user 와 관련된 좋아요를 다 지운다.
        likeRepository.deleteAllByUserId(user.getId());

        // user 와 관련되 장바구니를 다 지운다.
        basketRepository.deleteAllByUserId(user.getId());
//        user.getAddress().setId(null);

//        userRepository.save(user);

//        System.out.println("user : " + user.getId());
        userRepository.deleteById(user.getId());
//
//        addressRepository.deleteById(address.getId());



    }

    public Optional<User> findByUserName(String username) {

        return userRepository.findByName(username);

    }
}
