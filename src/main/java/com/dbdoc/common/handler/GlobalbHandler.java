package com.dbdoc.common.handler;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;
import com.jfinal.kit.PropKit;

public class GlobalbHandler extends Handler {

	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		if (PropKit.getBoolean("devMode", false)) {
			request.setAttribute("version", UUID.randomUUID().toString().replaceAll("-", ""));
		} else
			request.setAttribute("version", PropKit.get("version", "0.0"));
		next.handle(target, request, response, isHandled);
	}
}
