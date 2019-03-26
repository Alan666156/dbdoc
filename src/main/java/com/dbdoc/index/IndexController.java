package com.dbdoc.index;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dbdoc.common.controller.BaseController;
import com.dbdoc.common.model.Configure;
import com.dbdoc.configure.ConfigureService;
import com.dbdoc.parsedb.ParseDbService;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
/**
 * 首页请求
 * @author fhx
 * @date 2019年3月26日
 */
public class IndexController extends BaseController {
	
	public Logger logger = Logger.getLogger(this.getClass());
	
	public void index() {
		List<Configure> list = ConfigureService.service.list();
		// 所有链接信息
		setAttr("links", list);
		// 当前链接
		Configure config = null;
		Integer id = getParaToInt(0);
		if (null != id) {
			for (Configure c : list)
				if (c.getId().equals(id)){
					config = c;
				}
		} else if (null != list && list.size() > 0){
			config = list.get(0);
		}
		setAttr("config", config);

		// 数据库信息
		List<Record> dbs = null;
		List<Record> temp = new ArrayList<Record>();
		if (null != config && config.getDbtype() == 0) {
			dbs = ParseDbService.service.listDatabases(config);
			for(Record record : dbs) {
				String name = record.getStr("NAME");
				if(name.equals("information_schema") || name.equals("performance_schema") || name.equals("mysql")){
					
				}else {
					temp.add(record);
				}
			}
			setAttr("databases", temp);
		}
		// 表结构信息
		String tableName;
		if (null != temp && temp.size() > 0 && config.getDbtype()==0) {
			tableName = getPara(1);
			if (StrKit.isBlank(tableName)) {
				tableName = temp.get(0).getStr("name");
			}
			List<Record> tables = ParseDbService.service.listTable(config, tableName);
			setAttr("tables", tables);
			setAttr("tableName", tableName);
		} else if(config.getDbtype()==1){
			tableName = getPara(0);
			List<Record> tables = ParseDbService.service.listTable(config, tableName);
			setAttr("tables", tables);
			setAttr("tableName", tableName);
		}
	}


}
