package com.nsmall.backend.logistics.comm;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import com.nsmall.fw.service.NSITAbstractServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Class NsAlimTalkServiceImpl.java
 * @Description 1) 알림톡, SMS 공통 프로시저(SO_COMM_ALIM_MSG_TRANS)의 변수선언, Exception 처리기능을 구현
 * 				2) data set: Map으로 받아온 inVar를 DAO에 전달, SELECT의 결과값은 outVar로 받아온다.
 * 							 고정된 값, 조건에 따라 달라지는 값들은 NsAlimTalk.xml의 로직으로 처리한다.
 * @Modification Information
 * 
 * @author ehdgus0896
 * @since
 * @version 1.0
 */
@Service("NsAlimTalkService")
public class NsAlimTalkServiceImpl extends NSITAbstractServiceImpl implements
		NsAlimTalkService {
	
	@Resource(name="NsAlimTalkDAO")
	private NsAlimTalkDAO NsAlimTalkDAO;
	
	/* 클래스 전역 변수 선언 */
	String	 i_recvr_tel
			 ,i_send_to_tel
			 ,i_msg
			 ,i_sms_msg
			 ,i_msg_cd="1"
			 ,i_tplt_id
			 ,i_sms_kind_cd
			 ,i_sms_limit_yn
			 ,i_lms_title
			 ,i_lsc_cd
			 ,i_wbl_num
			 ,i_resv_send_dttm
			 ,i_vndr_cd
			 ,i_goods_cd
			 ,i_cust_num ="1212"
			 ,i_order_num
			 ,i_order_seq
			 ,i_except_yn
			 ,i_team_cd
			 ,i_camp_id
			 ,i_cust_cmpln_num
			 ,i_bttn_title
			 ,i_bttn_url
			 ,i_note_1
			 ,i_note_2
			 ,i_note_3
			 ,i_note_4
			 ,i_note_5
			 ,i_note_6
			 ,i_note_7
			 ,i_note_8
			 ,i_note_9
			 ,i_note_10
			 ,i_user_id
			 ,o_rtn_code
			 ,o_rtn_msg;

	/* method 내부 로직 변수 */
	String	 v_msg_id
			 ,v_msg_seq
			 ,v_send_msg
			 ,v_temp_msg
			 ,v_recvr_tel
			 ,v_only_sms
			 ,v_except_yn="Y"
			 ,v_except_desc
			 ,v_swtr_lsc_cd
			 ,v_sms_use_cnt;
	 String v_sms_conv_yn ;
	 Object obLsc;
	 
	public void NsAlimTalkCommMethod(Map<String, Object> inVar, Map<String, Object> outVar)throws Exception {
		
		System.out.println("");
		System.out.println("★☆★☆★☆★☆ 알림톡 구현 메소드호출 성공★☆★☆★☆★☆");
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("");
		System.out.println("★☆★☆★☆★☆ Data  수신 확인 ★☆★☆★☆★☆");
		System.out.println(inVar);
		System.out.println(" ");
		System.out.println("outVar(SELECT문의 결과값을 받아오기 위한 변수, 초깃값 선언 null) : " +outVar);
		System.out.println("-------------------------------------------------------------------------");
		
		System.out.println("테스트용 전송 핸드폰 번호 확인: "+inVar.get("i_recvr_tel"));
		if (inVar.get("i_recvr_tel").equals("") || inVar.get("i_recvr_tel") == null) {
			
			o_rtn_code = "-1";
			o_rtn_msg = "수신전화번호없음";
			System.out.println("-------------------------------------------------------------------------");
			System.out.println("[수신 번호 없을 때 리턴]");
			System.out.println("리턴코드: " + o_rtn_code);
			System.out.println("리턴메세지 :" + o_rtn_msg);
		}
		
		/* 고객의 동의 여부 확인 제외 - 일시 승인 건 */
		if (i_except_yn == "Y") {
			v_except_yn = "N";
			v_except_desc = null;
		} else {
			
			System.out.println(" ");	
			/* SMS 미사용 고객 SMS 전송 제외 */
			if (i_cust_num == "0") {
				
				System.out.println("[고객번호 없음]");
				v_except_yn = "N";
				v_except_desc = null;
			} else {
				
				System.out.println("[고객번호 확인 완료]");
				/* SMS 미사용 고객 SMS 전송 제외 */
				System.out.println("===========[SMS 미사용 고객 전송 제외를 위한 값 추출 확인]===========");
				
				List<Map> resultCount = NsAlimTalkDAO.smsUseCount(inVar);
				Map paramMap = (Map) resultCount.get(0);		
				String MapToStr = (String) paramMap.get("cnt").toString();				
				
				int cntVar = Integer.parseInt(MapToStr);
				v_sms_use_cnt = MapToStr;
				
				System.out.println("========================================================");
				System.out.println("List형식: "+resultCount);
				System.out.println("List -> Map");
				System.out.println("Map형식: "+ paramMap);
				System.out.println("Map -> String");
				System.out.println("String형식:" + MapToStr);
				System.out.println("========================================================");
			
					if (cntVar > 0) {
						v_except_yn = "N";
						v_except_desc = null;
						System.out.println(v_except_yn);
						System.out.println(v_except_desc);
					} else {
						v_except_yn = "N";
						v_except_desc = "NO-USE";
						System.out.println(v_except_yn);
						System.out.println(v_except_desc);
					}
			}
		}
		System.out.println("==============================================");
		System.out.println("택배사 코드 확인: " + inVar.get("i_lsc_cd"));
	
		/* 스윗트래커 택배사 코드 변경 */
		if (inVar.get("i_lsc_cd") != null) {		
			
	
			List<Map> lscCd = NsAlimTalkDAO.SwtrLscCd(inVar);
			Map paramMap2 = (Map) lscCd.get(0);
			String MapToStr2 =(String) paramMap2.get("cdNote5").toString();
			
			System.out.println("========================================================");
			System.out.println("List형식: "+lscCd);
			System.out.println("List -> Map");
			System.out.println("Map형식: "+ paramMap2);
			System.out.println("Map -> String");
			System.out.println("String형식:" + MapToStr2);
			System.out.println("========================================================");
			
			v_swtr_lsc_cd = MapToStr2;
			System.out.println("스윗트래커 택배사 코드로 변경 되었는지 확인: "+v_swtr_lsc_cd);

			} else {
				System.out.println("값이 null값입니다.");
				v_swtr_lsc_cd = null;
			}
	
			inVar.put("v_swtr_lsc_cd", v_swtr_lsc_cd);
			System.out.println("스윗트래커 택배사 코드 들어갔는지 check");
			System.out.println(inVar);
			System.out.println("========================================================");
		
		/* SMS 전환 여부를 반환 */
			List<Map> conYN = NsAlimTalkDAO.smsConvYN(outVar);
			Map paramMap3 = (Map) conYN.get(0);
			String MapToStr3 = (String) paramMap3.get("commCdInstc").toString();
			v_sms_conv_yn = MapToStr3;

			System.out.println("========================================================");
			System.out.println("list형식: "+conYN);
			System.out.println("List -> Map");
			System.out.println("Map형식: "+ paramMap3);
			System.out.println("Map -> String");
			System.out.println("String형식:" + MapToStr3);
			System.out.println("========================================================");
			
			inVar.put("v_sms_conv_yn",v_sms_conv_yn);
			System.out.println("SMS전환 여부 데이터 들어갔는지 확인");
			System.out.println(inVar);
			System.out.println("========================================================");
		try{
			if(conYN==null){
				System.out.println("Null입니다.");
			}
			//String MapToStr3 = paramMap3.get("conYN").toString();
		} catch (Exception e) {
			v_sms_conv_yn = "N";
		}finally{
			v_sms_conv_yn = "N";
		}	
		v_recvr_tel = i_recvr_tel;

		if ((i_msg_cd.equals("2") && i_msg.contains("9999-9999-9999"))
				|| (i_msg_cd.equals("20") && i_msg.contains("0원"))) {
			v_except_yn = "Y";
		}
		
		System.out.println("v_sms_conv_yn: "+ MapToStr3);
		
		if(v_sms_conv_yn=="N"){
			
			/*AlimTalk: Controller에서 받아온 Map 변수 inVar를 DAO에 전달.*/
				System.out.println("");
				System.out.println("알림톡 [ServiceImpl -> DAO 데이터 송신]");
				System.out.println("");
				
				NsAlimTalkDAO.commAlimTrmsInf(inVar);
		}else{
			
			System.out.println("SMS발송이 아닙니다.");
		
		}
			/*SMS*/
			try {
				//NsAlimTalkDAO.commSmsTrmsInf(inVar);
			} catch (Exception e) {
				o_rtn_code = "-2";
				o_rtn_msg = "SMS전송 에러발생 !";
			}
	
	}
	public void NsSmsCommMethod(Map<String, Object> smsInVar, Map<String, Object> smsOutVar)throws Exception {
		
		System.out.println("");
		System.out.println("★☆★☆★☆★☆ SMS 구현 메소드호출 성공★☆★☆★☆★☆");
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("");
		System.out.println("★☆★☆★☆★☆ Data  수신 확인 ★☆★☆★☆★☆");
		System.out.println(smsInVar);
		System.out.println(" ");
		System.out.println("outVar(SELECT문의 결과값을 받아오기 위한 변수, 초깃값 선언 null) : " +smsOutVar);
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("테스트용 전송 핸드폰 번호 확인: "+smsInVar.get("i_recvr_tel"));
		
		if (smsInVar.get("i_recvr_tel").equals("") || smsInVar.get("i_recvr_tel") == null) {		
			o_rtn_code = "-1";
			o_rtn_msg = "수신전화번호없음";
			System.out.println("-------------------------------------------------------------------------");
			System.out.println("[수신 번호 없을 때 리턴]");
			System.out.println("리턴코드: " + o_rtn_code);
			System.out.println("리턴메세지 :" + o_rtn_msg);
		}
		
		/* 고객의 동의 여부 확인 제외 - 일시 승인 건 */
		if (i_except_yn == "Y") {
			v_except_yn = "N";
			v_except_desc = null;
		} else {
			
			System.out.println(" ");	
			/* SMS 미사용 고객 SMS 전송 제외 */
			if (i_cust_num == "0") {
				
				System.out.println("[고객번호 없음]");
				v_except_yn = "N";
				v_except_desc = null;
			} else {
				
				System.out.println("[고객번호 확인]");
				/* SMS 미사용 고객 SMS 전송 제외 */
				System.out.println("===========[SMS 미사용 고객 전송 제외를 위한 값 추출 확인]===========");
				
				List<Map> resultCount = NsAlimTalkDAO.smsUseCount(smsInVar);
				Map paramMap = (Map) resultCount.get(0);		
				String MapToStr = (String) paramMap.get("cnt").toString();				
				
				int cntVar = Integer.parseInt(MapToStr);
				v_sms_use_cnt = MapToStr;
				
				System.out.println("========================================================");
				System.out.println("List형식: "+resultCount);
				System.out.println("List -> Map");
				System.out.println("Map형식: "+ paramMap);
				System.out.println("Map -> String");
				System.out.println("String형식:" + MapToStr);
				System.out.println("========================================================");
			
					if (cntVar > 0) {
						v_except_yn = "N";
						v_except_desc = null;
						System.out.println(v_except_yn);
						System.out.println(v_except_desc);
					} else {
						v_except_yn = "N";
						v_except_desc = "NO-USE";
						System.out.println(v_except_yn);
						System.out.println(v_except_desc);
					}
			}
		}
		System.out.println("==============================================");
		System.out.println("택배사 코드 가져오기: " + smsInVar.get("i_lsc_cd"));
	
		/* 스윗트래커 택배사 코드 변경 */
		if (smsInVar.get("i_lsc_cd") != null) {		
			
	
			List<Map> lscCd = NsAlimTalkDAO.SwtrLscCd(smsInVar);
			Map paramMap2 = (Map) lscCd.get(0);
			String MapToStr2 =(String) paramMap2.get("cdNote5").toString();
			
			System.out.println("========================================================");
			System.out.println("List형식: "+lscCd);
			System.out.println("List -> Map");
			System.out.println("Map형식: "+ paramMap2);
			System.out.println("Map -> String");
			System.out.println("String형식:" + MapToStr2);
			System.out.println("========================================================");
			
			v_swtr_lsc_cd = MapToStr2;
			System.out.println("스윗트래커 택배사 코드로 변경 되었는지 확인: "+v_swtr_lsc_cd);

			} else {
				System.out.println("값이 null값입니다.");
				v_swtr_lsc_cd = null;
			}
	
			System.out.println("저장 확인2: "+v_swtr_lsc_cd);
			smsInVar.put("v_swtr_lsc_cd", v_swtr_lsc_cd);
			System.out.println("스윗트래커 택배사 코드 들어갔는지 check");
			System.out.println(smsInVar);
		
		/* SMS 전환 여부를 반환 */
			List<Map> conYN = NsAlimTalkDAO.smsConvYN(smsOutVar);
			Map paramMap3 = (Map) conYN.get(0);
			String MapToStr3 = (String) paramMap3.get("commCdInstc").toString();
			v_sms_conv_yn = MapToStr3;
			System.out.println("========================================================");
			System.out.println("list형식: "+conYN);
			System.out.println("List -> Map");
			System.out.println("Map형식: "+ paramMap3);
			System.out.println("Map -> String");
			System.out.println("String형식:" + MapToStr3);
			System.out.println("========================================================");
			
			smsInVar.put("v_sms_conv_yn",v_sms_conv_yn);
			System.out.println("SMS전환 여부 데이터 들어갔는지 확인");
			System.out.println(smsInVar);
			System.out.println("========================================================");
			
		try{
			if(conYN==null){
				System.out.println("Null입니다.");
			}
			//String MapToStr3 = paramMap3.get("conYN").toString();
		} catch (Exception e) {
			v_sms_conv_yn = "N";
		}finally{
			v_sms_conv_yn = "N";
		}	
		v_recvr_tel = i_recvr_tel;

		if ((i_msg_cd.equals("2") && i_msg.contains("9999-9999-9999"))
				|| (i_msg_cd.equals("20") && i_msg.contains("0원"))) {
			v_except_yn = "Y";
		}
		
		System.out.println("v_sms_conv_yn: "+ MapToStr3);

		if(v_sms_conv_yn=="Y"){

			System.out.println("");
			System.out.println("SMS [ServiceImpl -> DAO 데이터 송신]");
			System.out.println("");
			
			NsAlimTalkDAO.commSmsTrmsInf(smsInVar);

		}else{
			System.out.println("알림톡이 아닙니다.");
		}
			/*SMS*/
			try {
				//NsAlimTalkDAO.commSmsTrmsInf(inVar);
			} catch (Exception e) {
				o_rtn_code = "-2";
				o_rtn_msg = "SMS전송 에러발생 !";
			}
	
	}

}	


