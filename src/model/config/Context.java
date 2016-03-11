package model.config;/**
 * Created by zql on 15/6/26.
 */

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zql on 15/6/26.
 * @className Context
 * @classUse
 *
 *
 */
public abstract class Context
{
    public static ApplicationContext serverCtx = new ClassPathXmlApplicationContext("model/config/spring.xml");
}
