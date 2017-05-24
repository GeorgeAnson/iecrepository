package com.osms.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osms.dao.PaymentDao;
import com.osms.dao.PaymentTypeDao;
import com.osms.dao.SearchByPagesDao;
import com.osms.dao.jdbc.JDBCUtil;
import com.osms.entity.Payment;
import com.osms.entity.PaymentType;
import com.osms.service.PaymentService;

@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	PaymentDao paymentDao;
	
	@Autowired
	PaymentTypeDao paymentTypeDao;
	
	@Autowired
	SearchByPagesDao searchByPagesDao;
	
	@Override
	public List<Payment> getPaymentByUserId(int userId) {
		// TODO Auto-generated method stub
		List<Payment> payments = searchByPagesDao.getUsersByPaymentOnamc(userId);	
		List<Payment> results=new ArrayList<Payment>();
		Payment res=new Payment();
		int id=0;
		for(Payment p:payments)
		{
			if(id!=p.getUser().getUserId())
			{
				if(id!=0)
				{
					results.add(res);
					res=null;
				}
				res=p;
				id=p.getUser().getUserId();
			}else
			{
				if(p.getMoney()==0)
				{
					res.setTotalMoney(res.getTotalMoney()+p.getTotalMoney());
				}
				res.setMoney(res.getMoney()+p.getMoney());
			}
		}
		if(payments.size()>0)
		{
			results.add(res);
		}
		
		return results;
	}

	@Override
	public List<Payment> getPaymentByConditions(Payment payment) {
		// TODO Auto-generated method stub
		List<Payment> payments=paymentDao.getPaymentByConditions(payment);
		List<PaymentType> paymentTypes=paymentTypeDao.getAllPaymentType();
		for(Payment p : payments)
		{
			for(PaymentType pt : paymentTypes)
			{
				if(p.getPaymentTypeId()==pt.getPaymentTypeId())
				{
					p.setPaymentType(pt);
				}
			}
		}
		return payments;
	}

	@Override
	public void save(List<Payment> payments) {
		// TODO Auto-generated method stub
		Connection conn=JDBCUtil.getConnection();
		try {
			conn.setAutoCommit(false);
			for(Payment p:payments)
			{
				paymentDao.save(p, conn);
			}
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Payment> searchByPaymentOnamc(IdentityHashMap<Object, Object> parma, int limit, int page, int count) {
		// TODO Auto-generated method stub
		List<Payment> payments = searchByPagesDao.getUsersByPaymentOnamc(parma, limit, page, count);
		
		List<Payment> results=new ArrayList<Payment>();
		Payment res=new Payment();
		int id=0;
		for(Payment p:payments)
		{
			if(id!=p.getUser().getUserId())
			{
				if(id!=0)
				{
					results.add(res);
					res=null;
				}
				res=p;
				id=p.getUser().getUserId();
			}else
			{
				if(p.getMoney()==0)
				{
					res.setTotalMoney(res.getTotalMoney()+p.getTotalMoney());
				}
				res.setMoney(res.getMoney()+p.getMoney());
			}
		}
		if(payments.size()>0)
		{
			results.add(res);
		}
		
		return results;
	}

	@Override
	public List<Payment> searchByPaymentOnUserId(int userId) {
		// TODO Auto-generated method stub
		List<Payment> payments = searchByPagesDao.getUsersByPaymentOnamc(userId);		
		List<Payment> results=new ArrayList<Payment>();
		Payment res=new Payment();
		int id=0;
		for(Payment p : payments)
		{
			if(id!=p.getPaymentTypeId())
			{
				if(id!=0)
				{
					results.add(res);
					res=null;
				}
				res=p;
				id=p.getPaymentTypeId();
			}else
			{
				res.setMoney(res.getMoney()+p.getMoney());
				if(p.getDescrible()!=null&&!"".contentEquals(p.getDescrible()))
				{
					res.setDescrible(p.getDescrible());//最近一次缴费描述	
				}
				if(p.getInvalidTime()!=null&&!"".equals(p.getInvalidTime()))
				{
					res.setInvalidTime(p.getInvalidTime());
				}
				if(p.getPayDate()!=null&&!"".equals(p.getPayDate()))
				{
					res.setPayDate(p.getPayDate());//最近一次缴费日期
				}
			}
		}
		if(payments.size()>0)
		{
			results.add(res);
		}
		System.out.println(results);
		return results;
	}

	@Override
	public List<Payment> getPaymentForDifferTypeByUserId(int userId) {
		// TODO Auto-generated method stub
		List<Payment> payments=paymentDao.getPaymentByUserId(userId);
		List<PaymentType> paymentTypes=paymentTypeDao.getAllPaymentType();
		List<Payment> results=new ArrayList<>();
		Payment res=new Payment();
		int id=0;
		for(Payment p : payments)
		{
			if(id!=p.getPaymentTypeId())
			{
				if(id!=0)
				{
					results.add(res);
					res=null;
				}
				res=p;
				id=p.getPaymentTypeId();
			}else
			{
				res.setMoney(res.getMoney()+p.getMoney());
				if(p.getDescrible()!=null&&!"".contentEquals(p.getDescrible()))
				{
					res.setDescrible(p.getDescrible());//最近一次缴费描述	
				}
				if(p.getPayDate()!=null&&!"".equals(p.getPayDate()))
				{
					res.setPayDate(p.getPayDate());//最近一次缴费日期
				}
			}
		}
		if(payments.size()>0)
		{
			results.add(res);
		}
		for(Payment p : results)
		{
			for(PaymentType pt : paymentTypes)
			{
				if(p.getPaymentTypeId()==pt.getPaymentTypeId())
				{
					p.setPaymentType(pt);
				}
			}
		}
		return results;
	}

}
