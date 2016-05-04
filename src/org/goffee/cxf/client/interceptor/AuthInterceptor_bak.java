package org.goffee.cxf.client.interceptor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * will show time out error
 * 未知为何写不回入message content
 * 
 */
public class AuthInterceptor_bak extends AbstractPhaseInterceptor<Message> {

	public AuthInterceptor_bak() {
		// 这儿使用pre_stream，意思为在流关闭之前
		super(Phase.PRE_STREAM);
//		super(Phase.WRITE);
	}

	public void handleMessage(Message message) {
			
		try {

			OutputStream os = message.getContent(OutputStream.class);

			CachedStream cs = new CachedStream();

			message.setContent(OutputStream.class, cs);

			message.getInterceptorChain().doIntercept(message);

			CachedOutputStream csnew = (CachedOutputStream) message.getContent(OutputStream.class);
			InputStream in = csnew.getInputStream();

			String xml = IOUtils.toString(in);
			System.out.println(xml);
			
			//String to Dom
			StringReader sr = new StringReader(xml);
			InputSource is = new InputSource(sr);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(is);
			
			// 这里对xml做处理，处理完后同理，写回流中
			IOUtils.copy(new ByteArrayInputStream(xml.getBytes()), os);

			cs.close();
			os.flush();

			message.setContent(OutputStream.class, os);

		} catch (Exception e) {

		}
	}

	private class CachedStream extends CachedOutputStream {

		public CachedStream() {

			super();

		}

		protected void doFlush() throws IOException {

			currentStream.flush();

		}

		protected void doClose() throws IOException {

		}

		protected void onWrite() throws IOException {

		}

	}

}
