package com.ymw.admin.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.ymw.admin.util.PasswordEncoder;

/**
 * 上面说到登录认证是通过调用 AuthenticationManager 的 authenticate(token) 方法实现的，
 * 而 AuthenticationManager 又是通过调用 AuthenticationProvider 的 authenticate(Authentication authentication) 来完成认证的，
 * 所以通过定制 AuthenticationProvider 也可以完成各种自定义的需求，我们这里只是简单的继承 DaoAuthenticationProvider覆写密码验证逻辑
 * 其他的的大家可以根据各自的需求按需定制。
 * 身份验证提供者
 * @author Louis
 * @date Nov 20, 2018
 */
public class JwtAuthenticationProvider extends DaoAuthenticationProvider {

    public JwtAuthenticationProvider(UserDetailsService userDetailsService) {
        setUserDetailsService(userDetailsService);
    }

	/**
	 * 额外的身份验证
	 * @param userDetails
	 * @param authentication
	 * @throws AuthenticationException
	 */
    @Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		if (authentication.getCredentials() == null) {
			logger.debug("Authentication failed: no credentials provided");
			throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}

		String presentedPassword = authentication.getCredentials().toString();
		String salt = ((JwtUserDetails) userDetails).getSalt();
		// 覆写密码验证逻辑
		if (!new PasswordEncoder(salt).matches(userDetails.getPassword(), presentedPassword)) {
			logger.debug("Authentication failed: password does not match stored value");
			throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}
	}

}