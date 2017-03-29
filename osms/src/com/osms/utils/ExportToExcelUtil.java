package com.osms.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.CollectionUtils;


public class ExportToExcelUtil<T> {

	private static int NUM=5000;
	
	private static String TITLE="";
	
	private static short DEFAULT_COLUMN_WIDTH=18;
	private static short TITLE_FONT_HEIGHT=15;
	private static int HIGHT_FOR_PIC=60;
	
	public void exportExcel(String[] headers, String[] columns, List<T> result, OutputStream out, HttpServletRequest request, String pattern)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException
	{
		File zip=new File(request.getServletContext().getRealPath("/")+"WEB-INF/model/"+getFileName()+".zip");
		int n=0;
		if(!CollectionUtils.isEmpty(result))
		{
			if(result.size()%NUM==0)
			{
				n=result.size()/NUM;
			}else
			{
				n=result.size()/NUM+1;
			}
		}else
		{
			n=1;
		}
		
		List<String> fileNames=new ArrayList<String>();
		
		for(int i=0;i<n;i++)
		{
			Collection<T> res=null;
			if(!CollectionUtils.isEmpty(result))
			{
				if(i==n-1)
				{
					if(result.size()%NUM==0)
					{
						res=result.subList(NUM*i, NUM*(i+1));
					}else
					{
						res=result.subList(NUM*i, NUM*i+result.size()%NUM);
					}
				}else
				{
					res=result.subList(NUM*i, NUM*(i+1));
				}
			}
			Workbook workbook=new HSSFWorkbook();
			HSSFSheet sheet=(HSSFSheet) workbook.createSheet(TITLE);
			sheet.setDefaultColumnWidth(DEFAULT_COLUMN_WIDTH);
			
			String file=request.getServletContext().getRealPath("/")+"WEB-INF/model/"+getFileName()+"-"+i+".xls";
			
			fileNames.add(file);
			
			FileOutputStream fos=new FileOutputStream(file);
			//生成样式表
			HSSFCellStyle style=(HSSFCellStyle) workbook.createCellStyle();
			//设置样式
			style.setFillForegroundColor(HSSFColor.GOLD.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			
			HSSFFont font=(HSSFFont) workbook.createFont();
			font.setColor(HSSFColor.VIOLET.index);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			style.setFont(font);
			
			style.setWrapText(true);
			
			HSSFPatriarch patriarch=sheet.createDrawingPatriarch();
			
			HSSFCellStyle titleStyle=(HSSFCellStyle) workbook.createCellStyle();
			
			titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
			titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			
			HSSFFont titleFont=(HSSFFont) workbook.createFont();
			
			titleFont.setFontHeightInPoints(TITLE_FONT_HEIGHT);
			titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			
			titleStyle.setFont(titleFont);
			
			sheet.addMergedRegion(new CellRangeAddress(0, (short)0, 0, (short)(headers.length-1)));
			HSSFRow rowHeader=sheet.createRow(0);
			HSSFCell cellHeader=rowHeader.createCell(0);
			HSSFRichTextString textHeader=new HSSFRichTextString(TITLE);
			cellHeader.setCellStyle(titleStyle);
			cellHeader.setCellValue(textHeader);
			
			HSSFRow row=sheet.createRow(1);
			for(int j=0;j<headers.length;j++)
			{
				HSSFCell cell=row.createCell(j);
				cell.setCellStyle(style);
				HSSFRichTextString text=new HSSFRichTextString(headers[j]);
				cell.setCellValue(text);
			}
			
			if(result!=null)
			{
				int index=2;
				for(T t:res)
				{
					row=sheet.createRow(index);
					index++;
					for(int j=0;j<columns.length;j++)
					{
						HSSFCell cell=row.createCell(j);
						String fileName=columns[j];
						String getMethodName="get"+fileName.substring(0, 1).toUpperCase()+fileName.substring(1);
						Class clazz=t.getClass();
						Method getMethod=clazz.getMethod(getMethodName, new Class[]{});
						Object value=getMethod.invoke(t, new Class[]{});
						String textValue=null;
						if(value==null)
						{
							textValue="";
						}else if(value instanceof Date)
						{
							Date date=(Date) value;
							SimpleDateFormat sdf=new SimpleDateFormat(pattern);
							textValue=sdf.format(date);
						}else if(value instanceof byte[])
						{
							row.setHeightInPoints(HIGHT_FOR_PIC);
							sheet.setColumnWidth(j, (short)(27.5*80));
							byte[] bsValue=(byte[]) value;
							HSSFClientAnchor anchor=new HSSFClientAnchor(0, 0, 1023, 255, (short)6, index, (short)6, index);
							anchor.setAnchorType(AnchorType.DONT_MOVE_DO_RESIZE);
							patriarch.createPicture(anchor, workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
							
						}else
						{
							textValue=value.toString();
						}
						
						if(textValue!=null)
						{
							Pattern p=Pattern.compile("^//d+(//.//d+)?$");
							Matcher matcher=p.matcher(textValue);
							if(matcher.matches())
							{
								cell.setCellValue(textValue);
							}else
							{
								HSSFRichTextString richTextString=new HSSFRichTextString(textValue);
								cell.setCellValue(richTextString);
							}
						}
					}
				}
			}
			workbook.write(fos);
			File srcFile[] =new File[fileNames.size()];
			for(int k=0,n1=fileNames.size();k<n1;k++)
			{
				srcFile[i]=new File(fileNames.get(k));
			}
			
			ZipFiles(srcFile, zip);
			FileInputStream fis=new FileInputStream(zip);
			byte[] buffer=new byte[4096];
			int read=0;
			while((read=fis.read(buffer))!=-1)
			{
				out.write(buffer, 0, read);
			}
			fis.close();
		}
	}

	/**
	 * zip files
	 * @param srcFile
	 * @param zip
	 */
	private void ZipFiles(File[] srcFile, File zip) {
		// TODO Auto-generated method stub
		byte[] buffer=new byte[1024];
		try {
			ZipOutputStream outputStream=new ZipOutputStream(new FileOutputStream(zip));
			for(int i=0;i<srcFile.length;i++)
			{
				FileInputStream fis=new FileInputStream(srcFile[i]);
				outputStream.putNextEntry(new ZipEntry(srcFile[i].getName()));
				int read=0;
				while((read=fis.read(buffer))>0)
				{
					outputStream.write(buffer, 0, read);
				}
				outputStream.closeEntry();
				fis.close();
			}
			outputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * get fileName
	 * @return
	 */
	public static String getFileName() {
		// TODO Auto-generated method stub
		String fileName=TITLE+Utils.createRandomName();
		return fileName;
	}
	
	public void setResponseHeader(HttpServletResponse response, String fileName)
	{
		try {
			TITLE=fileName;
			response.reset();
			response.setContentType("application/octet-stream;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename="+new String(TITLE.getBytes("UTF-8"))+".zip");
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
