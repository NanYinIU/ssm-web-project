package test;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * Created by NanYin on 2017-07-15 下午4:40.
 * 包名： test
 * 类描述：
 */
public class sessionLisener implements SessionListener {
    public void onStart(Session session) {
        System.out.println("回话创建："+session.getId());
    }

    public void onStop(Session session) {
        System.out.println("回话停止");
    }

    public void onExpiration(Session session) {

    }
}
