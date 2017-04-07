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

	private static int NUM=100;//����xls���������
	private static String TITLE="";//xls����
	private static short DEFAULT_COLUMN_WIDTH=18;//�п�
	private static short TITLE_FONT_HEIGHT=15;//���������С
	private static int HIGHT_FOR_PIC=60;//ͼƬ�߶�
	
	/**
	 * ������������������ѹ���ļ�
	 * @param headers ��ͷ
	 * @param columns ����
	 * @param result �����
	 * @param out �����
	 * @param request HttpServletRequest
	 * @param pattern Date��ʽ��
	 * @throws IOException
	 */
	public void exportExcel(String[] headers, String[] columns, List<T> result, OutputStream out, HttpServletRequest request, String pattern)
			throws IOException
	{
		//�����ļ�����
		int n=fileNUMS(result);		
		//�����ļ���
		List<String> fileNames=new ArrayList<String>();
		for(int j=0;j<n;j++)
		{
			//��ȡÿ��xls���ļ�����
			Collection<T> res=getPerFileData(j, n, result);
			
			//���ɹ�����
			Workbook workbook=new HSSFWorkbook();
			HSSFSheet sheet=(HSSFSheet) workbook.createSheet(TITLE);
			sheet.setDefaultColumnWidth(DEFAULT_COLUMN_WIDTH);
			
			String file=request.getServletContext().getRealPath("/")+"WEB-INF/model/"+getFileName()+"-"+j+".xls";
			
			fileNames.add(file);
			//�����ļ�
			File dir=new File(file);
			if(!dir.exists()&&dir.isDirectory())
			{
				dir.mkdirs();
			}
			
			FileOutputStream fos=new FileOutputStream(new File(file));
			//������ʽ��
			HSSFCellStyle style=(HSSFCellStyle) workbook.createCellStyle();
			//������ʽ
			HSSFPatriarch patriarch=setXLSStyle(style, sheet, headers, workbook);
			
			HSSFRow row=sheet.createRow(1);
			//���ɱ�ͷ
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
						//��ȡ��������
						Object value=reflectGetFunction(columns[i], t);
						String textValue=null;
						//��������ת��
						textValue = matchDate(columns[i], i, row, sheet, value, patriarch, workbook, index, pattern);
						//����xls��Ԫ��
						fillData(textValue, cell);
					}
				}
			}
			workbook.write(fos);
			//�����ļ���ѹ��
			CreateAndZipFile(fileNames, request, out);
		}
	}

	/**
	 * ������ѹ��
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
		//ѹ���ļ�
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
	 * ��������ֵxls
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
	 * ��������ת��
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
			   textValue="��";
		   }else if(value.equals(2))
		   {
			   textValue="Ů";
		   }
		}else if(col.equals("userTypeId"))
		{
			if(value.equals(1))
			{
				textValue="ϵͳ����Ա";
			}else if(value.equals(2))
			{
				textValue="����Ա";
			}else if(value.equals(3))
			{
				textValue="��ʦ";
			}else if(value.equals(4))
			{
				textValue="��ѧ��";
			}
		}else if(col.equals("status"))
		{
			if(value.equals(1))
			{
				textValue="����ʹ��";
			}else if(value.equals(-1))
			{
				textValue="��ɾ��";
			}
		}else{
			textValue=value.toString();
		}
		return textValue;
	}

	/**
	 * ����get����
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
	 * ���ɱ�ͷ
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
	 * ����xls����ʽ
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
		//��������
		HSSFFont font=(HSSFFont) workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);
		
		style.setWrapText(true);
		
		HSSFPatriarch patriarch=sheet.createDrawingPatriarch();
		//����ͷ
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
	 * ��ȡÿ���ļ�������
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
	 * �����ļ�����
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
	 * ������Ӧͷ
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
