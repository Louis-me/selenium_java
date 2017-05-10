import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.YamlRead;

public class test2 {

    public static void main(String[] args) throws FileNotFoundException, YamlException {
        YamlRead yamlRead = new YamlRead("/Login.yaml");
        List list = (List) yamlRead.getYmal().get("check");
        System.out.println(list);
        for(Object l: list){
            System.out.println(((Map)l).get("element_info"));
            System.out.println(((Map)l).get("find_type"));
        }

    }
}
