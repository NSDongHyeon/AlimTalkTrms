package com.nsmall.backend.logistics.lo09.web;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.nsmall.backend.logistics.lo09.service.SVLO0902010Service;
import com.nsmall.fw.cmmn.ria.xplatform.XPlatformConstant;
import com.nsmall.fw.cmmn.ria.xplatform.map.DataSetMap;
import com.nsmall.fw.cmmn.ria.xplatform.map.XPlatformMapDTO;


import egovframework.rte.fdl.property.EgovPropertyService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.nsmall.backend.logistics.comm.NsAlimTalkService;


/**
 * @Class Name : SVLO0902010Controller.java
 * @Description : 출고완료 조회(미출내역 조회) Controller
 * @Modification Information @ @
 * 
 * @author 신동민
 * @since 2013.10.07
 * @version 1.0
 * @see Copyright (C) 2009 by MOPAS All right reserved.
 */
@Controller
public class SVLO0902010Controller {
	
	protected Log log = LogFactory.getLog(this.getClass());
	
	@Resource(name="SVLO0902010Service")
	private SVLO0902010Service SVLO0902010Service;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	@Resource(name = "NsAlimTalkService")
	private NsAlimTalkService alimTalkService;

	
	/**
	 * 알림톡 DATA Insert Test를 위한 메소드
	 * @param 
	 * @return Map(inVar, outVar)
	 * @throws Exception
	 */
	@RequestMapping(value = "/backend/lo/SVLO0902010/alimInsert.do")
	public void alimInsert() throws Exception{
			
		Map<String, Object> inVar 		= new HashMap<String, Object>();
		Map<String, Object> outVar 		= new HashMap<String, Object>();
		outVar = null;

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		
		inVar.put("v_recvr_tel","01012341234"); // 수신자 전화번호
		inVar.put("i_msg","[TEST] 알림톡 테스트 메세지"); //메시지 내용
		inVar.put("i_sms_msg", "[TEST]미출5건 발생 배송미처리현황에 지연사유 및 운송장 등록 바랍니다."); //SMS 메세지 내용
		inVar.put("i_send_to_tel","0212345678"); //발신자 전화번호
		inVar.put("i_sms_kind_cd","a"); // SMS 종류 코드
		inVar.put("i_sms_limit_yn","N"); // SMS 한정 여부
		inVar.put("i_lms_title",""); // KMS 제목
		inVar.put("i_tplt_id","NS_test_018"); // 템플릿 아이디
		inVar.put("v_swtr_lsc_cd",""); //택배사 코드
		inVar.put("i_wbl_num",""); //운송장 번호
		inVar.put("i_resv_send_dttm","00000000000"); // 예약 발송 일시
		inVar.put("i_vndr_cd","105564"); // 협력업체 코드
		inVar.put("i_goods_cd","0000"); // 상품 코드
		inVar.put("i_cust_num","2134"); // 고객 번호
		inVar.put("i_order_num","1234"); // 주문 번호
		inVar.put("i_order_seq","1"); // 주문 순번
		inVar.put("v_except_yn","N"); // 제외 여부
		inVar.put("i_msg_cd","25"); // 메세지 코드
		inVar.put("i_note_1","11"); // 비고1
		inVar.put("i_bttn_title","나이스시스템으로 이동"); // 버튼 제목
		inVar.put("i_bttn_url","test.com"); // 버튼 URL\
		inVar.put("i_lsc_cd","20");
		inVar.put("i_cust_num","54321");
		inVar.put("i_recvr_tel","321");
		//inVar.put("v_sms_conv_yn","N"); // SMS 발송 전환 여부

		System.out.println("");
		System.out.println("★☆★☆★☆★☆ Cobtroller호출 성공(알림톡)★☆★☆★☆★☆");
		System.out.println("-------------------------------------------------------------------------");
		System.out.println(inVar);
		
		String resultMap = null;
        
//        for (int i = 0; i < list.size(); i++) {
//            Map<String, Object> map = list.get(i);
//            // map에 담긴 data를 꺼내어 변경 후 변수 result에 저장
//            result = map.get("v_swtr_lsc_cd").toString();
//            resultMap.put("v_swtr_lsc_cd","20");
//        }
//        
//        return inVar;

		
		alimTalkService.NsAlimTalkCommMethod(inVar, outVar);
	}
	
	/**
	 * SMS DATA Insert Test를 위한 메소드
	 * @param 
	 * @return Map(smsInVar, smsOutVar)
	 * @throws Exception
	 */
	@RequestMapping(value ="/backend/lo/SVLO0902010/smsInsert.do")
	public void smsInsert() throws Exception{
			
		Map<String, Object> smsInVar 		= new HashMap<String, Object>();
		Map<String, Object> smsOutVar 		= new HashMap<String, Object>();
		smsOutVar = null;

		smsInVar.put("v_recvr_tel","01013132424"); // 수신자 전화번호
		smsInVar.put("i_send_to_tel", "01031314242"); // 발신자 전화번호
		smsInVar.put("i_msg_cd", "1"); // 메세지 코드
		smsInVar.put("i_vndr_cd", "2"); // 협력사 코드
		smsInVar.put("i_goods_cd", "3"); // 상품 코드
		smsInVar.put("i_cust_cmpln_num", ""); // 고객 불만 번호
		smsInVar.put("v_except_yn", "N"); // 제외 여부
		smsInVar.put("v_except_desc", "test"); // 제외 사유
		smsInVar.put("v_send_msg", "메세지 잘 가는지 TEST"); // 메세지 내용
		smsInVar.put("i_sms_kind_cd", "S");
		smsInVar.put("i_lsc_cd","20");
		smsInVar.put("i_cust_num","54321");
		smsInVar.put("i_recvr_tel","321");
		//smsInVar.put("i_team_cd", ""); // TEAM_CD
		//smsInVar.put("i_user_id", ""); // USER_ID
		//smsInVar.put("i_lms_title", ""); 
		//smsInVar.put("i_sms_msg", ""); // LMS_MSG
		//inVar.put("v_sms_conv_yn","Y"); // SMS 발송 전환 여부
		
		System.out.println("");
		System.out.println("★☆★☆★☆★☆ Cobtroller호출 성공(SMS)★☆★☆★☆★☆");
		System.out.println("-------------------------------------------------------------------------");
		System.out.println(smsInVar);
		
		alimTalkService.NsSmsCommMethod(smsInVar, smsOutVar);	
	}
	
	
	/**
	 * 조회
	 * @param xpDto
	 * @param model
	 * @return ModelAndView
	 * @throws Exception
	 */
	/*@RequestMapping(value = "/backend/lo/SVLO0902010/doSelectList.do")
	public ModelAndView doSelect(XPlatformMapDTO xpDto, Model model)
	        throws Exception {
		
		ModelAndView mav = new ModelAndView("xplatformMapView");
		
		DataSetMap tranInfo 		        = xpDto.getTranInfoMap();
		Map <String, Object> inVar 			= xpDto.getInVariableMap();
		Map <String, DataSetMap> inDataset 	= xpDto.getInDataSetMap();
		Map <String, Object> outVar 		= xpDto.getOutVariableMap();
		Map <String, DataSetMap> outDataset = xpDto.getOutDataSetMap();
		
		SVLO0902010Service.doSelectService(tranInfo,inVar, inDataset, outVar, outDataset);
		
		mav.addObject(XPlatformConstant.OUT_VARIABLES_ATT_NAME, xpDto.getOutVariableMap());
		mav.addObject(XPlatformConstant.OUT_DATASET_ATT_NAME, 	xpDto.getOutDataSetMap());
		
		mav.addObject(XPlatformConstant.ERROR_CODE, "0");
		mav.addObject(XPlatformConstant.ERROR_MSG, "");
		
		return mav;
	}*/
	
	
	
}
