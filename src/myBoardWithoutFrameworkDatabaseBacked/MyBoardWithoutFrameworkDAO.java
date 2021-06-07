// TODO:コメントをもっと丁寧に追記していく
package myBoardWithoutFrameworkDatabaseBacked;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MyBoardWithoutFrameworkDAO {

	private Connection conn;
	private PreparedStatement pStatement;
	private ResultSet queryResult;

	// 投稿を全件取得するメソッド
	public List<MyBoardWithoutFramework> selectAllPosts(){
		// 結果返却用のリストを作成
		List<MyBoardWithoutFramework> results = new ArrayList<MyBoardWithoutFramework>();
		try {
			// DBに接続
			this.getConnection();

			// SQLを送るオブジェクトを作成（boardテーブルから全件取得）
			pStatement = conn.prepareStatement("select * from board where deleted = false");
			// SQLを実行
			queryResult = pStatement.executeQuery();
			//取得してきた結果を処理
			while (queryResult.next()) {
				// 結果を1レコードずつ格納するmyBoardWithoutFrameworkオブジェクトPostを作成
				MyBoardWithoutFramework Post = new MyBoardWithoutFramework();
				// 各カラムを格納
				Post.setId(queryResult.getInt("id"));
				Post.setName(queryResult.getString("name"));
				Post.setEmail(queryResult.getString("email"));
				Post.setMsg(queryResult.getString("msg"));
				Post.setPostedAt(queryResult.getTimestamp("posted_at"));
				Post.setUpdatedAt(queryResult.getTimestamp("updated_at"));
				// 取得・整理したレコードの内容を返却用リストに追加
				results.add(Post);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
		// 取得してきた結果を返却
		return results;
	}

	// 投稿を1件個別に取得するメソッド
	public MyBoardWithoutFramework selectPost(int id) {
		MyBoardWithoutFramework Post = new MyBoardWithoutFramework();
		try {
			// DBに接続
			this.getConnection();

			// SQLを送るオブジェクトを作成（boardテーブルから指定の投稿を取得）
			pStatement = conn.prepareStatement("select * from board where id=?");
			pStatement.setInt(1, id);

			// SQLを実行
			queryResult = pStatement.executeQuery();
			queryResult.next();
			//取得してきた結果を処理
			// 各カラムを格納
			Post.setId(queryResult.getInt("id"));
			Post.setName(queryResult.getString("name"));
			Post.setEmail(queryResult.getString("email"));
			Post.setMsg(queryResult.getString("msg"));
			Post.setPostedAt(queryResult.getTimestamp("posted_at"));
			Post.setUpdatedAt(queryResult.getTimestamp("updated_at"));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			this.close();
		}

		return Post;
	}


	// 投稿を追加するメソッド
	public void insertPost (MyBoardWithoutFramework Post) {
		Timestamp Now = new Timestamp(System.currentTimeMillis());
		try {
			this.getConnection();
			pStatement = conn.prepareStatement("insert into board (name, email, msg, posted_at, updated_at, deleted) values(?, ?, ?, ?, ?, ?)");
			pStatement.setString(1, Post.getName());
			pStatement.setString(2, Post.getEmail());
			pStatement.setString(3, Post.getMsg());
			pStatement.setTimestamp(4, Now);
			pStatement.setTimestamp(5, Now);
			pStatement.setBoolean(6, false);
			pStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	//投稿を削除するメソッド
	public void deletePost(int id) {
		// TODO:実際の処理を記述する
		try {
			this.getConnection();
			pStatement = conn.prepareStatement("update board set deleted = true where id = ?");
			pStatement.setInt(1, id);
			pStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	//投稿を更新するメソッド
	public void updatePost( MyBoardWithoutFramework Post) {
		// TODO:実際の処理を記述する
		Timestamp Now = new Timestamp(System.currentTimeMillis());
		try {
			this.getConnection();
			pStatement = conn.prepareStatement("update board set name=?,email=?,msg=?,updated_at=? where id=?");
			pStatement.setString(1, Post.getName());
			pStatement.setString(2, Post.getEmail());
			pStatement.setString(3, Post.getMsg());
			pStatement.setTimestamp(4, Now);
			pStatement.setInt(5, Post.getId());
			pStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// DB接続をするメソッド
	private void getConnection() throws SQLException,ClassNotFoundException {
		// JDBCドライバを読み込む
		Class.forName("com.mysql.cj.jdbc.Driver");
		// 接続URLをドライバに渡して接続する
		conn = DriverManager.getConnection("jdbc:mysql://localhost/myboard_wf?useSSL=false&characterEncoding=utf8","myboard","zaq12wsx");

	}

	// DB接続を閉じるメソッド
	private void close() {
		   if (queryResult != null) {
			  try {
			      queryResult.close();
			  } catch (SQLException e) {
			      e.printStackTrace();
			    }
			  }
			  if (pStatement != null) {
			      try {
			          pStatement.close();
			      } catch (SQLException e) {
			        e.printStackTrace();
			      }
			    }
			    if (conn != null) {
			      try {
			        conn.close();
			      } catch (SQLException e) {
			        e.printStackTrace();
			      }
			    }
			  }
	}


