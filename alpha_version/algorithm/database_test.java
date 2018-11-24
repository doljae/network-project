package sql_practice;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.ResultSetMetaData;

public class project_02 {

   public static void main(String[] args) {
      

      Connection con = null;
      try {
         Class.forName("com.mysql.jdbc.Driver"); // 사용할 jdbc 드라이버 등록
         String url = "jdbc:mysql://localhost/network_project"; // mydb안에 들어가서 그 안의 테이블을 acces하겠다.
         String user = "root", passwd = "12345"; // 로그인을 하기 위한 string 값을 저장
         con = DriverManager.getConnection(url, user, passwd); // 잘 연결됬으면 connection object를 보냄
         System.out.println(con);
      } catch (ClassNotFoundException e) { // 만일 클래스를 못 찾으면(jdbc jar 파일을 못찾으면)
         e.printStackTrace();
      } catch (SQLException e) {
         e.printStackTrace();
      }

      // database operations ...
      
      Statement stmt = null;
      ResultSet rs = null;
      DatabaseMetaData dbmd = null; //이땐s result set이 아니라 database 의 클래스를 쓴다.
      ResultSet mdrs = null;
      ArrayList mArrayList=new ArrayList<String>(); //전체 테이블의 튜플값 전부를 string으로 묶어서 44개.
      HashMap<Integer, String> mHashMap = new HashMap<Integer, String>(); //튜플의 id값과, 음식점이름
      int [][]mArray = new int[44][8]; //한식, 중식, 일식..... 의 flag 값
      
      
      try {
          dbmd = con.getMetaData(); //아까 connection object를 실행해 메타데이터를 얻는다.
          mdrs = dbmd.getColumns(null, "netwokr_project", "restaurant", "%");
          String[] column = { "COLUMN_NAME", "TYPE_NAME" };
          String food[] = null;
          //System.out.println(column[0] + "\t" + column[1]);
          while (mdrs.next()) {
              String column_name = mdrs.getString(column[0]); // column[0]== "COLUMN_NAME"
              String type_name = mdrs.getString(column[1]); //column[1]== "TYPE_NAME"
              //System.out.println(column_name + "\t" + type_name);
          }

         stmt = con.createStatement();
         // 이 쿼리 문장이 주어진 조건을 만족함
         // Einstein 교수의 수업을 들은 학생 중 성적이 A- 이상인 학생 정보를 출력한다
         // 결과값이 없다고 나와야함. takes 테이블에 Eistein 교수의 수업을 들은 학생의 성적중 조건에 맞는 튜플은 없음.
         String sql = "select * from restaurant";
      
         rs = stmt.executeQuery(sql); // result set object 가 들어가게됨
         // 이 오브젝트를 통해 결과를 봐야함(택 1)
         // 여기에 메타데이터 코드를 넣을 것
         while (rs.next()) { // 퀴리 결과에서 튜플들을 하나씩 가져온다 가져올값이 있으면 true, 없으면 false를 리턴함
            int id = rs.getInt(1); // 첫 attribute 를 string 값으로 가져와서 name 에 넣어라
            // getDouble 등 뭐 다른것도 됨. getInt 도 가능
            if (rs.wasNull())
               id = 0; // 만약에 읽었는데 값이 null 이라면 name에 null이라는 문자열을 넣어라
            String name = rs.getString(2); // 두 번째로 나오는 attribute 를 문자로 가져와라
            if (rs.wasNull())
               name = "null";
            int kr = rs.getInt(3); 
            if (rs.wasNull())
               kr = 99; 
            int cn = rs.getInt(4); 
            if (rs.wasNull())
               cn = 99; 
            int jp = rs.getInt(5); 
            if (rs.wasNull())
               jp = 99; 
            int it = rs.getInt(6); 
            if (rs.wasNull())
               it = 99; 
            int sn = rs.getInt(7); 
            if (rs.wasNull())
               sn = 99; 
            int gb = rs.getInt(8); 
            if (rs.wasNull())
               gb = 99; 
            int cp = rs.getInt(9); 
            if (rs.wasNull())
               cp = 99; 
            int ff = rs.getInt(10); 
            if (rs.wasNull())
               ff = 99;
            String result=id + " " + name+" "+kr+" "+ cn+" "+ jp+" "+ it+" "+ sn+" "+ gb+" "+ cp+" "+ ff;
            mArrayList.add(result);
            mHashMap.put(id,name);
            int i=0;
            mArray[i][0]=kr;
            mArray[i][1]=cn;
            mArray[i][2]=jp;
            mArray[i][3]=it;
            mArray[i][4]=sn;
            mArray[i][5]=gb;
            mArray[i][6]=cp;
            mArray[i][7]=ff;
            i++;
            //System.out.println(id + " " + name+" "+kr+" "+ cn+" "+ jp+" "+ it+" "+ sn+" "+ gb+" "+ cp+" "+ ff);
         }
         if (rs.next() == false) // 만일 조건을 만족하는 튜플이 없다면
         {
            System.out.println("종료");
         }
         // 여기에 메타데이터 코드를 넣을 것(택 1)
      } catch (SQLException e1) {
         e1.printStackTrace();
      }
      try {
         if (stmt != null && !stmt.isClosed())
            stmt.close(); // 다쓰면 이것도 close 해서 메모리를 풀어줘야함
         if (rs != null && !rs.isClosed())
            rs.close();
      } catch (SQLException e1) {
         e1.printStackTrace();
      }
      // database operation end
      try {
         if (con != null && !con.isClosed())
            con.close(); // 맨 마지막에는 connection을 끊어준다
      } catch (SQLException e) { // 끊을때도 문제발생을 대비해서 try catch 구문을 씀
         e.printStackTrace();
      }
      /*
      for(int i=0;i<mArrayList.size();i++)
      {
         System.out.println(mArrayList.get(i));
         System.out.println("\n");
      }*/
      
      
      
   }
}