package util;

import com.esotericsoftware.yamlbeans.YamlException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

public class YamlRead {

    String path = "";
    public YamlRead(String path) {
        this.path = path;
    }

    public Map getYmal() throws YamlException, FileNotFoundException {
        String path = getClass().getResource(this.path).getPath();
        com.esotericsoftware.yamlbeans.YamlReader reader = new com.esotericsoftware.yamlbeans.YamlReader(new FileReader(path));
        Object object = reader.read();
        return (Map)object;
    }
}
