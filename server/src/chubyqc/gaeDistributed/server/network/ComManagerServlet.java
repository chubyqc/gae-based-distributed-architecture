package chubyqc.gaeDistributed.server.network;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chubyqc.gaeDistributed.server.Logger;

public class ComManagerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private void doProcess(HttpServletRequest req) {
		try {
			ComManager.getInstance().receive(req.getInputStream(), req.getRemoteAddr());
		} catch (Exception e) {
			Logger.getInstance().error(e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doProcess(req);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doProcess(req);
	}
}
