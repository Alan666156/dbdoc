package com.dbdoc.configure;

import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.validate.Validator;

public class ConfigureValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		validateString("name", 1, 20, "error", "连接名 数据范围[1～20]");
		validateString("ip", 1, 20, "error", "主机名或IP地址 数据范围[1～30]");
		validateString("username", 1, 20, "error", "用户名 数据范围[1～15]");
		validateString("password", 1, 20, "error", "密码数据 范围[1～32]");
		validateInteger("port", 1000, 999999, "error", "端口号 越界[1000～999999]");
	}

	@Override
	protected void handleError(Controller c) {
		c.renderJson(Ret.fail("error", c.getAttr("error")));
	}
}
