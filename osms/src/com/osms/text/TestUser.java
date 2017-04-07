package com.osms.text;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.osms.dao.jdbc.JDBCUtil;
import com.osms.dao.jdbc.SearchByPagesDaoImpl;
import com.osms.dao.jdbc.UserDaoImpl;
import com.osms.entity.Users;
import com.osms.utils.Utils;

public class TestUser {

	@Test
	public void test()
	{
		save();
//		pages();
	}
	
	
	
	public void pages()
	{
		SearchByPagesDaoImpl searchByPagesDaoImpl=new SearchByPagesDaoImpl();
		Map<Object, Object> parma = new IdentityHashMap<Object, Object>();
		parma.put("userTypeId", 4);
		parma.put("gender", 1);
		int limit=2;
		int count = searchByPagesDaoImpl.getCount(parma, "Users");
		for(int pages=1;pages<=(count/(limit*1.0))+1;pages++)
		{
			//min is too big
			if((pages-1)*limit+1>count)
			{
				break;
			}
			List<Users> users=searchByPagesDaoImpl.getUsers(parma, limit, pages, count);
			System.out.println("------------"+pages+"------------");
			for(Users u:users)
			{
				System.out.println(u);
			}
			System.out.println("------------"+pages+"------------");
		}
		
	}
	
	public void save()
	{
		Users user=new Users(0, "System", 1, 1, "17855833076", 
				"123456@qq.com", Utils.toMD5("123456"), Utils.stringToDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date())),
				1, null);
		
		UserDaoImpl userDao=new UserDaoImpl();
		Connection conn=JDBCUtil.getConnection();
		try {
			conn.setAutoCommit(false);
			//save a student
			int userId=userDao.save(user, conn);
			//set userId to other object
			//fundingOnUser 
//			for(FundingOnUser f:user.getFundingOnUsers())
//			{
//				f.setUserId(userId);
//				fundingOnUserDao.save(f, conn);
//			}
//			//educationOnUser
//			user.getEducationOnUser().setUserId(userId);
//			//save
//			educationOnUserDao.save(user.getEducationOnUser(), conn);
//			//IdentityOnUser
//			user.getIdentityOnUser().setUserId(userId);
//			identityOnUserDao.save(user.getIdentityOnUser(), conn);
//			//VisaOnUser
//			user.getVisaOnUser().setUserId(userId);
//			//save
//			visaOnUserDao.save(user.getVisaOnUser(), conn);
//			//AMCOnUser,as a student, only has a AMCOnUser object, but to teacher, maybe many
//			for(AMCOnUser a:user.getAmcOnUsers())
//			{
//				a.setUserId(userId);
//				amcOnUserDao.save(a, conn);
//			}
//			//SchoolRoll
//			user.getSchoolRoll().setUserId(userId);
//			//save
//			schoolRollDao.save(user.getSchoolRoll(), conn);
//			//PassportOnUser
//			for(PassportOnUser p:user.getPassportOnUsers())
//			{
//				p.setUserId(userId);
//				passportOnUserDao.save(p, conn);
//			}
			//transaction commit
			System.out.println(userId);
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
		}finally
		{
			JDBCUtil.close(conn);
		}
	}
}
