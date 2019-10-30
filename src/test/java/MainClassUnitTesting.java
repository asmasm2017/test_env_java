
import com.eluon.rbt.testenv.MainClass;
import com.fasterxml.jackson.dataformat.yaml.snakeyaml.Yaml;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONObject;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author USER
 */
public class MainClassUnitTesting
{
   
    @Test
    public void givenYamlFile_thenConvertToJsonObject() throws FileNotFoundException, IOException
    {
        FileInputStream fis=new FileInputStream("D:/customer.yaml");
        assertNotNull(fis);
        MainClass mainClass=new MainClass();
        String fileString=mainClass.getFileContent( fis, "UTF-8");
        assertNotNull(fileString);
        String parsedFileString=mainClass.regexReplace(fileString);
        Yaml yaml=new Yaml();
        Map<String, Object> obj;
        obj=(Map<String, Object>) yaml.load(parsedFileString);
        JSONObject jsonObject=new JSONObject(obj);
        assertNotNull(jsonObject);
        assertTrue(jsonObject instanceof JSONObject);
    }
   
}
