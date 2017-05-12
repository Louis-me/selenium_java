# ˵��

selenium3+java �Զ�������

# ����
* maven ����
* testng ���ò�����
* yamlά������
* PageObject ģʽ


# �÷�

**��ʼ��mavnen**

```
.....
            </plugin>
            <!--��Ӳ�� ����testNg.xml-->
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

**����PageObject**

�Ե�¼Ϊ����,���е�page���������ֹ��ɣ�

* ���캯��
* ��������
* ����

```
public class LoginPage {
    YamlRead yamlRead;
    OperateElement operateElement;
    protected WebDriver driver;

    /***
     * Ĭ�Ϲ��캯��
     * @param driver
     * @param path yaml���ò���
     */
    public LoginPage(WebDriver driver, String path) {
        this.driver = driver;
        yamlRead = new YamlRead(path);
        operateElement= new OperateElement(this.driver);
    }

    /***
     * ���Բ���
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
     * ����
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

**���õ�¼��yaml**

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


**��¼����**

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
        Assert.assertTrue(loginPage.checkpoint(), "���㲻ͨ��");
    }
}
```

**����testng**

```
<suite name="TesterHome">
    <parameter name="appURL" value="https://testerhome.com/account/sign_in"/>
    <parameter name="browserType" value="chrome"/>
    <parameter name="driverPath" value="C:\Program Files (x86)\Google\Chrome\Application\"/>
    <listeners>
        <listener class-name="util.ExtentTestNGIReporterListener"/> <!--���ñ��������-->
    </listeners>
    <test name="��¼">
        <classes>
            <class name="test.LoginPageTest"/>
        </classes>
    </test>
    <test name="����ҳ��">
        <classes>
            <class name="test.MyinfoPageTest"/>
        </classes>
    </test>
</suite>

```


**���Խ��**

![](result2.PNG)

# ����

�鿴�ҵ�[������־](chanelog.md)

## �����汾

* ��־����
* ���豸
* docker gird




