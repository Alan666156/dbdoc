package com.dbdoc.configure;

import com.dbdoc.common.controller.BaseController;
import com.dbdoc.common.model.Configure;
import com.dbdoc.parsedb.ParseDbService;
import com.jfinal.aop.Before;
import com.jfinal.kit.Ret;
/**
 * 数据库连接增删改操作入口
 * @author fhx
 * @date 2019年3月26日
 */
public class ConfigureController extends BaseController {

	public void add() {
	}

	@Before(ConfigureValidator.class)
	public void save() {
		Configure model = getModel(Configure.class, "");
		boolean flag = model.save();
		if (flag){
			renderJson(Ret.ok());
		} else{
			renderJson(Ret.fail("error", "失败"));
		}
	}

	public void edit() {
		setAttr("model", ConfigureService.service.findById(getParaToInt()));
	}

	@Before(ConfigureValidator.class)
	public void update() {
		Configure model = getModel(Configure.class, "");
		boolean flag = model.update();
		if (flag){
			renderJson(Ret.ok());
		} else{
			renderJson(Ret.fail());
		}
	}

	public void delete() {
		boolean flag = ConfigureService.service.delete(getParaToInt());
		if (flag){
			renderJson(Ret.ok());
		} else{
			renderJson(Ret.fail());
		}
	}
	
	@Before(ConfigureValidator.class)
	public void testConn() {
		Configure model = getModel(Configure.class, "");
		boolean flag = ParseDbService.service.validateConnect(model);
		if (flag){
			renderJson(Ret.ok());
		} else{
			renderJson(Ret.fail());
		}
	}
}
