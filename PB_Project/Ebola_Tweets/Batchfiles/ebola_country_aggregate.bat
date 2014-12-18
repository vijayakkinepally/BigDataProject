
F:


cd F:\workspace\PB_Project\Ebola_Tweets


javac -classpath F:\workspace\PB_Project\Ebola_Tweets\mongo-java-driver-2.9.3.jar TotalAggregate.java


java -classpath F:\workspace\PB_Project\Ebola_Tweets\mongo-java-driver-2.9.3.jar;. TotalAggregate


F:

copy F:\workspace\PB_Project\Ebola_Tweets\Total_Aggregate.csv  F:\workspace\D3_Visualization\Total_Aggregate.csv


cd F:\workspace\D3_Visualization


start  chrome "http://localhost:8888/Country_Aggregate.html"

