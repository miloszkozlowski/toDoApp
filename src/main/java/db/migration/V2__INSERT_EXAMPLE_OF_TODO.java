package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

public class V2__INSERT_EXAMPLE_OF_TODO extends BaseJavaMigration{

	@Override
	public void migrate(Context context) throws Exception {
		new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true))
		.execute("insert into tasks(description, done) values('Testowe zadanie z migracji', false)");
		
	}
	

}
