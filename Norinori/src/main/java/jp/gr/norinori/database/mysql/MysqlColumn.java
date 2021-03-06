package jp.gr.norinori.database.mysql;

import jp.gr.norinori.database.DatabaseColumn;

public class MysqlColumn implements DatabaseColumn {
	// メンバ===================================================================
	public String type;
	public Class<?> javaType;
	public String name;
	public int size;
	public String comment;
	public boolean isAutoIncrement = false;
	public boolean isPrimaryKey = false;

	// メソッド=================================================================
	public String getType() {
		return this.type;
	}

	public Class<?> getJavaType() {
		return this.javaType;
	}

	public String getName() {
		return this.name;
	}

	public int getSize() {
		return this.size;
	}

	public String getComment() {
		return this.comment;
	}

	public boolean isAutoIncrement() {
		return this.isAutoIncrement;
	}

	public boolean isPrimaryKey() {
		return this.isPrimaryKey;
	}

}
