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

	private static int NUM=100;//单个xls最大数据量
	private static String TITLE="";//xls标题
	private static short DEFAULT_COLUMN_WIDTH=18;//列宽
	private static short TITLE_FONT_HEIGHT=15;//标题字体大小
	private static int HIGHT_FOR_PIC=60;//图片高度
	
	/**
	 * 大量数据批量导出并压缩文件
	 * @param headers 表头
	 * @param columns 属性
	 * @param result 结果集
	 * @param out 输出流
	 * @param request HttpServletRequest
	 * @param pattern Date格式化
	 * @throws IOException
	 */
	public void exportExcel(String[] headers, String[] columns, List<T> result, OutputStream out, HttpServletRequest request, String pattern)
			throws IOException
	{
		//计算文件数量
		int n=fileNUMS(result);		
		//保存文件名
		List<String> fileNames=new ArrayList<String>();
		for(int j=0;j<n;j++)
		{
			//获取每份xls的文件数据
			Collection<T> res=getPerFileData(j, n, result);
			
			//生成工作簿
			Workbook workbook=new HSSFWorkbook();
			HSSFSheet sheet=(HSSFSheet) workbook.createSheet(TITLE);
			sheet.setDefaultColumnWidth(DEFAULT_COLUMN_WIDTH);
			
			String file=request.getServletContext().getRealPath("/")+"WEB-INF/model/"+getFileName()+"-"+j+".xls";
			
			fileNames.add(file);
			//生成文件
			File dir=new File(file);
			if(!dir.exists()&&dir.isDirectory())
			{
				dir.mkdirs();
			}
			
			FileOutputStream fos=new FileOutputStream(new File(file));
			//生成样式表
			HSSFCellStyle style=(HSSFCellStyle) workbook.createCellStyle();
			//设置样式
			HSSFPatriarch patriarch=setXLSStyle(style, sheet, headers, workbook);
			
			HSSFRow row=sheet.createRow(1);
			//生成表头
			createHeader(headers, row, style);
			
			if(result!=null)
			{
				int index=2;
				for(T t:res)
				{
					row=sheet.createRow(index);
					index++;					
					for(int i=0;i<columns.length;i++)
					{
						HSSFCell cell=row.createCell(i);
						//获取反射数据
						Object value=reflectGetFunction(columns[i], t);
						String textValue=null;
						//数据类型转换
						textValue = matchDate(columns[i], i, row, sheet, value, patriarch, workbook, index, pattern);
						//填入xls单元格
						fillData(textValue, cell);
					}
				}
			}
			workbook.write(fos);
			//生成文件并压缩
			CreateAndZipFile(fileNames, request, out);
		}
	}

	/**
	 * 创建并压缩
	 * @param fileNames
	 * @param request
	 * @param out
	 */
	private void CreateAndZipFile(List<String> fileNames, HttpServletRequest request, OutputStream out) {
		// TODO Auto-generated method stub
		File zip=new File(request.getServletContext().getRealPath("/")+"WEB-INF/model/"+getFileName()+".zip");
		File srcFile[] =new File[fileNames.size()];
		for(int i=0,n1=fileNames.size();i<n1;i++)
		{
			srcFile[i]=new File(fileNames.get(i));
		}
		//压缩文件
		ZipFiles(srcFile, zip);
		FileInputStream fis;
		try {
			fis = new FileInputStream(zip);
			byte[] buffer=new byte[4096];
			int read=0;
			while((read=fis.read(buffer))!=-1)
			{
				out.write(buffer, 0, read);
			}
			fis.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 填入数据值xls
	 * @param textValue
	 * @param cell
	 */
	private void fillData(String textValue, HSSFCell cell) {
		// TODO Auto-generated method stub
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

	/**
	 * 数据类型转换
	 * @param col
	 * @param i
	 * @param row
	 * @param sheet
	 * @param value
	 * @param patriarch
	 * @param workbook
	 * @param index
	 * @param pattern
	 * @return
	 */
	private String matchDate(String col, int i, HSSFRow row, HSSFSheet sheet, Object value, HSSFPatriarch patriarch,
			Workbook workbook, int index, String pattern) {
		// TODO Auto-generated method stub
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
			sheet.setColumnWidth(i, (short)(27.5*80));
			byte[] bsValue=(byte[]) value;
			HSSFClientAnchor anchor=new HSSFClientAnchor(0, 0, 1023, 255, (short)6, index, (short)6, index);
			anchor.setAnchorType(AnchorType.DONT_MOVE_DO_RESIZE);
			patriarch.createPicture(anchor, workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
			
		}else if(col.equals("gender"))
		{
		   if(value.equals(1))
		   {
			   textValue="男";
		   }else if(value.equals(2))
		   {
			   textValue="女";
		   }
		}else if(col.equals("userTypeId"))
		{
			if(value.equals(1))
			{
				textValue="系统管理员";
			}else if(value.equals(2))
			{
				textValue="管理员";
			}else if(value.equals(3))
			{
				textValue="教师";
			}else if(value.equals(4))
			{
				textValue="留学生";
			}
		}else if(col.equals("status"))
		{
			if(value.equals(1))
			{
				textValue="正常使用";
			}else if(value.equals(-1))
			{
				textValue="已删除";
			}
		}else{
			textValue=value.toString();
		}
		return textValue;
	}

	/**
	 * 反射get方法
	 * @param col
	 * @param t
	 * @return
	 */
	private Object reflectGetFunction(String col, T t) {
		// TODO Auto-generated method stub
		String fieldName = col;
		String getMethodName = "get";
		Object value=null;
		if(fieldName.equals("cName")||fieldName.equals("eName"))
		{
			getMethodName+=fieldName;
		}else
		{
			getMethodName+=fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		}
        Class<?> clazz = t.getClass();   
        Method getMethod;
		try {
			getMethod = clazz.getMethod(getMethodName, new Class[]{});
			value = getMethod.invoke(t, new Class[]{});
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		return value;
	}

	/**
	 * 生成表头
	 * @param headers
	 * @param row
	 * @param style
	 */
	private void createHeader(String[] headers, HSSFRow row, HSSFCellStyle style) {
		// TODO Auto-generated method stub
		for(int i=0;i<headers.length;i++)
		{
			HSSFCell cell=row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text=new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
	}

	/**
	 * 设置xls表样式
	 * @param style
	 * @param sheet
	 * @param headers
	 * @param workbook
	 * @return
	 */
	private HSSFPatriarch setXLSStyle(HSSFCellStyle style, HSSFSheet sheet, String[] headers, Workbook workbook) {
		// TODO Auto-generated method stub
		style.setFillForegroundColor(HSSFColor.GOLD.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//设置字体
		HSSFFont font=(HSSFFont) workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);
		
		style.setWrapText(true);
		
		HSSFPatriarch patriarch=sheet.createDrawingPatriarch();
		//设置头
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
		return patriarch;
	}

	/**
	 * 获取每个文件的数据
	 * @param j
	 * @param n
	 * @param result
	 * @return 
	 */
	private Collection<T> getPerFileData(int j, int n, List<T> result) {
		// TODO Auto-generated method stub
		Collection<T> res=null;
		if(!CollectionUtils.isEmpty(result))
		{
			if(j==n-1)
			{
				if(result.size()%NUM==0)
				{
					res=result.subList(NUM*j, NUM*(j+1));
				}else
				{
					res=result.subList(NUM*j, NUM*j+result.size()%NUM);
				}
			}else
			{
				res=result.subList(NUM*j, NUM*(j+1));
			}
		}
		return res;
	}

	/**
	 * 计算文件数量
	 * @param result
	 * @return
	 */
	private int fileNUMS(List<T> result) {
		// TODO Auto-generated method stub
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
		return n;
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
	
	/**
	 * 设置响应头
	 * @param response
	 * @param fileName
	 */
	public void setResponseHeader(HttpServletResponse response, byte[] fileName)
	{
		try {
			TITLE=new String(fileName,"UTF-8");
			response.reset();
			response.setContentType("application/octet-stream;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename="+getFileName()+".zip");
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
