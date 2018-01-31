package com.fangdd.testcenter.testcase;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

//ContextConfiguration需要加载多个配置时以逗号分隔
//如 locations = { "classpath:config/applicationContext1.xml","classpath:config/applicationContext2.xml" }
@ContextConfiguration(locations = { "classpath:config/applicationContext.xml" })
public abstract class AbstractTest extends AbstractTestNGSpringContextTests {

}
