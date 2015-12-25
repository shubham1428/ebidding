package ebid;
import java.sql.*;
import java.util.*;
import java.io.*;
public class DriverClass 
{
    static Scanner in=new Scanner(System.in);
    public static void main(String[] args) 
    {
        int no_of_people=0;
        Connection c;
        String out;
        try
        {
            FileWriter fr=new FileWriter("D:/bidding_logs.txt");
            System.out.println("Welcome to the Auction.\nHere we auction the 2011 Cricket World Cup accessories");
            System.out.println("\nRules : You will get 15 seconds to bid . Bids after 15 secs would be discarded");
            Thread.sleep(5000);
            Class.forName("com.mysql.jdbc.Driver");
            c=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Ebidding","root","1234");
            Statement s1;
            Statement s;
            s1=c.createStatement();
            s=c.createStatement();
            ResultSet rs;
            ResultSet rs1;
            rs=s.executeQuery("select count(ID) from People");
            while(rs.next())
                no_of_people=rs.getInt(1);
            rs=s.executeQuery("select * from Items");
            while(rs.next())
            {
                int id=0;
                double amt;
                int soldto=0;
                int itemid=rs.getInt(1);
                String iname=rs.getString(2);
                double base=rs.getDouble(3);
                String des=rs.getString(6);
                System.out.println("\nThe item id: "+itemid+" ,name: "+iname+" with base price: $"+base+" is now open for auction.");
                System.out.println("\nItem Description: "+des);
                System.out.println("\nBid starts at $"+base);
                Thread.sleep(500);System.out.println();
                while(id>=0)
                {
                    System.out.print("Enter your id(1-"+no_of_people+") for bidding:");
                    Timer t=new Timer(no_of_people);
                    while(true)
                    {   
                        id=in.nextInt();
                        //if(id<=0){System.out.println("ID should be greater than 0.Enter again:");continue;}
                        if(t.time<15)
                            t.end=false;
                        break;
                    }
                    if(t.end)
                    {
                        id=-1;
                        System.out.println("Time out");
                    }
                    if(!t.end)
                    {
                        if(id>no_of_people||id<=0)
                        {   System.out.println("Invalid ID");id=0;}
                        else
                        {
                            System.out.println("Enter your bid:");
                            amt=in.nextDouble();
                            rs1=s1.executeQuery("select Balance from people where ID="+id);
                            double bal=0;
                            while(rs1.next())
                                bal=rs1.getDouble(1);
                            if(amt<=base)
                            {
                                System.out.println("Invalid bid.Bid can't be less than previous bid or base price.\n");
                                id=soldto;
                            }
                            else if((bal-amt)<0)
                            {
                                System.out.println("Invalid bid.You don't have enough balance for this bid.\n");
                                id=soldto;
                            }
                            else
                            {
                                base=amt;
                                System.out.println("Bid Accepted\n");
                                soldto=id;
                            }
                        }
                    }
                }
                if(soldto>0)
                {
                    String query="update People set Balance=Balance-"+base+" where ID="+soldto;
                    s1.executeUpdate(query);
                    query="update Items set SoldTo="+soldto+",SellingPrice="+base+" where ItemID="+itemid;
                    s1.executeUpdate(query);
                    query="select Name from People where ID="+soldto;
                    rs1=s1.executeQuery(query);
                    String name="";
                    while(rs1.next())
                        name=rs1.getString(1);
                    out="Item: "+itemid+" name: "+iname+" sold to Mr."+name+" with ID: "+soldto+" for price: $"+base;
                    System.out.println(out);
                    fr.write(out+"\n");
                }
                else
                {
                    String query="update Items set SoldTo=null,SellingPrice=null where ItemID="+itemid;
                    s1.executeUpdate(query);
                    out="Item: "+itemid+" name: "+iname+" is not sold to anyone.";
                    System.out.println(out+"Auction closed for this item");
                    fr.write(out+"\n");
                }
                Thread.sleep(2000);
                System.out.println();
            }
            fr.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
