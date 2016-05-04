package org.goffee.cxf.client.interceptor;

import org.apache.cxf.jaxws.interceptors.HolderOutInterceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.util.Assert;


/**
 * 获取body参数
 */
public class AuthInterceptor extends AbstractPhaseInterceptor<Message> {
	
	public AuthInterceptor() {
		super(Phase.PRE_LOGICAL);
		addBefore(HolderOutInterceptor.class.getName());
	}

	public void handleMessage(Message message) {
		
		MessageContentsList outObjects = MessageContentsList.getContentsList(message);
        Assert.isTrue(outObjects.size() == 1);
        Object outObject = outObjects.get(0);
        System.out.println(outObject);
        // object is our soap response
	}
}
