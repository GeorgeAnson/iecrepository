package com.osms.dao.jdbc;

import java.sql.Connection;

import org.springframework.stereotype.Repository;

import com.osms.dao.FundingOnUserDao;
import com.osms.entity.FundingOnUser;

@Repository("fundingOnUserDao")
public class FundingOnUserDaoImpl extends JDBCBase implements FundingOnUserDao {

	@Override
	public int save(FundingOnUser fundingOnUser, Connection conn) {
		String sql="INSERT INTO FundingOnUser VALUES(?,?,?)";
		Object[] parma={
			fundingOnUser.getUserId(),
			fundingOnUser.getFundingTypeId(),
			fundingOnUser.getStatus()
		};
		int id=save(sql, parma, conn);
		return id;
	}

}
