package com.osms.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.osms.dao.FundingTypeDao;
import com.osms.entity.FundingType;
import com.osms.utils.Packager;

@Repository("fundingTypeDao")
public class FundingTypeDaoImpl extends JDBCBase implements FundingTypeDao {

	@Override
	public FundingType getFundingTypeByFundingTypeId(int fundingTypeId) {
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		FundingType fundingType=null;
		String sql="SELECT * FROM FundingType WHERE fundingTypeStatus=1 AND fundingTypeId="+fundingTypeId;
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			if(rs.next())
			{
				fundingType=Packager.PackagerFundingType(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return fundingType;
	}

	@Override
	public List<FundingType> getAllFundingType() {
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<FundingType> fundingTypes=new ArrayList<FundingType>();
		String sql="SELECT * FROM FundingType WHERE fundingTypeStatus=1";
		try {
			ps=conn.prepareStatement(sql);
			rs=query(ps);
			while(rs.next())
			{
				fundingTypes.add(Packager.PackagerFundingType(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			JDBCUtil.close(rs, ps, conn);
		}
		return fundingTypes;
	}

	@Override
	public int save(FundingType fundingType) {
		String sql="INSERT INTO FundingType VALUES(?,?,?)";
		Object[] parma={
			fundingType.getcName(),
			fundingType.geteName(),
			fundingType.getStatus()
		};
		int fundingTypeId=save(sql, parma);
		return fundingTypeId;
	}

	@Override
	public void update(FundingType fundingType) {
		StringBuilder sql= new StringBuilder("UPDATE FundingType SET fundingTypeeName=?");
		ArrayList<Object> parma=new ArrayList<Object>();
		parma.add(fundingType.geteName());
		if(fundingType.getcName()!=null&&!"".equals(fundingType.getcName()))
		{
			sql.append(", fundingTypecName=?");
			parma.add(fundingType.getcName());
		}
		if(fundingType.getStatus()!=0)
		{
			sql.append(", fundingTypeStatus=?");
			parma.add(fundingType.getStatus());
		}
		sql.append(" WHERE fundingTypeId=?");
		parma.add(fundingType.getFundingTypeId());
		saveOrUpdateOrDelete(sql.toString(), parma.toArray());
	}

	@Override
	public void delete(FundingType fundingType, String type) {
		StringBuilder sql=new StringBuilder("DELETE FROM FundingType WHERE 1=1");
		ArrayList<Object> parma=new ArrayList<Object>();
		try
		{
			if(fundingType.getFundingTypeId()!=0)
			{
				sql.append(type+"fundingTypeId=?");
				parma.add(fundingType.getFundingTypeId());
			}
			
			if(fundingType.getcName()!=null&&!"".equals(fundingType.getcName()))
			{
				sql.append(type+"fundingTypecName=?");
				parma.add(fundingType.getcName());
			}
			if(fundingType.geteName()!=null&&!"".equals(fundingType.geteName()))
			{
				sql.append(type+"fundingTypeeName=?");
				parma.add(fundingType.geteName());
			}
			if(fundingType.getStatus()!=0)
			{
				sql.append(type+"fundingTypeStatus=?");
				parma.add(fundingType.getStatus());
			}
			saveOrUpdateOrDelete(sql.toString(), parma.toArray());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
