package top.b0x0.cloud.alibaba.user.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;
import top.b0x0.cloud.alibaba.api.IUserService;
import top.b0x0.cloud.alibaba.common.vo.res.UserRes;

/**
 * @author ManJiis
 */
@DubboService(version = "${service.version}")
@Component
@Slf4j
public class UserServiceImpl implements IUserService {


    @Override
    public UserRes findUser() {
        UserRes userRes = new UserRes().setUsername("张三").setNickName("大麻瓜").setIdCard("430114198708063921").setBankCard("87764791674196569151").setPhone("1778989887");
        System.out.println("userRes = " + userRes);
        return userRes;
    }

    @Override
    @SentinelResource(value = "user#UserServiceImpl#sayHello", fallback = "defaultFallback")
    public String sayHello(String name) {
        return "b0x0-cloud-user say hello -->" + name;
    }

    public String defaultFallback(String name) {
        log.info("UserServiceImpl Go to default fallback");
        return "UserServiceImpl default_fallback";
    }
}
