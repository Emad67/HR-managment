/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package start;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JTable;

/**
 *
 * @author win7b
 */
public class Conn {
   
    public String cycle ;
    public String nmbr ;
    public String day ;
    public String srid ;
    public static String [] columns;
    public static String [][] data;
    public static ArrayList<cdrFiller> values;
    
    
    public static String [] getColumns(){
    
        return columns;
    }
    
    
    public static String [][] getData(){
    
    return data;
    }
    
   
    
 
    
    Conn(String cycle,String nmbr,String day,String srid) {
        
     this.cycle = cycle;
     this.nmbr = nmbr;
     this.day = day;
     this.srid = srid;

}

   
public void myConn(){
    try{
Class.forName("oracle.jdbc.OracleDriver").newInstance();
   try (Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.10.162:1522:rb","eristaff1","eristaff1")) {
      System.out.println("connection works");
      System.out.println("cycle is "+this.cycle);
      
       columns = new String[2];
       columns[0]="Calling Number";
       columns[1]="Calling Date";
    
      
      String sql = "SELECT a.*,ROWID FROM v8rb.event_usage_"+this.cycle+" a" + " WHERE calling_nbr LIKE '291"+this.nmbr+"%'\n AND start_time >= to_date('"+this.day+"','yyyy-mm-dd')\n AND event_src_id ='"+this.srid+"' ORDER BY start_time ASC" ;
                       
     PreparedStatement stmt;
   stmt = conn.prepareStatement(sql);
     ResultSet rs = stmt.executeQuery();
     values = new ArrayList<>();
     cdrFiller c = new cdrFiller();
     while(rs.next()) {
        
         String sd = rs.getString("STATE_DATE");
        String bn = rs.getString("BILLING_NBR");
        c.setStateDate(rs.getString("STATE_DATE"));
        c.setBillinNumber(rs.getString("BILLING_NBR"));
        values.add(c);
       System.out.println(bn+"...."+sd);      }
     
        data= new String[1][2];
        data[0][0]= c.getStateDate() ;
        data[0][1]= c.getBilliNumber();
        
       
        
     rs.close();
     stmt.close();
     conn.close();
   } 
    
   }
  catch ( SQLException e){
        System.out.println("connection Failed");
  }
    catch ( IllegalAccessException ex){
        System.out.println("illegal acess tried");
    }
    catch (ClassNotFoundException el){
        System.out.println("class not found exception");
    }
    catch ( InstantiationException em){
        System.out.println("instatiation exception");
    }
  
}
   public static void main(String[] args) {
      
            Conn cn=new Conn("1366","12","2018-7-28","12");
            cn.myConn();}
            
   
//  public static void fillTable(){
//    
//        columns = new String[2];
//        columns[0]="Calling Number";
//        columns[1]="Calling Date";
//      
//        
//        
//    
//  }
}

