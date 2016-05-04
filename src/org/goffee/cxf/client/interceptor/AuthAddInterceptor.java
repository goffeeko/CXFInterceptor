package org.goffee.cxf.client.interceptor;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.goffee.cxf.util.Utils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 添加授权拦截器 用于在客户端发请求时添加授权
 * <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
 * 	<soap:Header>
 * 		<auth xmlns="http://cxf.goffee.org/auth">
 * 			<checksum>CHECKSUMgoffee</checksum>
 * 		</auth>
 * 	</soap:Header>
 * 	<soap:Body>
 * 		<ns2:sayHello xmlns:ns2="http://service.cxf.goffee.org/">
 * 			<who>goffee</who>
 * 		</ns2:sayHello>
 * 	</soap:Body>
 * </soap:Envelope>
 * 
 */
public class AuthAddInterceptor extends AbstractPhaseInterceptor<SoapMessage> {
	public AuthAddInterceptor() {
		// 准备发送阶段
//		super(Phase.PREPARE_SEND); //load org.goffee.cxf.service.jaxws_asm.SayHello@7178b64b
		super(Phase.PRE_LOGICAL); //load value: goffee
		
//		addAfter(SAAJInInterceptor.class.getName());
//		addBefore(HolderOutInterceptor.class.getName());
	}

	@Override
	public void handleMessage(SoapMessage message) throws Fault {
		System.out.println(message.getInterceptorChain());

		MessageContentsList contentsList = MessageContentsList.getContentsList(message);

		//print body value
		if (contentsList.size() > 0) {
			for (int i = 0; i < contentsList.size(); i++) {
				System.out.println(contentsList.get(i));
			}
		} else {
			System.out.println("contentsList size < 1");
		}

		// add to header
		List<Header> headers = message.getHeaders();

		Document doc = DOMUtils.createDocument();
		Element auth = doc.createElementNS("http://cxf.goffee.org/auth", "auth");
		Element checksum = doc.createElement("checksum");
		checksum.setTextContent(Utils.checksum(contentsList));
		auth.appendChild(checksum);
		headers.add(new Header(new QName(""), auth));
	}
}
