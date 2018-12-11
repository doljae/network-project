package Ex2;

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

public class WhatToEatToday_Data {

   public static void main(String[] args) {
      

      Connection con = null;
      try {
         Class.forName("com.mysql.cj.jdbc.Driver"); // ����� jdbc ����̹� ���
         String url = "jdbc:mysql://localhost/network_project"; // mydb�ȿ� ���� �� ���� ���̺��� acces�ϰڴ�.
         String user = "root", passwd = "12345"; // �α����� �ϱ� ���� string ���� ����
         con = DriverManager.getConnection(url, user, passwd); // �� ��������� connection object�� ����
         System.out.println(con);
      } catch (ClassNotFoundException e) { // ���� Ŭ������ �� ã����(jdbc jar ������ ��ã����)
         e.printStackTrace();
      } catch (SQLException e) {
         e.printStackTrace();
      }

      // database operations ...
      
      Statement stmt = null;
      ResultSet rs = null;
      DatabaseMetaData dbmd = null; //�̶�s result set�� �ƴ϶� database �� Ŭ������ ����.
      ResultSet mdrs = null;
      ArrayList mArrayList=new ArrayList<String>(); //��ü ���̺��� Ʃ�ð� ���θ� string���� ��� 44��.
      HashMap<Integer, String> mHashMap = new HashMap<Integer, String>(); //Ʃ���� id����, �������̸�
      int [][]mArray = new int[44][8]; //�ѽ�, �߽�, �Ͻ�..... �� flag ��
      
      
      try {
          dbmd = con.getMetaData(); //�Ʊ� connection object�� ������ ��Ÿ�����͸� ��´�.
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
         // �� ���� ������ �־��� ������ ������
         // Einstein ������ ������ ���� �л� �� ������ A- �̻��� �л� ������ ����Ѵ�
         // ������� ���ٰ� ���;���. takes ���̺� Eistein ������ ������ ���� �л��� ������ ���ǿ� �´� Ʃ���� ����.
         String sql = "select * from restaurant";
      
         rs = stmt.executeQuery(sql); // result set object �� ���Ե�
         // �� ������Ʈ�� ���� ����� ������(�� 1)
         // ���⿡ ��Ÿ������ �ڵ带 ���� ��
         while (rs.next()) { // ���� ������� Ʃ�õ��� �ϳ��� �����´� �����ð��� ������ true, ������ false�� ������
            int id = rs.getInt(1); // ù attribute �� string ������ �����ͼ� name �� �־��
            // getDouble �� �� �ٸ��͵� ��. getInt �� ����
            if (rs.wasNull())
               id = 0; // ���࿡ �о��µ� ���� null �̶�� name�� null�̶�� ���ڿ��� �־��
            String name = rs.getString(2); // �� ��°�� ������ attribute �� ���ڷ� �����Ͷ�
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
            System.out.println(id + " " + name+" "+kr+" "+ cn+" "+ jp+" "+ it+" "+ sn+" "+ gb+" "+ cp+" "+ ff);
         }
         if (rs.next() == false) // ���� ������ �����ϴ� Ʃ���� ���ٸ�
         {
            System.out.println("����");
         }
         // ���⿡ ��Ÿ������ �ڵ带 ���� ��(�� 1)
      } catch (SQLException e1) {
         e1.printStackTrace();
      }
      try {
         if (stmt != null && !stmt.isClosed())
            stmt.close(); // �پ��� �̰͵� close �ؼ� �޸𸮸� Ǯ�������
         if (rs != null && !rs.isClosed())
            rs.close();
      } catch (SQLException e1) {
         e1.printStackTrace();
      }
      // database operation end
      try {
         if (con != null && !con.isClosed())
            con.close(); // �� ���������� connection�� �����ش�
      } catch (SQLException e) { // �������� �����߻��� ����ؼ� try catch ������ ��
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