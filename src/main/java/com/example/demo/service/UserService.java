package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

/**
 * ユーザーサービス
 */
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

		// 単一のロールを取得し、認証情報に付与
		String role = user.getRole(); // e.g., "ROLE_ADMIN" や "ROLE_USER"
		return new org.springframework.security.core.userdetails.User(
				user.getUserName(),
				user.getPassword(),
				List.of(new SimpleGrantedAuthority(role)) // 1つのロールのみ付与
		);
	}

	/**
	 * ユーザー名で検索
	 * @param userName ユーザー名
	 * @return ユーザー情報
	 */
	public User findByUsername(String userName) {
		return userRepository.findByUserName(userName);
	}

	/**
	 * ユーザー名でユーザーID検索
	 * @param userName ユーザー名
	 * @return ユーザーID
	 */
	public Integer findUserIdByUsername(String userName) {
		return userRepository.findByUserName(userName).getUserId();
	}

	/**
	 * 全ユーザーの検索
	 * @return ユーザーリスト
	 */
	public List<User> findAll() {
		return userRepository.findAll();
	}

	/**
	 * ユーザーIDでユーザー検索
	 * @param userId ユーザーID
	 * @return ユーザー情報
	 */
	public User findByUserId(Integer userId) {
		return userRepository.findByUserId(userId);
	}

	/**
	 * ユーザー情報の保存
	 * @param userDto 入力情報
	 */
	@Transactional
	public void save(User user) {
		userRepository.save(user);
	}

	/**
	 * ユーザー情報の上書き
	 * @param user ユーザー情報
	 */
	public void updateUser(User user) {
		User existingUser = userRepository.findByUserId(user.getUserId());

		// ユーザー名の更新
		existingUser.setUserName(user.getUserName());

		// パスワードが入力されている場合のみ更新
		if (user.getPassword() != null && !user.getPassword().isEmpty()) {
			existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
		}

		existingUser.setRole(user.getRole());

		// データベースに保存
		userRepository.save(existingUser);
	}

	public void deleteUser(User user) {
		userRepository.delete(user);
	}

}