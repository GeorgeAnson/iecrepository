package com.osms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.osms.dao.SearchByPagesDao;
import com.osms.entity.Payment;
import com.osms.entity.PaymentType;
import com.osms.entity.Users;
import com.osms.globle.Constants;
import com.osms.utils.Packager;

@Repository("searchByPagesDao")
public class SearchByPagesDaoImpl extends JDBCBase implements SearchByPagesDao {

	@Override
	public int getCount(Map<Object, Object> parma, String tableName) {
		// TODO Auto-generated method stub
		StringBuilder sql=new StringBuilder("SELECT COUNT(*) FROM "+tableName+" WHERE 1=1");
		
		for(Map.Entry<Object, Object> entity:parma.entrySet())
		{
			sql.append(" AND "+entity.getKey()+" = "+entity.getValue());
		}
		int count=getCount(sql.toString());
		return count;
	}

	@Override
	public List<Users> getUsers(Map<Object, Object> parma, int limit, int pages, int count) {
		// TODO Auto-generated method stub
		int min=(pages-1)*limit+1;
		int max=pages*limit;
		if(max>count)
		{
			max=count;
		}
		if(max<10)
		{
			max=10;
		}
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Users> users= new ArrayList<Users>();
		StringBuilder psql=new StringBuilder();
		for(Map.Entry<Object, Object> entity:parma.entrySet())
		{
			psql.append(" AND "+entity.getKey()+" = "+entity.getValue());
		}
		StringBuilder sql=new StringBuilder("SELECT * FROM ( SELECT row_number() over ( order by userId ) rownumber, * from Users  WHERE userStatus=1");
		sql.append(psql);
		sql.append(") a WHERE rownumber BETWEEN "+min+" AND "+max);
		try {
			ps=conn.prepareStatement(sql.toString());
			rs=query(ps);
			while(rs.next())
			{
				users.add(Packager.PackagerUser(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return users;
	}

	@Override
	public List<Users> getUsersByAMCOnUsers(Map<Object, Object> parma, int limit, int pages, int count) {
		// TODO Auto-generated method stub
		int min=(pages-1)*limit+1;
		int max=pages*limit;
		if(max>count)
		{
			max=count;
		}
		if(max<10)
		{
			max=10;
		}
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Users> users= new ArrayList<Users>();
		StringBuilder psql=new StringBuilder();
		for(Map.Entry<Object, Object> entity:parma.entrySet())
		{
			if(entity.getValue().equals(Constants.TEACHER))
			{
				psql.append(" AND "+entity.getKey()+" !=  "+Constants.STUDENT.trim());
			}else
			{
				psql.append(" AND "+entity.getKey()+" = "+entity.getValue());
			}
		}
		StringBuilder sql=new StringBuilder("SELECT * FROM ( SELECT row_number() over ( order by userId)"
				+ " rownumber, userId, gender, fullName, userPhone, email, userType"
				+ " FROM Users, AMCOnUser"
				+ " WHERE amcOnUserStatus=1 AND userStatus=1 AND userId=amcOnUserUser ");
		sql.append(psql);
		sql.append(") a  WHERE rownumber BETWEEN "+min+" AND "+max);
		System.out.println(sql.toString());
		try {
			ps=conn.prepareStatement(sql.toString());
			rs=query(ps);
			while(rs.next())
			{
				Users user=new Users();
				user.setUserId(rs.getInt("userId"));
				user.setGender(rs.getInt("gender"));
				user.setFullName(rs.getString("fullName"));
				user.setPhone(rs.getString("userPhone"));
				user.setEmail(rs.getString("email"));
				user.setUserTypeId(rs.getInt("userType"));
				users.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return users;
	}

	@Override
	public int getCount(Map<Object, Object> parma, Map<String, String> table) {
		// TODO Auto-generated method stub
		
		StringBuilder sql=new StringBuilder("SELECT COUNT(*) FROM");
		
		boolean flag=true;
		for(Map.Entry<String, String> entity:table.entrySet())
		{
			if(flag)
			{
				sql.append(" "+entity.getKey());
				flag=false;
			}else
			{
				sql.append(" ,"+entity.getKey());
			}
		}
		sql.append(" WHERE 1=1");
		flag=true;
		for(Map.Entry<String, String> entity:table.entrySet())
		{
			if(flag)
			{
				sql.append(" AND "+entity.getKey()+"."+entity.getValue()+" = ");
				flag=false;
			}else
			{
				sql.append(entity.getKey()+"."+entity.getValue());
				flag=true;
			}
		}
		
		for(Map.Entry<Object, Object> entity:parma.entrySet())
		{
			if(entity.getValue().equals(Constants.TEACHER))
			{
				sql.append(" AND "+entity.getKey()+" !=  "+Constants.STUDENT.trim());
			}else
			{
				sql.append(" AND "+entity.getKey()+" = "+entity.getValue());
			}
			//sql.append(" AND "+entity.getKey()+" = "+entity.getValue());
		}
		System.out.println(sql.toString());
		int count=getCount(sql.toString());
		return count;
	}

	@Override
	public List<Payment> getUsersByPaymentOnamc(IdentityHashMap<Object, Object> parma, int limit, int pages, int count) {
		// TODO Auto-generated method stub
		int min=(pages-1)*limit+1;
		int max=pages*limit;
		if(max>count)
		{
			max=count;
		}
		if(max<10)
		{
			max=10;
		}
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Payment> payments= new ArrayList<Payment>();
		StringBuilder psql=new StringBuilder();
		for(Map.Entry<Object, Object> entity:parma.entrySet())
		{
			psql.append(" AND "+entity.getKey()+" = "+entity.getValue());
		}
		StringBuilder sql=new StringBuilder("SELECT * FROM ( SELECT row_number() over ( order by userId)"
				+ " rownumber, userId, fullName, userPhone, email, schoolYear, theSemester, totalMoney, money, paymentTypeId, paymentTypecName, paymentOprUser, describle"
				+ " FROM Users, AMCOnUser, Payment, PaymentType"
				+ " WHERE userStatus=1 AND paymentStatus=1 AND paymentTypeStatus=1"
				+ " AND userId=amcOnUserUser AND userId=paymentUser AND paymentTypeId=paymentType ");
		sql.append(psql);
		System.out.println("psql:"+psql);
		sql.append(") a  WHERE rownumber BETWEEN "+min+" AND "+max);
		System.out.println(sql.toString());
		try {
			ps=conn.prepareStatement(sql.toString());
			rs=query(ps);
			while(rs.next())
			{
				Users user=new Users();
				user.setUserId(rs.getInt("userId"));
				user.setFullName(rs.getString("fullName"));
				user.setPhone(rs.getString("userPhone"));
				user.setEmail(rs.getString("email"));
				Payment payment=new Payment();
				payment.setUser(user);
				payment.setSchoolYear(rs.getString("schoolYear"));
				payment.setTheSemester(rs.getInt("theSemester"));
				payment.setTotalMoney(rs.getDouble("totalMoney"));
				payment.setMoney(rs.getDouble("money"));
				payment.setPaymentOprUser(rs.getInt("paymentOprUser"));
				payment.setDescrible(rs.getString("describle"));
				PaymentType paymentType=new PaymentType();
				paymentType.setPaymentTypeId(rs.getInt("paymentTypeId"));
				paymentType.setcName(rs.getString("paymentTypecName"));
				payment.setPaymentType(paymentType);
				payments.add(payment);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return payments;
	}

	@Override
	public List<Payment> getUsersByPaymentOnamc(int userId) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Payment> payments= new ArrayList<Payment>();
	
		StringBuilder sql=new StringBuilder(
				"SELECT userId, fullName, userPhone, email, schoolYear, theSemester, totalMoney, money, paymentTypeId, paymentTypecName, paymentOprUser, describle"
				+ " FROM Users, AMCOnUser, Payment, PaymentType"
				+ " WHERE userStatus=1 AND paymentStatus=1 AND paymentTypeStatus=1"
				+ " AND userId=amcOnUserUser AND userId=paymentUser AND paymentTypeId=paymentType AND userId="+userId);

		System.out.println(sql.toString());
		try {
			ps=conn.prepareStatement(sql.toString());
			rs=query(ps);
			while(rs.next())
			{
				Users user=new Users();
				user.setUserId(rs.getInt("userId"));
				user.setFullName(rs.getString("fullName"));
				user.setPhone(rs.getString("userPhone"));
				user.setEmail(rs.getString("email"));
				Payment payment=new Payment();
				payment.setUser(user);
				payment.setSchoolYear(rs.getString("schoolYear"));
				payment.setTheSemester(rs.getInt("theSemester"));
				payment.setTotalMoney(rs.getDouble("totalMoney"));
				payment.setMoney(rs.getDouble("money"));
				payment.setPaymentOprUser(rs.getInt("paymentOprUser"));
				payment.setDescrible(rs.getString("describle"));
				PaymentType paymentType=new PaymentType();
				paymentType.setPaymentTypeId(rs.getInt("paymentTypeId"));
				paymentType.setcName(rs.getString("paymentTypecName"));
				payment.setPaymentType(paymentType);
				payments.add(payment);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return payments;
	}

}
