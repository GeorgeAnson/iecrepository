package com.osms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.osms.dao.PaymentDao;
import com.osms.entity.Payment;
import com.osms.utils.Packager;

@Repository("paymentDao")
public class PaymentDaoImpl extends JDBCBase implements PaymentDao {

	@Override
	public Payment getPaymentByPaymentId(int paymentId) {
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		Payment payment=null;
		String sql="SELECT * FROM Payment WHERE paymentStatus=1 AND paymentId="+paymentId;
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			if(rs.next())
			{
				payment=Packager.PackagerPayment(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return payment;
	}

	@Override
	public List<Payment> getAllPayment() {
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Payment> payments=new ArrayList<Payment>();
		String sql="SELECT * FROM Payment WHERE paymentStatus=1";
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			while(rs.next())
			{
				payments.add(Packager.PackagerPayment(rs));
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
	public void save(Payment payment) {
		String sql="INSERT INTO Payment VALUES(?,?,?,?,?,?,?,?,?,?)";
		Object[] parma={
			payment.getUserId(),
			payment.getPaymentTypeId(),
			payment.getValidTime(),
			payment.getInvalidTime(),
			payment.getTotalMoney(),
			payment.getMoney(),
			payment.getPaymentOprUser(),
			payment.getPayDate(),
			payment.getDescrible(),
			payment.getStatus()
		};
		saveOrUpdateOrDelete(sql, parma);
	}

	@Override
	public void update(Payment payment) {
		StringBuilder sql=new StringBuilder("UPDATE Payment SET paymentStatus=?");
		ArrayList<Object> parma=new ArrayList<Object>();
		parma.add(payment.getStatus());
		
		if(payment.getUserId()!=0)
		{
			sql.append(", paymentUser=?");
			parma.add(payment.getUserId());
		}
		
		if(payment.getPaymentTypeId()!=0)
		{
			sql.append(", paymentType=?");
			parma.add(payment.getPaymentTypeId());
		}
		if(payment.getValidTime()!=null)
		{
			sql.append(", validTime=?");
			parma.add(payment.getValidTime());
		}
		
		if(payment.getInvalidTime()!=null)
		{
			sql.append(", invalidTime=?");
			parma.add(payment.getInvalidTime());
		}
		if(payment.getTotalMoney()>=0)
		{
			sql.append(", totalMoney=?");
			parma.add(payment.getTotalMoney());
		}
		if(payment.getMoney()>=0)
		{
			sql.append(", money=?");
			parma.add(payment.getMoney());
		}
		
		if(payment.getPayDate()!=null&&!"".equals(payment.getPayDate()))
		{
			sql.append(", payDate=?");
			parma.add(payment.getPayDate());
		}
		
		if(payment.getDescrible()!=null&&!"".equals(payment.getDescrible()))
		{
			sql.append(", describle=?");
			parma.add(payment.getDescrible());
		}
		
		if(payment.getOprUser()!=null)
		{
			sql.append(", paymentOprUser=?");
			parma.add(payment.getPaymentOprUser());
		}
		saveOrUpdateOrDelete(sql.toString(), parma.toArray());
	}

	@Override
	public List<Payment> getPaymentByUserId(int userId) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Payment> payments=new ArrayList<Payment>();
		String sql="SELECT * FROM Payment WHERE paymentStatus=1 AND paymentUser="+userId+"  order by paymentType";
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			while(rs.next())
			{
				payments.add(Packager.PackagerPayment(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtil.close(rs, ps, conn);
		}
		return payments;
	}

	@Override
	public List<Payment> getPaymentByConditions(Payment payment) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<Payment> payments=new ArrayList<Payment>();
		StringBuilder sql=new StringBuilder("SELECT * FROM Payment WHERE paymentStatus=1");
		ArrayList<Object> parmas=new ArrayList<Object>();
		if(payment.getPaymentTypeId()!=0)
		{
			sql.append(" AND paymentId=?");
			parmas.add(payment.getId());
		}
		if(payment.getPaymentTypeId()!=0)
		{
			sql.append(" AND paymentTypeId=?");
			parmas.add(payment.getPaymentTypeId());
		}
		if(payment.getValidTime()!=null)
		{
			sql.append(" AND validTime=?");
			parmas.add(payment.getValidTime());
		}
		if(payment.getInvalidTime()!=null)
		{
			sql.append(" AND invalidTime=?");
			parmas.add(payment.getInvalidTime());
		}
		if(payment.getTotalMoney()!=0)
		{
			sql.append(" AND totalMoney=?");
			parmas.add(payment.getTotalMoney());
		}
		if(payment.getMoney()!=0)
		{
			sql.append(" AND money=?");
			parmas.add(payment.getMoney());
		}
		if(payment.getPaymentOprUser()!=0)
		{
			sql.append(" AND paymentOprUser=?");
			parmas.add(payment.getPaymentOprUser());
		}
		if(payment.getPayDate()!=null)
		{
			sql.append(" AND payDate=?");
			parmas.add(payment.getPayDate());
		}
		sql.append(" ORDER BY paymentUser");
		try {
			ps=conn.prepareStatement(sql.toString());
			rs=query(ps);
			while(rs.next())
			{
				payments.add(Packager.PackagerPayment(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCUtil.close(rs, ps, conn);
		}
		return payments;
	}

	@Override
	public void save(Payment p, Connection conn) {
		// TODO Auto-generated method stub
		String sql="INSERT INTO Payment VALUES(?,?,?,?,?,?,?,?,?,?)";
		Object[] parma={
			p.getUserId(),
			p.getPaymentTypeId(),
			p.getValidTime(),
			p.getInvalidTime(),
			p.getTotalMoney(),
			p.getMoney(),
			p.getPaymentOprUser(),
			p.getPayDate(),
			p.getDescrible(),
			p.getStatus()
		};
		save(sql, parma, conn);
	}

}
