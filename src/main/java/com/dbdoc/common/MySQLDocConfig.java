package com.dbdoc.common;

import com.dbdoc.common.handler.GlobalbHandler;
import com.dbdoc.common.model._MappingKit;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.json.MixedJsonFactory;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.dialect.Sqlite3Dialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.template.Engine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 系统配置
 * 应用启动入口
 * @author fhx
 *
 */
public class MySQLDocConfig extends JFinalConfig {
	private static Logger logger = LoggerFactory.getLogger(MySQLDocConfig.class);
	private static Prop config = PropKit.use("config.ini");
	private static Prop p = loadConfig();
	private static Prop loadConfig() {
		Prop m = null;
		try {
			// 优先加载生产环境配置文件
//			if("true".equals(c.get("devMode"))){
//				m=PropKit.use("db_config_dev.properties");
//			}else{
//				m=PropKit.use("db_config_pro.properties");
//			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return m;
	}

	@Override
	public void configConstant(Constants me) {
		me.setDevMode(config.getBoolean("devMode", false));
		me.setJsonFactory(MixedJsonFactory.me());
	}

	@Override
	public void configEngine(Engine me) {
		if("true".equals(config.get("devMode"))){
			me.setDevMode(true);
		}else{
			me.setDevMode(false);
		}

		me.addSharedFunction("/WEB-INF/view/common/__layout.html");
	}

	@Override
	public void configHandler(Handlers me) {
		me.add(new ContextPathHandler("ctx"));
		me.add(new GlobalbHandler());
	}

	@Override
	public void configInterceptor(Interceptors me) {
	}

	@Override
	public void configPlugin(Plugins me) {
		DruidPlugin druidPlugin = getDruidPlugin();
		me.add(druidPlugin);

		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		arp.setDialect(new Sqlite3Dialect());
		if (config.getBoolean("devMode", false)) {
			arp.setShowSql(true);
		}
		me.add(arp);
		_MappingKit.mapping(arp);
		RedisPlugin redisPlugin = new RedisPlugin("myredis","172.16.230.132");
		me.add(redisPlugin);
	}

	@Override
	public void configRoute(Routes me) {
		me.add(new FrontRoutes());
	}

	public static DruidPlugin getDruidPlugin() {
		String jdbcUrl = null;
		String os = System.getProperties().getProperty("os.name");
		if (os != null && os.toLowerCase().indexOf("linux") > -1){
			logger.info("===========当前系统环境linux===========");
			jdbcUrl ="jdbc:sqlite:///data/db/doc.db";
		}else {
			logger.info("===========当前系统环境windows===========");
//			jdbcUrl ="jdbc:sqlite://E:/tools/dbdoc/db/doc.db";
//			URL url = ClassLoader.getSystemResource("doc.db");
//			jdbcUrl = "jdbc:sqlite://" + url.getPath();
		}
		logger.info("当前URL：", Thread.currentThread().getContextClassLoader().getResource("doc.db").getPath());
		jdbcUrl = "jdbc:sqlite://" + Thread.currentThread().getContextClassLoader().getResource("doc.db").getPath();
		return new DruidPlugin(jdbcUrl, "1", "1");
	}

	/**
	 * 启动入口，运行此 main 方法可以启动项目
	 */
	public static void main(String[] args) {
		
		/**
		 * 特别注意：Eclipse 之下建议的启动方式
		 */
		JFinal.start("src/main/webapp", 8887, "/");

		/*String driver = "oracle.jdbc.OracleDriver";
		//String url = "jdbc:oracle:thin:@140.206.216.47:16021:kftpp";
		String url="jdbc:oracle:thin:@//140.206.216.47:16021/kftpp";
		String user = "tpp";
		String password = "TPP#kf#7knw6TB839";
		Connection con = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		boolean flag = false;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			String sql = "select * from TPP_TRADE_T_COLLECT_INFO";
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()){
				System.out.println(rs.getString("PAYER_NAME"));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
	}
}