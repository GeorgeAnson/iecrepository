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
		List<Payment> payments=paymentDao.getPaymentByUserId(userId);
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
		for(Payment p:payments)
		{
			//get a userId
			int userId=p.getUser().getUserId();
			double totalMoney=0;
			double totalPaid=0;
			List<Integer> payTypeIds=new ArrayList<Integer>();
			payTypeIds.add(0);
			for(Payment payment:payments)
			{
				if(userId==payment.getUser().getUserId())
				{
					//the same user
					boolean flag=true;
					for(int payTypeId:payTypeIds)
					{
						//check paymentTypeId
						if(payTypeId==payment.getPaymentType().getPaymentTypeId())
						{
							flag=false;
						}
					}
					//different paymentTypeId
					if(flag)
					{
						totalMoney+=payment.getTotalMoney();
						payTypeIds.add(payment.getPaymentType().getPaymentTypeId());
					}
					totalPaid+=payment.getMoney();
				}
			}
			p.setTotalMoney(totalMoney);
			p.setMoney(totalPaid);
			results.add(p);
		}
		return results;
	}

	@Override
	public List<Payment> searchByPaymentOnUserId(int userId) {
		// TODO Auto-generated method stub
		List<Payment> payments = searchByPagesDao.getUsersByPaymentOnamc(userId);
		List<Payment> results=new ArrayList<Payment>();
		for(Payment p:payments)
		{
			//get a userId
			int user=p.getUser().getUserId();
			double totalMoney=0;
			double totalPaid=0;
			List<Integer> payTypeIds=new ArrayList<Integer>();
			payTypeIds.add(0);
			for(Payment payment:payments)
			{
				if(user==payment.getUser().getUserId())
				{
					//the same user
					boolean flag=true;
					for(int payTypeId:payTypeIds)
					{
						//check paymentTypeId
						if(payTypeId==payment.getPaymentType().getPaymentTypeId())
						{
							flag=false;
						}
					}
					//different paymentTypeId
					if(flag)
					{
						totalMoney+=payment.getTotalMoney();
						payTypeIds.add(payment.getPaymentType().getPaymentTypeId());
					}
					totalPaid+=payment.getMoney();
				}
			}
			p.setTotalMoney(totalMoney);
			p.setMoney(totalPaid);
			results.add(p);
		}
		return results;
	}

}
