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

	@Transactional
	public void save(UserDto userDto) {
		User user = new User();
		user.setUserName(userDto.getUserName());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));

		userRepository.save(user);
	}
}