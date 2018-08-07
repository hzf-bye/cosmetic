package base;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @description: 测试类基类
 * @author: hzf
 * @date: 2018/2/5 下午3:58
 */
//整合junit和spring，让junit在启动时候加载springIOC容器
@RunWith(value = SpringJUnit4ClassRunner.class)
//告诉junit spring的配置文件,需要用到的配置文件，如果是dao的话可以不用spring-service.xml
@ContextConfiguration(locations = { "classpath:spring/applicationContext-*.xml"})
public class BaseTest {
}
