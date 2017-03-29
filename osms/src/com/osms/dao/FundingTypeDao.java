package com.osms.dao;

import java.util.List;

import com.osms.entity.FundingType;

public interface FundingTypeDao {

	/**
	 * get a fundingType object by fundingTypeId
	 * @param fundingTypeId
	 * @return
	 */
	public FundingType getFundingTypeByFundingTypeId(int fundingTypeId);
	
	
	/**
	 * get all fundingType objects
	 * @return
	 */
	public List<FundingType> getAllFundingType();
	
	
	/**
	 * save a fundingType object
	 * @param fundingType
	 * @return
	 */
	public int save(FundingType fundingType);
	
	
	/**
	 * update a fundingType object
	 * @param fundingType
	 */
	public void update(FundingType fundingType);
	
	
	/**
	 * delete operation
	 * @param fundingType
	 * 		fundingType object 
	 * @param type
	 * 		key word operation:AND operation, OR operation
	 */
	public void delete(FundingType fundingType, String type);
}
