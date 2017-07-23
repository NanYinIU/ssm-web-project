package test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.junit.Test;

/**
 * Created by NanYin on 2017-07-11 下午2:27.
 * 包名： test
 * 类描述：
 */
public class hashTest {
    @Test
    public void md5Test(){
        String pass = "12345";
        String salt = "salt";
        String md5 = new Md5Hash(pass,"salt",1024).toString();
        String md5s = new SimpleHash("MD5",pass,salt,1024).toString();
        System.out.println(md5);
        System.out.println(md5s);
    }

    @Test
    public void subjectTest(){
        Session session = SecurityUtils.getSubject().getSession();
        session.getHost();
        SecurityUtils.getSubject().getSession().getHost();
    }
}
