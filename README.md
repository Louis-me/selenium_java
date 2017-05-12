# 说明

selenium3+java 自动化测试

# 功能
* maven 管理
* testng 配置测试类
* yaml维护用例
* PageObject 模式


# 用法

**初始化mavnen**

```
.....
            </plugin>
            <!--添加插件 关联testNg.xml-->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>2.2</version>
                <configuration>
                    <testFailureIgnore>true</testFailureIgnore>
                    <suiteXmlFiles>
                        <file>res/testNG.xml</file>
                    </suiteXmlFiles>
                    <!--<workingDirectory>target/</workingDirectory>-->
                </configuration>
            </plugin>
        </plugins>
    </build>

    <dependencies>
        <dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
            <version>6.11</version>
        </dependency>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
            <version>3.4.0</version>
        </dependency>
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-chrome-driver</artifactId>
            <version>3.4.0</version>
        </dependency>
        <dependency>
            <groupId>com.esotericsoftware.yamlbeans</groupId>
            <artifactId>yamlbeans</artifactId>
            <version>1.11</version>
        </dependency>
    </dependencies>
</project>

```

**配置PageObject**

以登录为例子,所有的page都由三部分构成：

* 构造函数
* 操作方法
* 检查点

```
public class LoginPage {
    YamlRead yamlRead;
    OperateElement operateElement;
    protected WebDriver driver;

    /***
     * 默认构造函数
     * @param driver
     * @param path yaml配置参数
     */
    public LoginPage(WebDriver driver, String path) {
        this.driver = driver;
        yamlRead = new YamlRead(path);
        operateElement= new OperateElement(this.driver);
    }

    /***
     * 测试步骤
     * @throws YamlException
     * @throws FileNotFoundException
     */
    public void operate() throws YamlException, FileNotFoundException {
        List list = (List) yamlRead.getYmal().get("testcase");
//        System.out.println(list);
        for(Object item: list){
            TestCase testCase = new TestCase();
            testCase.setFind_type((String) ((Map)item).get("find_type"));
            testCase.setElement_info((String) ((Map)item).get("element_info"));
            testCase.setText((String) ((Map)item).get("text"));
            testCase.setOperate_type((String) ((Map)item).get("operate_type"));
            operateElement.operate(testCase);
        }
    }

    /***
     * 检查点
     * @return
     * @throws YamlException
     * @throws FileNotFoundException
     */
    public boolean checkpoint() throws YamlException, FileNotFoundException {
        List list = (List) yamlRead.getYmal().get("check");
//        System.out.println(list);
        for(Object item: list){
            CheckPoint checkPoint = new CheckPoint();
            checkPoint.setElement_info((String) ((Map)item).get("element_info"));
            checkPoint.setFind_type((String) ((Map)item).get("find_type"));
            if (!operateElement.checkElement(checkPoint)) {
                return false;
            }
        }
        return true;
    }
```

**配置登录的yaml**

```
testcase:
    - element_info: user_login
      find_type: id
      operate_type: send_keys
      text: lose
    - element_info: user[password]
      find_type: name
      operate_type: send_keys
      text: password
    - element_info: //*[@id="new_user"]/div[4]/input
      find_type: xpath
      operate_type: click
check:
    - element_info: /html/body/div[1]/nav/div/ul[1]/li/a/img
      find_type: xpath
    - element_info: /html/body/div[1]/nav/div/ul[2]/li[2]/a
      find_type: xpath

```


**登录测试**

```
public class LoginPageTest extends TestBaseSetup {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver=getDriver();
    }
    @Test
    public void testLogin() throws YamlException, FileNotFoundException {
        LoginPage loginPage = new LoginPage(this.driver, "/Login.yaml");
        loginPage.operate();
        Assert.assertTrue(loginPage.checkpoint(), "检查点不通过");
    }
}
```

**配置testng**

```
<suite name="TesterHome">
    <parameter name="appURL" value="https://testerhome.com/account/sign_in"/>
    <parameter name="browserType" value="chrome"/>
    <parameter name="driverPath" value="C:\Program Files (x86)\Google\Chrome\Application\"/>
    <listeners>
        <listener class-name="util.ExtentTestNGIReporterListener"/> <!--配置报告监听器-->
    </listeners>
    <test name="登录">
        <classes>
            <class name="test.LoginPageTest"/>
        </classes>
    </test>
    <test name="个人页面">
        <classes>
            <class name="test.MyinfoPageTest"/>
        </classes>
    </test>
</suite>

```


**测试结果**

![](result2.PNG)

# 其他

查看我的[更新日志](chanelog.md)

## 后续版本

* 日志管理
* 多设备
* docker gird




