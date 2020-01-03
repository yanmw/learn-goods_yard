package com.ymw.admin.controller.sys;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ymw.admin.util.PasswordUtils;
import com.ymw.admin.vo.LoginBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.ymw.admin.model.sys.SysUser;
import com.ymw.admin.security.JwtAuthenticatioToken;
import com.ymw.admin.sevice.sys.SysUserService;
import com.ymw.admin.util.SecurityUtils;
import com.ymw.common.utils.IOUtils;
import com.ymw.core.http.HttpResult;

/**
 * 登录控制器
 * @author Louis
 * @date Oct 29, 2018
 */
@RestController
@Api(tags = "登录管理")
public class SysLoginController {

	@Autowired
	private Producer producer;
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("captcha.jpg")
	@ApiOperation(value = "获取图片验证码")
	public void captcha(HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");

		// 生成文字验证码
		String text = producer.createText();
		// 生成图片验证码
		BufferedImage image = producer.createImage(text);
		// 保存到验证码到 session
		request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, text);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);	
		IOUtils.closeQuietly(out);
	}

	/**
	 * 其实 Spring Security 的登录认证过程只需 调用 AuthenticationManager 的 authenticate(Authentication authentication) 方法，
	 * 最终返回认证成功的 Authentication 实现类并存储到SpringContexHolder 上下文即可，
	 * 这样后面授权的时候就可以从 SpringContexHolder 中获取登录认证信息，并根据其中的用户信息和权限信息决定是否进行授权。
	 *
	 * 登录接口
	 */
	@PostMapping(value = "/qr/login")
	@ApiOperation(value = "登录")
	public HttpResult login(@RequestBody LoginBean loginBean, HttpServletRequest request) throws IOException {
		String username = loginBean.getAccount();
		String password = loginBean.getPassword();
		String captcha = loginBean.getCaptcha();

		// 从session中获取之前保存的验证码跟前台传来的验证码进行匹配
//		Object kaptcha = request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
//		if(kaptcha == null){
//			return HttpResult.error("验证码已失效");
//		}
//		if(!captcha.equals(kaptcha)){
//			return HttpResult.error("验证码不正确");
//		}
		
		// 用户信息
		SysUser user = sysUserService.findByUsername(username);

		// 账号不存在、密码错误
		if (user == null) {
			return HttpResult.error("账号不存在");
		}
		
		if (!PasswordUtils.matches(user.getSalt(), password, user.getPassword())) {
			return HttpResult.error("密码不正确");
		}

		// 账号锁定
		if (user.getStatus() == 0) {
			return HttpResult.error("账号已被锁定,请联系管理员");
		}

		// 系统登录认证
		JwtAuthenticatioToken token = SecurityUtils.login(request, username, password, authenticationManager);
		// TODO 对接时，放开此处代码
		Map<String,Object> map = new HashMap<>();
		map.put("token",token);
		map.put("user",user);
		return HttpResult.ok(map);
//		return HttpResult.ok(token);
	}

}
