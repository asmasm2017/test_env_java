/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eluon.rbt.testenv;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.simple.JSONObject;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author USER
 */

public class MainClass
{
      
    public void prettyPrintJson(JSONObject jsonObject)
    {
        Gson gson2=new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        String testJson=gson2.toJson(jsonObject);        
        System.out.println(testJson);
    }
    public String regexReplace(String text)
    {
         Map<String, String> envMap = System.getenv();            
            String pattern = "\\$\\{\\$([A-Za-z0-9-@#^*_]+)\\}";
            Pattern expr = Pattern.compile(pattern);
            Matcher matcher = expr.matcher(text);
            while (matcher.find()) 
            {
                String envValue = envMap.get(matcher.group(1).toUpperCase());
                if (envValue == null) 
                {
                    envValue = "";
                } else
                {
                    envValue = envValue.replace("\\", "\\\\");
                }
                Pattern subexpr = Pattern.compile(Pattern.quote(matcher.group(0)));
                text = subexpr.matcher(text).replaceAll(envValue);
          
            }
            return text;
    }
    
    public  String getFileContent(   FileInputStream fis,   String          encoding ) throws IOException
    {
      try( BufferedReader br =
              new BufferedReader( new InputStreamReader(fis, encoding )))
      {
         StringBuilder sb = new StringBuilder();
         String line;
         while(( line = br.readLine()) != null ) {
            sb.append( line );
            sb.append( '\n' );
         }
         return sb.toString();
      }
   }
    public JSONObject resolveConfig() throws FileNotFoundException, IOException 
    {
        
        String fileString=this.getFileContent( new FileInputStream("D:/customer.yaml"), "UTF-8");
        //System.out.println("fileString:"+fileString);
        String parsedFileString=this.regexReplace(fileString);
        Yaml yaml=new Yaml();
        Map<String, Object> obj;
        obj=yaml.load(parsedFileString);
        JSONObject jsonObject=new JSONObject(obj);
        return jsonObject;
    }
    public static void main(String[] args)
    {
        MainClass mainClass=new MainClass();
        JSONObject jsonObject;
        try
        {
            jsonObject=mainClass.resolveConfig();
            mainClass.prettyPrintJson(jsonObject);
        }
        catch(Exception E)
        {
            System.out.println("exception"+E.toString());
        }
    }
}
