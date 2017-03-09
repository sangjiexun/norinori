package jp.gr.norinori.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractDatabaseConnection implements DatabaseConnection {
	// メンバ===================================================================
	private Connection connection;
	private String databaseName;
	private String target;
	private Database database;

	private int validTime;

	// コンストラクタ===========================================================
	/**
	 * データベースコネクションフレームのインスタンスを生成する。
	 *
	 * @param connection
	 */
	public AbstractDatabaseConnection(Database database, String target, Connection connection) {
		this.database = database;
		this.connection = connection;
		this.target = target;
	}

	// メソッド=================================================================
	/*
	 * (非 Javadoc)
	 *
	 * @see jp.gr.norinori.database.DatabaseConnection#getDatabaseName()
	 */
	@Override
	public String getDatabaseName() {
		return this.databaseName;
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see jp.gr.norinori.database.DatabaseConnection#setDatabaeName(java.lang.
	 * String )
	 */
	@Override
	public void setDatabaeName(String databaseName) {
		this.databaseName = databaseName;
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see jp.gr.norinori.database.DatabaseConnection#getConnection()
	 */
	@Override
	public Connection getConnection() throws SQLException {
		if (this.connection == null) {
			reconnect();
			if (this.connection == null) {
				throw new SQLException("Reconnection Failure.");
			}
			return this.connection;
		}

		if (this.validTime > 0 && !this.connection.isValid(this.validTime)) {
			reconnect();
			if (this.connection == null) {
				throw new SQLException("Reconnection Failure.");
			}
			if (this.validTime > 0 && !this.connection.isValid(this.validTime)) {
				throw new SQLException("Reconnection Failure.");
			}
		}
		return this.connection;
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see jp.gr.norinori.database.DatabaseConnection#createStatement()
	 */
	@Override
	public Statement createStatement() throws SQLException {
		return getConnection().createStatement();
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see
	 * jp.gr.norinori.database.DatabaseConnection#prepareStatement(java.lang
	 * .String)
	 */
	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return getConnection().prepareStatement(sql);
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see
	 * jp.gr.norinori.database.DatabaseConnection#prepareStatement(java.lang
	 * .String, int)
	 */
	@Override
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		return getConnection().prepareStatement(sql, autoGeneratedKeys);
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see
	 * jp.gr.norinori.database.DatabaseConnection#prepareCall(java.lang.String)
	 */
	@Override
	public CallableStatement prepareCall(String sql) throws SQLException {
		return getConnection().prepareCall(sql);
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see jp.gr.norinori.database.DatabaseConnection#commit()
	 */
	@Override
	public void commit() throws SQLException {
		getConnection().commit();
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see jp.gr.norinori.database.DatabaseConnection#rollback()
	 */
	@Override
	public void rollback() throws SQLException {
		getConnection().rollback();
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see jp.gr.norinori.database.DatabaseConnection#close()
	 */
	@Override
	public void close() throws SQLException {
		getConnection().close();
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see jp.gr.norinori.database.DatabaseConnection#setValidTime(int)
	 */
	@Override
	public void setValidTime(int validTime) {
		this.validTime = validTime;
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see jp.gr.norinori.database.DatabaseConnection#reconnect()
	 */
	@Override
	public void reconnect() throws SQLException {
		this.connection = null;
		DatabaseConnection databaseConnection = this.database.createConnection(this.target);
		if (databaseConnection != null) {
			this.connection = databaseConnection.getConnection();
		}
	}

	private Map<String, Savepoint> savePoints = null;

	@Override
	public void save(String savePoint) throws Exception {
		if (this.savePoints == null) {
			this.savePoints = new HashMap<String, Savepoint>();
		}
		this.savePoints.put(savePoint, getConnection().setSavepoint(savePoint));
	}

	@Override
	public void rollback(String savePoint) throws Exception {
		if (this.savePoints == null || !this.savePoints.containsKey(savePoint)) {
			getConnection().rollback(null);
			return;
		}
		getConnection().rollback(this.savePoints.get(savePoint));
	}

	@Override
	public void open() throws Exception {
		// no process
	}

	@Override
	public <T> void open(T target) throws Exception {
		// no process
	}

	@Override
	public boolean isLiving() throws Exception {
		return !getConnection().isClosed();
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see jp.gr.norinori.database.DatabaseConnection#setAutoCommit(boolean)
	 */
	@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		getConnection().setAutoCommit(autoCommit);
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see jp.gr.norinori.database.DatabaseConnection#getAutoCommit()
	 */
	@Override
	public boolean getAutoCommit() throws SQLException {
		return getConnection().getAutoCommit();
	}

}
