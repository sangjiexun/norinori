package jp.gr.norinori.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import jp.gr.norinori.core.flow.Transaction;

/**
 * データベースコネクション
 *
 * @author inoue
 */
public interface DatabaseConnection extends Transaction {
	/**
	 * データベース名を取得する
	 *
	 * @return データベース名
	 */
	public String getDatabaseName();

	/**
	 * データベース名を設定する
	 *
	 * @param databaseName データベース名
	 */
	public void setDatabaeName(String databaseName);

	/**
	 * コネクションを取得する
	 *
	 * @return コネクション
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException;

	/**
	 * ステートメントを作成する
	 *
	 * @return ステートメント
	 * @throws SQLException
	 */
	public Statement createStatement() throws SQLException;

	/**
	 * プリペアードステートメントを作成する
	 *
	 * @param sql SQL文
	 * @return プリペアードステートメント
	 * @throws SQLException
	 */
	public PreparedStatement prepareStatement(String sql) throws SQLException;

	/**
	 * プリペアードステートメントを作成する
	 *
	 * @param sql SQL文
	 * @param autoGeneratedKeys
	 * @return プリペアードステートメント
	 * @throws SQLException
	 */
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException;

	/**
	 * コーラブルステートメントを作成する
	 *
	 * @param sql SQL文
	 * @return コーラブルステートメント
	 * @throws SQLException
	 */
	public CallableStatement prepareCall(String sql) throws SQLException;

	/**
	 * コミットする
	 *
	 * @throws SQLException
	 */
	public void commit() throws SQLException;

	/**
	 * ロールバックする
	 *
	 * @throws SQLException
	 */
	public void rollback() throws SQLException;

	/**
	 * クローズする
	 *
	 * @throws SQLException
	 */
	public void close() throws SQLException;

	/**
	 * 指定したテーブルのテーブル情報を作成する
	 *
	 * @param tableName テーブル名
	 * @return テーブル情報
	 */
	public DatabaseTableInformation createTableInformation(String tableName) throws Exception;

	/**
	 * 検証待機時間を設定する
	 *
	 * @param validTime 検証待機時間
	 */
	public void setValidTime(int validTime);

	/**
	 * 再接続する
	 *
	 * @throws SQLException
	 */
	public void reconnect() throws SQLException;
}
