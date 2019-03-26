package com.dbdoc.common.model;

import javax.sql.DataSource;

import com.jfinal.plugin.activerecord.generator.MetaBuilder;

public class DocMetaBuilder extends MetaBuilder {

	// 根据前缀 忽略表
	protected String[] ignoreTableNamePrefixes = null;

	public DocMetaBuilder(DataSource dataSource) {
		super(dataSource);
	}

	public DocMetaBuilder(DataSource dataSource, String[] ignoreTableNamePrefixes) {
		super(dataSource);
		this.ignoreTableNamePrefixes = ignoreTableNamePrefixes;
	}

	@Override
	protected boolean isSkipTable(String tableName) {
		if (null != ignoreTableNamePrefixes) {
			for (String prefix : ignoreTableNamePrefixes) {
				if (tableName.startsWith(prefix))
					return true;
			}
		}
		return false;
	}

	public String[] getIgnoreTableNamePrefixes() {
		return ignoreTableNamePrefixes;
	}

	public void setIgnoreTableNamePrefixes(String[] ignoreTableNamePrefixes) {
		this.ignoreTableNamePrefixes = ignoreTableNamePrefixes;
	}
}
