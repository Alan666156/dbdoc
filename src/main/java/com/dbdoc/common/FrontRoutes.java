package com.dbdoc.common;

import com.dbdoc.configure.ConfigureController;
import com.dbdoc.index.IndexController;
import com.jfinal.config.Routes;
/**
 * 前端路由配置
 * @author fhx
 * @date 2019年3月26日
 */
public class FrontRoutes extends Routes {

	@Override
	public void config() {
		setBaseViewPath("/WEB-INF/view");
		add("/", IndexController.class, "/index");
		add("/configure", ConfigureController.class);
	}
}
