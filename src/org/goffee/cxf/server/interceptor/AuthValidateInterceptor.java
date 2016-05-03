package org.goffee.cxf.server.interceptor;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 服务端输入拦截器
 * 拦截请求有没有授权信息
 */
public class AuthValidateInterceptor extends AbstractPhaseInterceptor<SoapMessage> {
    public AuthValidateInterceptor(){
        super(Phase.PRE_INVOKE);
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
    	
    	System.out.println("message: " + message);
    	
        List<Header> headers = message.getHeaders();
        if(headers == null || headers.size() < 1) {  
            throw new Fault(new Exception("header is null or header size < 1"));
        }

        Element auth = null;
        //获取授权信息元素
        for(Header header : headers){
            QName qname = header.getName();
            String ns = qname.getNamespaceURI();
            String tagName = qname.getLocalPart();
            if(ns != null && ns.equals("http://www.tmp.com/auth") && tagName != null && tagName.equals("auth")){
                auth = (Element)header.getObject();
                break;
            }
        }

        //如果授权信息元素不存在，提示错误
        if(auth == null){
            throw new Fault(new Exception("auth is null"));
        }

        NodeList nameList = auth.getElementsByTagName("name");
        NodeList pwdList = auth.getElementsByTagName("password");
        if(nameList.getLength() != 1 || pwdList.getLength() != 1){
            throw new Fault(new Exception("empty params"));
        }

        String name = nameList.item(0).getTextContent();
        String password = pwdList.item(0).getTextContent();
        if(!"admin".equals(name) || !"admin".equals(password)){
            throw new Fault(new Exception("validate error"));
        }
    }
}
