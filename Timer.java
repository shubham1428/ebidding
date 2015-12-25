package ebid;
class Timer implements Runnable
{
    boolean end;
    int count,time,no;
    Thread th;
    Timer(int no)
    {
        this.no=no;
        th=new Thread(this);
        end=true;count=0;time=0;th.start();
    }
    public void run()
    {
        while(count<=3 && end)
        {
            try
            {
                if(!end)break;
                else System.out.println("\r"+(15-time)+" seconds remaining");
                if(time==15){System.out.print("Enter any integer to terminate this bidding:");break;}
                System.out.print("Enter your id(1-"+no+") for bidding:");
                Thread.sleep(5000);
                time+=5;
                count++;
            }
            catch(Exception e)
            {
                System.out.println(e);
            }
        }
    }
}
