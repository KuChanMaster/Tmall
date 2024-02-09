package com.example.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.common.Constants;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.User;
import com.example.service.AdminService;
import com.example.service.BusinessService;
import com.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
/**
 * Token工具类
 */
@Component
public class TokenUtils {
    private static final Logger log = LoggerFactory.getLogger(TokenUtils.class);
    private static AdminService staticAdminService;
    //静态的服务对象，可以在类的任何静态方法中使用

    private static BusinessService staticBusinessService;

    private static UserService staticUserService;
    //静态的服务对象，可以在类的任何静态方法中使用
    @Resource
    AdminService adminService;

    @Resource
    BusinessService businessService;

    @Resource
    UserService userService;

/**
 * @PostConstruct: 这个注解的作用是在依赖注入完成后，初始化被注解的方法。
 * setUserService()方法的具体作用是将
 * 非静态的adminService和businessService对象赋值给
 * 静态的staticAdminService和staticBusiness对象。
 * 这样，即使在静态的上下文
 * 也可以使用adminService和businessService。
 * */
    @PostConstruct
    public void setUserService() {
        staticAdminService = adminService;
        staticBusinessService = businessService;
        staticUserService=userService;
    }
    /**
     * 生成token
     */
    public static String createToken(String data, String sign) {
        return JWT.create().withAudience(data) // 将 userId-role 保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // 2小时后token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥
    }
    /**
     * 获取当前登录的用户信息
     */
    public static Account getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader(Constants.TOKEN);
            if (ObjectUtil.isNotEmpty(token)) {
                String userRole = JWT.decode(token).getAudience().get(0);
                String userId = userRole.split("-")[0];  // 获取用户id
                String role = userRole.split("-")[1];    // 获取角色
                if (RoleEnum.ADMIN.name().equals(role)) {
                    return staticAdminService.selectById(Integer.valueOf(userId));
                }
                if(RoleEnum.BUSINESS.name().equals(role)){
                    return staticBusinessService.selectById(Integer.valueOf(userId));
                }
                if(RoleEnum.USER.name().equals(role)){
                    return staticUserService.selectById(Integer.valueOf(userId));
                }
            }
        } catch (Exception e) {
            log.error("获取当前用户信息出错", e);
        }
        return new Account();  // 返回空的账号对象
    }
}

