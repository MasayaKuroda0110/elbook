package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.entity.UserDto;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new UserPricipal(user);
	}

	public User findByUsername(String userName) {
		return userRepository.findByUserName(userName);
	}
	
	public Integer findUserIdByUsername(String userName) {
		return userRepository.findByUserName(userName).getUserId();
	}

	@Transactional
	public void save(UserDto userDto) {
		User user = new User();
		user.setUserName(userDto.getUserName());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));

		userRepository.save(user);
	}
	public void updateUser(User user) {
	    User existingUser = userRepository.findByUserId(user.getUserId());

	    // ユーザー名の更新
	    existingUser.setUserName(user.getUserName());

	    // パスワードが入力されている場合のみ更新
	    if (user.getPassword() != null && !user.getPassword().isEmpty()) {
	        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
	    }

	    // データベースに保存
	    userRepository.save(existingUser);
	}
}