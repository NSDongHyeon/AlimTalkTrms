package com.nsmall.backend.logistics.comm;

import java.util.List;
import java.util.Map;


import com.nsmall.fw.dao.NSITAbstractDAO;
import org.springframework.stereotype.Repository;

/**
 * @Class NsAlimTalkDAO.java
 * @Description DB 데이터 조회 및 조작 오브젝트
 * @Modification Information @ @
 * 
 * @author ehdgus0896
 * @since
 * @version 1.0 
 */
@Repository("NsAlimTalkDAO")
public class NsAlimTalkDAO extends NSITAbstractDAO{

	/**
	 * SMS 미사용 고객 SMS 전송 제외를 위한 COUNT (SELECT)
	 * @param aMap
	 * @return
	 * @throws Exception
	 */
	public List<Map> smsUseCount(Map aMap) throws Exception{
		
		return list("NsAlimTalkDAO.smsUseCount", aMap);
	}
	
	/**
	 * 스위트래커 택배사 코드로 변경 (SELECT)
	 * @param aMap
	 * @param i_lsc_cd 
	 * @return
	 * @throws Exception
	 */
	public List<Map> SwtrLscCd(Map aMap) throws Exception{
		

		return list("NsAlimTalkDAO.SwtrLscCd", aMap);
	}
	
	
	/**
	 * SMS 전환 여부를 반환 (SELECT)
	 * @param aMap
	 * @return
	 * @throws Exception
	 */
	public List<Map> smsConvYN(Map aMap) throws Exception{
		
		return list("NsAlimTalkDAO.smsConvYN", aMap);
	}
	
	/**
	 * 알림톡 데이터 전송을 위한 테이블에 DATA INSERT
	 * @param vo
	 * @return 
	 * @return 
	 * @return
	 * @throws Exception
	 */
	public  Object commAlimTrmsInf(Object vo) throws Exception{
		
		System.out.println("알림톡 [NsAlimTalkDAO.commAlimTrmsInf 도착 -> 데이터 수신 OK]");
		System.out.println("");
		System.out.println("테이블에 데이터 Insert 시작");
		return insert("NsAlimTalkDAO.commAlimTrmsInf", vo);
	}
	
	/**
	 * SMS 데이터 전송을 위한 테이블에 DATA INSERT
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public Object commSmsTrmsInf(Object vo) throws Exception{
		
		System.out.println("SMS [NsAlimTalkDAO.commAlimTrmsInf 도착 -> 데이터 수신 OK]");
		System.out.println("");
		System.out.println("테이블에 데이터 Insert 시작");
		return insert("NsAlimTalkDAO.commSmsTrmsInf", vo);
	}
		
}
