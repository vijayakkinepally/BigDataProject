F:

cd F:\workspace\PB_Project\Ebola_Tweets

javac -classpath F:\workspace\PB_Project\Ebola_Tweets\mongo-java-driver-2.9.3.jar EbolaNovAggregate.java

java -classpath F:\workspace\PB_Project\Ebola_Tweets\mongo-java-driver-2.9.3.jar;. EbolaNovAggregate

F:

copy F:\workspace\PB_Project\Ebola_Tweets\EbolaNov.csv  F:\workspace\D3_Visualization\EbolaNov.csv


cd F:\workspace\D3_Visualization


#start  chrome "http://localhost:8888/EbolaNovAggregate.html"


F:


cd F:\workspace\PB_Project\Ebola_Tweets


javac -classpath F:\workspace\PB_Project\Ebola_Tweets\mongo-java-driver-2.9.3.jar EbolaDecAggregate.java


java -classpath F:\workspace\PB_Project\Ebola_Tweets\mongo-java-driver-2.9.3.jar;. EbolaDecAggregate


F:

copy F:\workspace\PB_Project\Ebola_Tweets\EbolaDec.csv  F:\workspace\D3_Visualization\EbolaDec.csv


cd F:\workspace\D3_Visualization


#start  chrome "http://localhost:8888/EbolaDecAggregate.html"



F:


cd F:\workspace\PB_Project\Ebola_Tweets


javac -classpath F:\workspace\PB_Project\Ebola_Tweets\mongo-java-driver-2.9.3.jar EbolaTop10Users.java


java -classpath F:\workspace\PB_Project\Ebola_Tweets\mongo-java-driver-2.9.3.jar;. EbolaTop10Users


F:

copy F:\workspace\PB_Project\Ebola_Tweets\ebola_top10users.csv  F:\workspace\D3_Visualization\ebola_top10users.csv


F:


cd F:\workspace\PB_Project\Ebola_Tweets


javac -classpath F:\workspace\PB_Project\Ebola_Tweets\mongo-java-driver-2.9.3.jar TotalAggregate.java


java -classpath F:\workspace\PB_Project\Ebola_Tweets\mongo-java-driver-2.9.3.jar;. TotalAggregate


F:

copy F:\workspace\PB_Project\Ebola_Tweets\Total_Aggregate.csv  F:\workspace\D3_Visualization\Total_Aggregate.csv


cd F:\workspace\D3_Visualization


start chrome "http://localhost:8888/Ebola_Month.html"