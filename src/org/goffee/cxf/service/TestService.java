package org.goffee.cxf.service;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface TestService {

	String sayHello(@WebParam(name = "who") String who);
	
	String moreValues(String a, String b);
}
