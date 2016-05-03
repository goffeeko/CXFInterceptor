package org.goffee.cxf.client.interceptor;

import java.io.IOException;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

import antlr.collections.List;




/**
 * 添加授权拦截器
 * 用于在客户端发请求时添加授权
 * 
 * 
 */
public class AuthInterceptor extends AbstractPhaseInterceptor<SoapMessage> {
    
	private SAAJInInterceptor saajIn = new SAAJInInterceptor();
	
	public AuthInterceptor(){
        //准备发送阶段
    	super(Phase.WRITE);
        //getAfter().add(SAAJInInterceptor.class.getName()); 
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
    	System.out.println(message.getInterceptorChain()); 
    	List list = message.getContent(List.class);
    	System.out.println(list);
    	
    	
    	SOAPMessage doc = getSOAPMessage(message);
    	
    	try {
    		System.out.println("====================================");
			doc.writeTo(System.out);
			SOAPBody body = doc.getSOAPBody();
			System.out.println(body);
			System.out.println("====================================");
			
		} catch (SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private SOAPMessage getSOAPMessage(SoapMessage smsg){

        SOAPMessage soapMessage = smsg.getContent(SOAPMessage.class);

    if (soapMessage == null) {

        saajIn.handleMessage(smsg);

        soapMessage = smsg.getContent(SOAPMessage.class);

    }   

    return soapMessage;

  }
}
