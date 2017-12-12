package com.hmkcode.neo4j;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.graphdb.index.ReadableIndex;


import java.util.Scanner;
import java.sql.*;

public class Sample1 {
	
	
	// JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "password";
	   
	   @SuppressWarnings("deprecation")
	public static void main(String[] args) {
	   Connection conn = null;
	   Statement stmt = null;
	   
		   
	        GraphDatabaseService graphDb = new GraphDatabaseFactory().
                newEmbeddedDatabaseBuilder( "db/graphDB" ).
                setConfig( GraphDatabaseSettings.node_keys_indexable, "name" ).
                setConfig( GraphDatabaseSettings.relationship_keys_indexable, "name" ).
                setConfig( GraphDatabaseSettings.node_auto_indexing, "true" ).
                setConfig( GraphDatabaseSettings.relationship_auto_indexing, "true" ).
                newGraphDatabase();
 
        registerShutdownHook(graphDb);
       
        Relationship relation;
        Node[] node=new Node[50] ;
        
        Transaction tx;
        
        int count=0,i,j=1,k,l,m,no_of_col;
        String database,db;
  	    String var1,var2,var3,var4,var5,var6,var7,pk,tab1,tab2,tab3;
  	    String [] col_name=new String[5];
  	    String [] table_name=new String[20];
  	   
  	    
  	    Scanner sc=new Scanner(System.in);
	    System.out.print("Please enter name of the relational database to be converted into graph database: ");
	    database=sc.next();
  	    
  	    
  	    
  	    try{
  	      //STEP 2: Register JDBC driver
  	      Class.forName("com.mysql.jdbc.Driver");
  	      String DB_URL = "jdbc:mysql://localhost:3306/";
  	      //STEP 3: Open a connection
  	      conn = DriverManager.getConnection(DB_URL,USER,PASS);
    	  DatabaseMetaData dbmd = conn.getMetaData();  
    	  ResultSet ctlgs = dbmd.getCatalogs(); 
    	  //int dbcount=0;
    	  while (ctlgs.next())  
    	  {  
    		  //dbcount++;
    		  //System.out.println("Database"+dbcount+": "+ctlgs.getString(1));
    		  db=ctlgs.getString(1);
    		  if(database.equals(db))
    		  {
    			  System.out.println("Database name provided is found...");
    		  }
    	  }
    	  conn.close();
  	      	
    	  
    	      	  
    	  
  	      conn = DriverManager.getConnection(DB_URL+database,USER,PASS);
  	      System.out.println("Connecting to database...");
  	      
  	    DatabaseMetaData dbm = conn.getMetaData();
  	    ResultSet rstab = dbm.getTables(null,null,"%",null);
  	    System.out.println("Table name:");
  	    int tbcount=0;
  	    while (rstab.next()){
  	    	table_name[tbcount]= rstab.getString("TABLE_NAME");
  	    	System.out.println(table_name[tbcount]);
  	    	tbcount++;
  	    }
  	  
  	     String db1="neo4j";
  	     //String db2="sup_part";
  	     
  	     if(database.equals(db1))
  	      {
  	    	  tab1="student";
  	    	  tab2="subject";
  	    	  tab3="marks_details";
  	      }
  	      else
  	      {
  	    	  
  	    	  tab1="supplier";
	    	  tab2="part";
	    	  tab3="shipment";
	     }
  	      
  	      //STEP 4: Execute a query
  	      stmt = conn.createStatement();
  	      String sql;
  	      System.out.println("Selecting table ["+tab1+"] to convert into graph database.");
  	      sql = "SELECT * FROM "+tab1;
  	      ResultSet rs = stmt.executeQuery(sql);
  	      PreparedStatement pstmt=conn.prepareStatement(sql);
	      ResultSetMetaData rsmd=pstmt.getMetaData();
	      no_of_col=rsmd.getColumnCount();
	      System.out.println("No of col= "+no_of_col);
	      for(i=1;i<=no_of_col;i++)
	      {
	    	  col_name[i]=rsmd.getColumnName(i);
	    	  System.out.println("Column"+i+" of table 1= "+col_name[i]);
	      }
	      pk=rsmd.getColumnName(1);
	      System.out.println("Primary Key is : "+pk);
  	     
	      tx = graphDb.beginTx();
  	    
  	    
  	      
  	      //STEP 5: Extract data from result set
  	      while(rs.next()){
  	         //Retrieve by column name
  	         count++;
  	         if(count==1)
  	         {
  	        	 
  	        	 var1=rs.getString(col_name[1]);
  	        	 k=j;
  	        	 node[j]= graphDb.createNode();
  	        	 node[j].setProperty("name", var1);
  	        	 j++;
  	        	 
  	        	 var2=rs.getString(col_name[2]);
  	        	 node[j]= graphDb.createNode();
 	        	 node[j].setProperty("name", var2);
 	        	if(database.equals(db1))
 	        		 relation = node[k].createRelationshipTo(node[j], RelTypes.name);
	        	 else
        		relation = node[k].createRelationshipTo(node[j], RelTypes.supp_name);
 	        	 j++;
 	        	 
 	        	 var3=rs.getString(col_name[3]);
 	        	 node[j]= graphDb.createNode();
	        	 node[j].setProperty("name", var3);
	        	 if(database.equals(db1))
 	        		 relation = node[k].createRelationshipTo(node[j], RelTypes.dob);
 	        	 else
 	        		relation = node[k].createRelationshipTo(node[j], RelTypes.supp_city);
	        	 j++;
  	         }
  	         else
  	         {
  	        	 
  	        	 var1=rs.getString(col_name[1]);
  	        	 k=j;
  	        	 node[j]= graphDb.createNode();
  	        	 node[j].setProperty("name", var1);
  	        	 j++;
  	        	 
  	        	 var2=rs.getString(col_name[2]);
  	        	 
  	        	 for(i=1;i<j;i++)
  	        	 {
  	        		 Object value= node[i].getProperty("name", null);
  	        		 if(value!=null && value.equals(var2))
  	        		 {
  	        			if(database.equals(db1))
  	        				relation = node[k].createRelationshipTo(node[j], RelTypes.name);
	        			else
 	        				relation = node[k].createRelationshipTo(node[j], RelTypes.supp_name);
  	        			break; 
  	        		 }
  	        	 }
  	        	
  	        	 if(i==j)
  	        	 {
  	        		 node[j]= graphDb.createNode();
  		        	 node[j].setProperty("name", var2);
  		        	if(database.equals(db1))
  	 	        		 relation = node[k].createRelationshipTo(node[j], RelTypes.name);
 	 	        	 else
 	 	        		relation = node[k].createRelationshipTo(node[j], RelTypes.supp_name);
  		        	 j++; 
  	        	 }
  	        	
  	        	var3=rs.getString(col_name[3]);
  	        	for(l=1;l<j;l++)
 	        	 {
 	        		 Object value= node[l].getProperty("name", null);
 	        		 if(value!=null && value.equals(var3))
 	        		 {
 	        			if(database.equals(db1))
 	        				relation = node[k].createRelationshipTo(node[l], RelTypes.dob);
 	   	        	 	else
 	   	        	 		relation = node[k].createRelationshipTo(node[l], RelTypes.supp_city);
 	        			break; 
 	        		 }
 	        	 }
  	        	if(l==j)
  	        	{
  	        		 node[j]= graphDb.createNode();
  		        	 node[j].setProperty("name", var3);
  		        	if(database.equals(db1))
	        			relation = node[k].createRelationshipTo(node[j], RelTypes.dob);
	   	        	else
	   	        		relation = node[k].createRelationshipTo(node[j], RelTypes.supp_city);
   		        	 j++;
  	        	}
	        	  
  	         }//End try
  	    	  
  	      }//End while
  	      rs.close();
	      stmt.close();
	      System.out.println("Table ["+tab1+"] converted to graph database.");
  	      
  	      
  	      stmt = conn.createStatement();
	      sql = "SELECT * FROM "+tab2;
	      System.out.println("Selecting table ["+tab2+"] to convert into graph database.");
	      ResultSet rs1 = stmt.executeQuery(sql);
	      PreparedStatement pstmt1=conn.prepareStatement(sql);
	      ResultSetMetaData rsmd1=pstmt1.getMetaData();
	      no_of_col=rsmd1.getColumnCount();
	      System.out.println("No of col= "+no_of_col);
	      for(i=1;i<=no_of_col;i++)
	      {
	    	  col_name[i]=rsmd1.getColumnName(i);
	    	  System.out.println("Column"+i+" of table 2 = "+col_name[i]);
	      }
	      pk=rsmd1.getColumnName(1);
	      System.out.println("Primary Key is : "+pk);
	      count=0;
	     //STEP 5: Extract data from result set
	      while(rs1.next()){
	         //Retrieve by column name
	         count++;
	         if(count==1)
	         {
	        	 
	        	 var4=rs1.getString(col_name[1]);
	        	 k=j;
	        	 node[j]= graphDb.createNode();
	        	 node[j].setProperty("name", var4);
	        	 j++;
	        	 
	        	 var5=rs1.getString(col_name[2]);
	        	 node[j]= graphDb.createNode();
	        	 node[j].setProperty("name", var5);
	        	 if(database.equals(db1))
	        		 relation = node[k].createRelationshipTo(node[j], RelTypes.sub_name);
	        	 else
	        		 relation = node[k].createRelationshipTo(node[j], RelTypes.part_name);
	             j++;
	        	 
	        	 var6=rs1.getString(col_name[3]);
	        	 node[j]= graphDb.createNode();
	        	 node[j].setProperty("name", var6);
	        	 if(database.equals(db1))
	        		 relation = node[k].createRelationshipTo(node[j], RelTypes.tot_marks);
	        	 else
	        		 relation = node[k].createRelationshipTo(node[j], RelTypes.part_color);
	        	 relation.setProperty("name", "total marks");
	             j++;
	         }
	         else
	         {
	        	 
	        	 var4=rs1.getString(col_name[1]);
	        	 k=j;
	        	 node[j]= graphDb.createNode();
	        	 node[j].setProperty("name", var4);
	        	 j++;
	        	 
	        	 var5=rs1.getString(col_name[2]);
	        	 
	        	 for(i=1;i<j;i++)
	        	 {
	        		 Object value= node[i].getProperty("name", null);
	        		 if(value!=null && value.equals(var5))
	        		 {
	        			 if(database.equals(db1))
	    	        		 relation = node[k].createRelationshipTo(node[i], RelTypes.sub_name);
	    	        	 else
	    	        		 relation = node[k].createRelationshipTo(node[i], RelTypes.part_name);
	 	                break; 
	        		 }
	        	 }
	        	 
	        	 if(i==j)
	        	 {
	        		 node[j]= graphDb.createNode();
		        	 node[j].setProperty("name", var5);
		        	 if(database.equals(db1))
		        		 relation = node[k].createRelationshipTo(node[j], RelTypes.sub_name);
		        	 else
		        		 relation = node[k].createRelationshipTo(node[j], RelTypes.part_name);
		             j++; 
	        	 }
	        	 
	        	var6=rs1.getString(col_name[3]);
	        	for(l=1;l<j;l++)
	        	 {
	        		 Object value= node[l].getProperty("name", null);
	        		 if(value!=null && value.equals(var6))
	        		 {
	        			 if(database.equals(db1))
	    	        		 relation = node[k].createRelationshipTo(node[l], RelTypes.tot_marks);
	    	        	 else
	    	        		 relation = node[k].createRelationshipTo(node[l], RelTypes.part_color);
	 	                break; 
	        		 }
	        	 }
	        	if(l==j)
	        	{
	        		 node[j]= graphDb.createNode();
		        	 node[j].setProperty("name", var6);
		        	 if(database.equals(db1))
		        		 relation = node[k].createRelationshipTo(node[j], RelTypes.tot_marks);
		        	 else
		        		 relation = node[k].createRelationshipTo(node[j], RelTypes.part_color);
		             j++;
	        	}
	        	  
	         }//End try
	    	  
	      }//End while
	      rs.close();
	      stmt.close();
	      System.out.println("Table ["+tab1+"] converted to graph database.");
  	      
  	      
  	      stmt = conn.createStatement();
	      sql = "SELECT * FROM "+tab3;
	      System.out.println("Selecting table ["+tab3+"] to convert into graph database.");
	      ResultSet rs2 = stmt.executeQuery(sql);
	      PreparedStatement pstmt2=conn.prepareStatement(sql);
	      ResultSetMetaData rsmd2=pstmt2.getMetaData();
	      no_of_col=rsmd2.getColumnCount();
	      System.out.println("No of col= "+no_of_col);
	      for(i=1;i<=no_of_col;i++)
	      {
	    	  col_name[i]=rsmd2.getColumnName(i);
	    	  System.out.println("Column"+i+" of table 3 = "+col_name[i]);
	      }
	      
	     //STEP 5: Extract data from result set
	      while(rs2.next()){
	         //Retrieve by column name
	         
	    	    var1=rs2.getString(col_name[1]);
	    	     for(i=1;i<j;i++)
	        	 {
	        		 Object value= node[i].getProperty("name", null);
	        		 if(value!=null && value.equals(var1))
	        			 break; 
	        	 }
	    	   
	        	 var4=rs2.getString(col_name[2]);
	        	 for(l=1;l<j;l++)
	        	 {
	        		 Object value= node[l].getProperty("name", null);
	        		 if(value!=null && value.equals(var4))
	        			 break; 
	        	 }
	        	 
	        	 
	        	 var7=rs2.getString(col_name[3]);
	        	 for(m=1;m<j;m++)
	        	 {
	        		 Object value= node[m].getProperty("name", null);
	        		 if(value!=null && value.equals(var7))
	        		 {
	        			 if(database.equals(db1))
	        			 {
			        		 relation = node[i].createRelationshipTo(node[m], RelTypes.marks_obtained);
				             //relation.setProperty("name", "sub name");
				        	 relation = node[m].createRelationshipTo(node[l], RelTypes.in_subject);
	        			 }
	        			 else
	        			 {
	        				 relation = node[i].createRelationshipTo(node[m], RelTypes.on_date);
				             //relation.setProperty("name", "sub name");
				        	 relation = node[m].createRelationshipTo(node[l], RelTypes.ordered);	        				 
	        			 }
			        	
			             break; 
	        		 }
	        	 }
	        	 
	        	 if(m==j)
	        	 {
	        		 node[j]= graphDb.createNode();
		        	 node[j].setProperty("name", var7);
		        	 if(database.equals(db1))
        			 {
		        		 relation = node[i].createRelationshipTo(node[j], RelTypes.marks_obtained);
			             //relation.setProperty("name", "sub name");
			        	 relation = node[j].createRelationshipTo(node[l], RelTypes.in_subject);
        			 }
        			 else
        			 {
        				 relation = node[i].createRelationshipTo(node[j], RelTypes.on_date);
			             //relation.setProperty("name", "sub name");
			        	 relation = node[j].createRelationshipTo(node[l], RelTypes.ordered);	        				 
        			 }
		        	 j++; 
	        	 }
	    	  
	      }//End while
         
  	      
  	      //STEP 6: Clean-up environment
  	      rs.close();
  	      stmt.close();
  	      conn.close();
  	    System.out.println("Table ["+tab3+"] converted to graph database.");  
  	    tx.success();
        tx.finish();
         
  	     
        }		
  	   
  	   catch(SQLException se){
  	      //Handle errors for JDBC
  	      se.printStackTrace();
  	   }
  	   catch(Exception e){
  	      //Handle errors for Class.forName
  	      e.printStackTrace();
  	   }
  	   finally{
  	      //finally block used to close resources
  	      try{
  	         if(stmt!=null)
  	            stmt.close();
  	      }
  	      catch(SQLException se2){
  	      }// nothing we can do
  	      try{
  	         if(conn!=null)
  	            conn.close();
  	      }
  	      catch(SQLException se){
  	         se.printStackTrace();
  	      }
  	   }//end finally
  	  
  	  
  	   ReadableIndex<Node> autoNodeIndex = graphDb.index()
                .getNodeAutoIndexer()
                .getAutoIndex();
        System.out.println(autoNodeIndex.getName());
 
    }
	   
	   
    public static enum RelTypes implements RelationshipType
    {
        name,dob,marks_obtained,sub_name,tot_marks,in_subject,
        supp_name,supp_city,part_name,part_color,on_date,ordered;
  
    }
 
    private static void registerShutdownHook( final GraphDatabaseService graphDb )
    {
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                graphDb.shutdown();
 
            }
        } );
    }
    
}
