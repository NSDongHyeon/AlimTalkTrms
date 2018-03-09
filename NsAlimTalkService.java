package com.nsmall.backend.logistics.comm;

import java.util.Map;

/**
 * @Class NSAlimTalkService.java
 * @Description NsAlimTalkServiceImpl.java의 인터페이스
 * @Modification Information
 * 
 * @author ehdgus0896
 * @since
 * @version 1.0
 */

public interface NsAlimTalkService {

	void NsAlimTalkCommMethod(Map<String, Object> inVar,Map<String, Object> outVar) throws Exception;
	
	void NsSmsCommMethod(Map<String, Object> smsInVar, Map<String, Object> smsOutVar) throws Exception;
	
}