package com.osms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.osms.dao.PaymentTypeDao;
import com.osms.entity.PaymentType;
import com.osms.utils.Packager;

@Repository("paymentTypeDao")
public class PaymentTypeDaoImpl extends JDBCBase implements PaymentTypeDao {

	@Override
	public PaymentType getPaymentTypeByPaymentTypeId(int paymentTypeId) {
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		PaymentType paymentType=null;
		String sql="SELECT * FROM PaymentType WHERE paymentTypeStatus=1 AND paymentTypeId="+paymentTypeId;
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			if(rs.next())
			{
				paymentType=Packager.PackagerPaymentType(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return paymentType;
	}

	@Override
	public List<PaymentType> getAllPaymentType() {
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<PaymentType> paymentTypes=new ArrayList<PaymentType>();
		String sql="SELECT * FROM PaymentType WHERE paymentTypeStatus=1";
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			while(rs.next())
			{
				paymentTypes.add(Packager.PackagerPaymentType(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return paymentTypes;
	}

	@Override
	public void save(PaymentType paymentType) {
		String sql="INSERT INTO PaymentType VALUES(?,?,?)";
		Object[] parma={
			paymentType.getcName(),
			paymentType.geteName(),
			paymentType.getStatus()
		};
		saveOrUpdateOrDelete(sql, parma);
	}

	@Override
	public void update(PaymentType paymentType) {
		StringBuilder sql= new StringBuilder("UPDATE PaymentType SET paymentTypeeName=?");
		ArrayList<Object> parma=new ArrayList<Object>();
		parma.add(paymentType.geteName());
		if(paymentType.getcName()!=null&&!"".equals(paymentType.getcName()))
		{
			sql.append(", paymentTypecName=?");
			parma.add(paymentType.getcName());
		}
		if(paymentType.getStatus()!=0)
		{
			sql.append(", paymentTypeStatus=?");
			parma.add(paymentType.getStatus());
		}
		sql.append(" WHERE paymentTypeId=?");
		parma.add(paymentType.getPaymentTypeId());
		saveOrUpdateOrDelete(sql.toString(), parma.toArray());
	}

	@Override
	public void delete(PaymentType paymentType, String type) {
		StringBuilder sql=new StringBuilder("DELETE FROM PaymentType WHERE 1=1 ");
		ArrayList<Object> parma=new ArrayList<Object>();
		try
		{
			if(paymentType.getPaymentTypeId()!=0)
			{
				sql.append(type+"paymentTypeId=?");
				parma.add(paymentType.getPaymentTypeId());
			}
			
			if(paymentType.getcName()!=null&&!"".equals(paymentType.getcName()))
			{
				sql.append(type+"paymentTypecName=?");
				parma.add(paymentType.getcName());
			}
			if(paymentType.geteName()!=null&&!"".equals(paymentType.geteName()))
			{
				sql.append(type+"paymentTypeeName=?");
				parma.add(paymentType.geteName());
			}
			if(paymentType.getStatus()!=0)
			{
				sql.append(type+"paymentTypeStatus=?");
				parma.add(paymentType.getStatus());
			}
			saveOrUpdateOrDelete(sql.toString(), parma.toArray());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
