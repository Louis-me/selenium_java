# 说明

selenium3+java 自动化测试

# 功能
* maven 管理
* testng 配置测试类
* yaml维护用例
* PageObject 模式
* docker 多浏览器支持

# 用法

**初始化mavnen**

```
.....
            <<plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>2.20</version>
                <configuration>
                    <suiteXmlFiles>
                        <suiteXmlFile>res/testng.xml</suiteXmlFile>
                    </suiteXmlFiles>
                </configuration>
                <executions>
                <execution>  <!--集成测试-->
                    <id>failsafe-integration-tests</id>
                    <phase>integration-test</phase>
                    <goals>
                        <goal>integration-test</goal>
                    </goals>
                </execution>
            </executions>
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
    private boolean isOperate = true;
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
    public void operate() throws YamlException, FileNotFoundException, InterruptedException {
        List list = (List) yamlRead.getYmal().get("testcase");
//        System.out.println(list);
        for(Object item: list){
            TestCase testCase = new TestCase();
            testCase.setFind_type((String) ((Map)item).get("find_type"));
            testCase.setElement_info((String) ((Map)item).get("element_info"));
            testCase.setText((String) ((Map)item).get("text"));
            testCase.setOperate_type((String) ((Map)item).get("operate_type"));
            if (!operateElement.operate(testCase)) {
                isOperate = false;
                System.out.println("操作失败");
                break;
            }

        }
    }

    /***
     * 检查点
     * @return
     * @throws YamlException
     * @throws FileNotFoundException
     */
    public boolean checkpoint() throws YamlException, FileNotFoundException, InterruptedException {
        if (!isOperate) { // 如果操作步骤失败，检查点也就判断失败
            System.out.println("操作步骤失败了");
            return false;
        }
        List list = (List) yamlRead.getYmal().get("check");
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
<suite name="XXX" parallel="tests" thread-count="3"> <!-- 并行地执行test套件-->
    <listeners>
        <listener class-name="util.ExtentTestNGIReporterListener"/> <!-- 测试报告-->
        <listener class-name="util.TestMonitor"/><!-- 记录日志-->
    </listeners>
    <test name="chrome59登录">
        <parameter name="browserType" value="chrome" />
        <parameter name="browserVersion" value="59.0.3071.115" />
        <parameter name="remoteIP" value="192.168.99.100" />
        <parameter name="driverPath" value="C:\Program Files (x86)\Google\Chrome\Application\"/>
        <parameter name="appURL" value="https://XXX/#/login"/>
        <classes>
            <class name="test.LoginTest"/>
        </classes>
    </test>
    <test name="ff54">
        <parameter name="browserType" value="firefox" />
        <parameter name="browserVersion" value="54.0" />
        <parameter name="remoteIP" value="192.168.99.100" />
        <parameter name="driverPath" value="1" />
        <parameter name="appURL" value="https:/XXXX8443/#/login"/>
        <classes>
            <class name="test.LoginTest"/>
        </classes>
    </test>

    <test name="chrome59申请">
        <parameter name="browserType" value="chrome" />
        <parameter name="browserVersion" value="59.0.3071.115" />
        <parameter name="remoteIP" value="192.168.99.100" />
        <parameter name="driverPath" value="C:\Program Files (x86)\Google\Chrome\Application\"/>
        <parameter name="appURL" value="https:/XXX/#/login"/>
        <classes>
            <class name="test.ApplyContinueTest"/>
        </classes>
    </test>
</suite>

```

# 命令执行

``` mvn test```

![](img/result3.PNG)


**测试结果**

![](img/result2.PNG)

# 其他

查看我的[更新日志](chanelog.md)




