package chubyqc.gaeDistributed.server.network;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chubyqc.gaeDistributed.server.Logger;
import chubyqc.gaeDistributed.server.Session;

public class ComManagerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private void doProcess(HttpServletRequest req, HttpServletResponse resp) {
		try {
			String content = getContent(req, resp);
			System.err.println("receive " + content);
			ComManager.getInstance().receive(content, req.getRemoteAddr(),
					new Session(req.getSession()));
		} catch (Exception e) {
			Logger.getInstance().error(e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doProcess(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doProcess(req, resp);
	}
	
	private String getContent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.getSession();
		ComManager com = ComManager.getInstance();
		String content = com.read(req.getInputStream());
		resp.getOutputStream().close();
		return content;
	}
}
