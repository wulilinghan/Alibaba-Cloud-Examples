//package top.b0x0.cloud.alibaba.user.filter;
//
//import org.apache.dubbo.common.constants.CommonConstants;
//import org.apache.dubbo.common.extension.Activate;
//import org.apache.dubbo.rpc.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.StringUtils;
//
///**
// * 下游Filter
// * 接收消费者传递过来的参数
// *
// * @author ManJiis
// * @since 2021-07-18
// */
//@Activate(group = CommonConstants.PROVIDER)
//public class DownStreamFilter implements Filter {
//    private static final Logger log = LoggerFactory.getLogger(DownStreamFilter.class);
//
//    @Override
//    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
//        RpcContext rpcContext = RpcContext.getContext();
//        //上游如果手动设置了consumer的应用名进attachment，则取出来打印
//        //这次请求来自哪个ip
//        String clientIp = rpcContext.getRemoteHost();
//        String application = rpcContext.getAttachment("dubboApp");
//        String token = rpcContext.getAttachment("loginToken");
//        String from = clientIp;
//        if (!StringUtils.isEmpty(application)) {
//            from = application + "(" + clientIp + ")";
//        }
//        log.info("[Trace]From {}, {}.{}(), param:{}, token:[{}]",
//                from, invoker.getInterface(), invocation.getMethodName(), invocation.getArguments(), token);
//        return invoker.invoke(invocation);
//    }
//}