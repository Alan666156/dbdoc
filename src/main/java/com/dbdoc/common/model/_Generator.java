
package com.dbdoc.common.model;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.druid.DruidPlugin;
import com.dbdoc.common.MySQLDocConfig;

import javax.sql.DataSource;

/**
 * Model、BaseModel、_MappingKit 生成器
 */
public class _Generator {

	/**
	 * 重用 JFinalClubConfig 中的数据源配置，避免冗余配置
	 */
	public static DataSource getDataSource() {
		DruidPlugin druidPlugin = MySQLDocConfig.getDruidPlugin();
		druidPlugin.start();
		return druidPlugin.getDataSource();
	}

	public static void main(String[] args) {
		// base model 所使用的包名
		String baseModelPackageName = "com.dbdoc.common.model.base";
		// base model 文件保存路径
		String baseModelOutputDir = PathKit.getWebRootPath() + "/src/main/java/com/dbdoc/common/model/base";

		System.out.println("输出路径：" + baseModelOutputDir);

		// model 所使用的包名 (MappingKit 默认使用的包名)
		String modelPackageName = "com.dbdoc.common.model";
		// model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
		String modelOutputDir = baseModelOutputDir + "/..";

		// 创建生成器
		Generator gen = new Generator(getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName,
				modelOutputDir);
		gen.setMetaBuilder(new DocMetaBuilder(getDataSource(), new String[] { "_config" }));
		gen.setMetaBuilder(new DocMetaBuilder(getDataSource(), new String[] { "_direct" }));
		// 设置数据库方言
		gen.setDialect(new MysqlDialect());
		// 添加不需要生成的表名
		gen.addExcludedTable("sqlite_sequence");
		// 设置是否在 Model 中生成 dao 对象
		gen.setGenerateDaoInModel(false);
		// 设置是否生成字典文件
		gen.setGenerateDataDictionary(false);
		// 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为
		// "User"而非 OscUser
		// gernerator.setRemovedTableNamePrefixes("t_");
		// 生成
		gen.generate();
	}
}
