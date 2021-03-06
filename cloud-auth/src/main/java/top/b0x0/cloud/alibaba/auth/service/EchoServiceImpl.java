package top.b0x0.cloud.alibaba.auth.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.b0x0.cloud.alibaba.api.IEchoService;
import top.b0x0.cloud.alibaba.api.IUserService;
import top.b0x0.cloud.alibaba.common.vo.res.UserRes;

/**
 * @author ManJiis
 * @since 2021-07-16
 * @since 1.8
 */
@DubboService(version = "${service.version}", group = "b0x0-cloud-auth")
@Component
public class EchoServiceImpl implements IEchoService {
    private static final Logger log = LoggerFactory.getLogger(EchoServiceImpl.class);

    @DubboReference(version = "${service.version}")
    private IUserService userService;

    @Override
    @SentinelResource(value = "auth#EchoServiceImpl#userThenAuthSayHello", fallback = "userThenAuthSayHelloFallback")
    public String userThenAuthSayHello(String param) {
        UserRes userRes = userService.findUser();
//        int i = 1 / 0;
        return JSON.toJSONString(userRes.toString());
    }

    public String userThenAuthSayHelloFallback(String param, Throwable ex) {
        return JSON.toJSONString(ex.getMessage());
    }

    /**
     * 原函数
     *
     * @param param /
     * @return /
     */
    @Override
    @SentinelResource(value = "auth#EchoServiceImpl#sayHello", fallback = "sayHelloFallback")
    public String sayHello(String param) {
        System.out.println("auth#EchoServiceImpl#sayHello： " + param);
        if ("error".equals(param)) {
            throw new RpcException("error oops...");
        }
        return param;
    }

    /**
     * Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
     *
     * @param s /
     * @return /
     */
    public String sayHelloFallback(String s) {
        return String.format("auth sayHelloFallback() --> sayHello %s", s);
    }

    public String sayHelloFallback(String s, Throwable ex) {
        // Do some log here.
        ex.printStackTrace();
        return "auth sayHelloFallback() Oops, error occurred at " + s;
    }

    /**
     * Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
     *
     * @param s  /
     * @param ex /
     * @return /
     */
    public String exceptionHandler(String s, BlockException ex) {
        // Do some log here.
        ex.printStackTrace();
        return "auth exceptionHandler() Oops, error occurred at " + s;
    }

    public String defaultFallback() {
        log.info("Go to default fallback");
        return "default_fallback";
    }

    @SentinelResource(value = "auth#EchoServiceImpl#bonjour", defaultFallback = "bonjourFallback")
    @Override
    public String bonjour(String name) {
        return "Bonjour, " + name;
    }

    public String bonjourFallback(Throwable t) {
        if (BlockException.isBlockException(t)) {
            return "auth bonjourFallback() Blocked by Sentinel: " + t.getClass().getSimpleName();
        }
        return "auth bonjourFallback() Oops, failed: " + t.getClass().getCanonicalName();
    }

}
