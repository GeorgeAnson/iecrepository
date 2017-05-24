package com.osms.utils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ExportUtil {
	
	private Configuration configuration=null;
	private Map<String, Template> teMap=null;
	
	public File exportToWordOrExcel(Map<?, ?> dataMap, String type, String valueName, String realPath, String fType)
	{
		configuration=new Configuration();
		configuration.setDefaultEncoding("utf-8");
		try {
			configuration.setDirectoryForTemplateLoading(new File(realPath));
			teMap=new HashMap<String, Template>();
			teMap.put(type, configuration.getTemplate(valueName));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String name=Utils.createRandomName()+fType;
		File file=new File(name);
		Template template=teMap.get(type);
		
		Writer writer;
		try {
			writer = new OutputStreamWriter(new FileOutputStream(file),"utf-8");
			template.process(dataMap, writer);
			writer.close();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}
}
