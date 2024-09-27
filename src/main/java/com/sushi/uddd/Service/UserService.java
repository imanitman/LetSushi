package com.sushi.uddd.Service;

import org.springframework.stereotype.Service;

import com.sushi.uddd.Domain.User;
import com.sushi.uddd.Domain.Request.ReqRegisterDto;
import com.sushi.uddd.Repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User saveUserToDb(User user){
        return this.userRepository.save(user);
    }
    public User convertDtoToUser(ReqRegisterDto reqRegisterDto){
        User currentUser = new User();
        currentUser.setEmail(reqRegisterDto.getEmail());
        currentUser.setName(reqRegisterDto.getUsername());
        currentUser.setPhoneNumber(reqRegisterDto.getPhoneNumber());
        currentUser.setPassword(reqRegisterDto.getPassword());
        return currentUser;
    }
    public User fetchUserByEmail(String email){
        return this.userRepository.findByEmail(email);
    }
    public User fetchUserById(long id){
        return this.userRepository.findById(id).get();
    }
}
