# ebidding
It is recommended to run this application on NetBeans .
Here first add the library for mysql as the code uses Driver class.

The java codes are written in ebid package so either make them in one package named ebid or change the package name in both the files as your requirement.
Import the ebidding.sql file in your database before running the code.
Make the necessary changes for the database in the DriverClass.java where getConnection is used.The changes would be for the name of the database(if any) and password(if any).

Input:

1.User has to enter his id for making a bid.ID should be between 1 and the total number of bidders in the database.

2.If user doesn't enter his bid within 15 seconds , the bidding stops taking an integer from user to terminate the bidding otherwise the bidding continues unless there is no user to bid for the product.

3.The auction continues till no product in the database is up for bidding.

Output:

1.The only output is the details of the bidding.

The logs of the overall bidding are maintained in a file named "bidding.txt" in D drive.

Note:

The program uses concepts of database , threading and file handling alongwith other java fundamentals.
