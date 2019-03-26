package com.dbdoc.configure;

import java.util.List;

import com.dbdoc.common.model.Configure;

/**
 * 数据库连接配置
 * 
 */
public class ConfigureService {
	public static final ConfigureService service = new ConfigureService();
	private final Configure dao = new Configure();

	public List<Configure> list() {
		List<Configure> configures = dao.find("select * from configure");
		return configures;

	}

	public Configure findById(Integer id) {
		return dao.findById(id);
	}
	
	public boolean delete(Integer id){
		return dao.deleteById(id);
	}
	
	
}
