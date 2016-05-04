package org.goffee.cxf.service.impl;

import javax.jws.WebService;

import org.goffee.cxf.service.TestService;

@WebService(endpointInterface="org.goffee.cxf.service.TestService", serviceName="TestService")
public class TestServiceImpl implements TestService {

	@Override
	public String sayHello(String who) {
		// TODO Auto-generated method stub
		return "Hello " + who;
	}

	@Override
	public String moreValues(String a, String b) {
		// TODO Auto-generated method stub
		return "Two Values, A:" + a + ", B:" + b;
	}

}
