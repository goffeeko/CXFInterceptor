package org.goffee.cxf.client;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.goffee.cxf.client.interceptor.AuthAddInterceptor;
import org.goffee.cxf.client.interceptor.AuthInterceptor;
import org.goffee.cxf.service.TestService;

public class Test {

	public static void main(String[] args) {
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(TestService.class);
		factory.setAddress("http://localhost:8080/CXFInterceptor/services/TestService?wsdl");

		factory.getInInterceptors().add(new LoggingInInterceptor());

		//客户端授权拦截器
		factory.getOutInterceptors().add(new AuthAddInterceptor());
//		factory.getOutInterceptors().add(new AuthInterceptor());
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		TestService testService = factory.create(TestService.class);
		String welcome = testService.sayHello("goffee");
		System.out.println(welcome);
	}
}
