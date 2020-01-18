package com.nanyin.config.util;

import org.slf4j.MDC;

import java.util.Locale;
/**
 * 使用mdc构建日志，使用ThreadLocal存储国际化信息，防止出现在url上
 * @Author nanyin
 * @Date 13:38 2019-08-20
 **/
public class MDCUtil {

    private final static ThreadLocal<String> TL_USER = new ThreadLocal<String>(){
        @Override
        protected String initialValue() {
            return "000000-000-0000000000";
        }
    };

    private final static ThreadLocal<Locale> TL_LOCALE = new ThreadLocal<Locale>() {
        @Override
        protected Locale initialValue() {
            // 语言的默认值
            return Locale.SIMPLIFIED_CHINESE;
        };
    };

    private final static ThreadLocal<String> TL_REQUEST_ID = new ThreadLocal<>();

    public static final String KEY_LANG = "lang";

    public static final String KEY_USER = "username";

    public static final String KEY_REQUEST_ID = "requestId";

    private static final String NULL = "null";

    public static void setUser(String userid) {
        TL_USER.set(userid);
        MDC.put(KEY_USER, userid);
    }

    public static String getUser() {
        return TL_USER.get();
    }

    public static void setLocale(String locale) {
        setLocale(new Locale(locale));
    }

    public static void setLocale(Locale locale) {
        TL_LOCALE.set(locale);
    }

    public static Locale getLocale() {
        return TL_LOCALE.get();
    }

    public static void setRequestId(String requestId) {
        MDC.put(KEY_REQUEST_ID, requestId);
        TL_REQUEST_ID.set(requestId);
    }

    public static String getRequestId() {
        return TL_REQUEST_ID.get();
    }

    public static void clearAllUserInfo() {
        TL_USER.remove();
        TL_LOCALE.remove();
        TL_REQUEST_ID.remove();
        MDC.remove(KEY_USER);
        MDC.remove(KEY_REQUEST_ID);
    }

    public static Locale getLocale(String locale){
        Tools.check(locale.split("_").length > 1, "check_error", "resolveLocale");
        // 在MDC中存放副本
        return new Locale(locale.split("_")[0], locale.split("_")[1]);
    }


}
