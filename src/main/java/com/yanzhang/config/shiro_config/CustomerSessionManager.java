package com.yanzhang.config.shiro_config;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * 重写自定义的SessionManager
 */
public class CustomerSessionManager extends DefaultWebSessionManager {

    /**
     * 将用户的sessionId以请求头的方式， token:y7fehfea2356677bvcx
     * 重写该方法的原因在于，父类中是从Cookie中取得SessionId, 但是有自己的规则，
     * @param request
     * @param response
     * @return
     */
    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        // HttpServletRequest req = (HttpServletRequest)request;
        // 从请求头中获取SessionId
        String sessionId = WebUtils.toHttp(request).getHeader("token");

        if(null != sessionId){
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, ShiroHttpServletRequest.URL_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
            //automatically mark it valid here.  If it is invalid, the
            //onUnknownSession method below will be invoked and we'll remove the attribute at that time.
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);

            request.setAttribute(ShiroHttpServletRequest.SESSION_ID_URL_REWRITING_ENABLED, isSessionIdUrlRewritingEnabled());
            return sessionId;

        }else { //没有按照自定义的规则
            return super.getSessionId(request, response); //直接调用父类的方法
        }
    }
}
